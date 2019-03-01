# lein-git-info

[![Clojars Project](https://img.shields.io/clojars/v/lein-git-info.svg)](https://clojars.org/lein-git-info)

build | status
----- | -----
Latest Build | [![CircleCI](https://circleci.com/gh/hilsonchang2018/lein-git-info.svg?style=svg&circle-token=e3805d10dcb2507eaa2e281250032063acafa30a)](https://circleci.com/gh/hilsonchang2018/lein-git-info)
master branch | [![CircleCI](https://circleci.com/gh/hilsonchang2018/lein-git-info/tree/master.svg?style=svg&circle-token=e3805d10dcb2507eaa2e281250032063acafa30a)](https://circleci.com/gh/hilsonchang2018/lein-git-info/tree/master)

## Usage

Use this for user-level plugins:

`[lein-git-info "0.1.2"]` into the `:plugins` vector of your `:user`
profile.

Use this for project-level plugins:

`[lein-git-info "0.1.2"]` into the `:plugins` vector of your project.clj.

and add an example usage that actually makes sense:

    $ lein git-info                    ;lein clean & lein install all "checkouts" projects & generate resources/verison.txt in this project.
    $ lein git-info install
    $ lein git-info uberjar
    $ lein git-info ring uberwar
    $ lein git-info (other task...)   ;lein git-info & lein (other task...) this project & remove resources/version.txt after package.

The reason of remove version file after packaged is this version file isn't in your project git respository,so all project use this plugin not need to add "resources/version.txt"  to .gitignore file,and the other reason is the version file in resources folder just represent the git info snapshot in package time.

## License

Copyright © 2019 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
