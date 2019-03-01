(defproject lein-git-info "0.1.2"
  :description "Generate projects git information to version file"
  :url "https://github.com/hilsonchang2018/lein-git-info"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :deploy-repositories [["releases" {:url "https://repo.clojars.org"
                                     :username :gpg :password :gpg }]
                        ["snapshots" {:url "https://repo.clojars.org"
                                      :username :gpg :password :gpg}]]
  :eval-in-leiningen true)
