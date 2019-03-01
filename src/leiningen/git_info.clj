(ns leiningen.git-info (:require [clojure.string :refer [trim-newline]]
            [clojure.java.shell :refer [sh]]
            [clojure.java.io :refer [as-file]]
            [leiningen.core.project :as project])
  (:use [leiningen.core.main :only (apply-task task-not-found)]
        [clojure.string :only (join)])
  (:import java.io.File))

(def VER-FILE "resources/version.txt")

(defn error [msg-format & params]
  (println (apply format (str "ERROR: " msg-format) params))
  (System/exit 1))

(defn user-dir []
  (-> "user.dir" System/getProperty File.))

(defn list-checkouts [^File dir]
  (seq (.listFiles (File. dir "checkouts"))))

(defn get-project-data [^File dir]
  (project/read (.getAbsolutePath (File. dir "project.clj"))))

(defrecord Project [name, dir, data, checkouts])

(defn create-project [dir]
  (if-let [data (get-project-data dir)]
    (Project. (:name data), dir, data, nil)
    (error "There is no \"project.clj\" in directory \"%s\"!" (.getPath dir))))

(defn get-checkouts-map [project]
  (loop [project-list [(create-project (-> project :root File.))], result-map {}]
    (if-let [p (first project-list)] 
      (if (contains? result-map (:name p))
        (recur (rest project-list), result-map)
        (if-let [checkouts (seq (map create-project (list-checkouts (:dir p))))]
          (recur
            (apply conj (rest project-list) checkouts)
            (assoc result-map  (:name p) (assoc p :checkouts (set (map :name checkouts)))))
          (recur
            (rest project-list)
            (assoc result-map  (:name p) p))))
      result-map)))

(defn update-checkouts-map [checkouts-map, no-deps]
  (let [no-deps-names (map :name no-deps),
        checkouts-map (apply dissoc checkouts-map no-deps-names)
        ]
    (reduce
      (fn [cm, k] (update-in cm [k :checkouts] #(apply disj % no-deps-names))) 
      checkouts-map
      (keys checkouts-map))))


(defn create-checkout-build-seq [checkouts-map]
  (loop [checkouts-map checkouts-map, build-seq []]
    (if (empty? checkouts-map)
      build-seq
      (if-let [no-deps (seq (filter #(empty? (:checkouts %)) (vals checkouts-map)))]
        (recur (update-checkouts-map checkouts-map, no-deps), (apply conj build-seq no-deps))
        (error "There seem to be cyclic dependent checkouts!")
        ))))


(defn perform-lein-task [task-name, project]
  (println "lein" (if (string? task-name) task-name (join " " (seq task-name))))
  (apply-task task-name (:data project) []))

(defn new-version-file []
  (-> VER-FILE as-file .getParent as-file .mkdirs)
  (-> VER-FILE as-file .createNewFile)
  (-> VER-FILE (spit "Git:\n")))

(defn project-git-info [proj]
  (println (format "\nProcess project \"%s\":" (:name proj)))
  (let [git-dir (str "--git-dir=" (.getAbsolutePath (:dir proj)) "/.git")
        branch (trim-newline (:out (sh "git" git-dir "rev-parse" "--abbrev-ref" "HEAD")))
        commit-id (trim-newline (:out (sh "git" git-dir "rev-parse" "--short" "HEAD")))
        ]
    (println (format "git: \"%s\" \"%s\"" branch commit-id))
    (-> VER-FILE (spit (str (:name proj) ":\n" branch "\n" commit-id "\n\n") :append true))))

(defn git-infos [build-seq args]
  (new-version-file)
  (doseq [p (butlast build-seq)]
    (project-git-info p)
    (perform-lein-task "clean" p)
    (perform-lein-task "install" p))
  (let [p (last build-seq)]
    (project-git-info p)
    (perform-lein-task "clean" p)
    (when args (do (perform-lein-task args p)
                   (-> VER-FILE as-file .delete)))))

(defn git-info
  "Write git info to version file include all linked projects in \"checkouts\"."
  [project & args]
  (if project
    (-> project get-checkouts-map create-checkout-build-seq (git-infos args))
    (error "No project specified!")))

