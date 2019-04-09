# lein-git-info

![Clojars Project](https://img.shields.io/clojars/v/lein-git-info.svg)
![download](https://img.shields.io/clojars/dt/lein-git-info.svg)
![stars](https://img.shields.io/github/stars/hilsonchang2018/lein-git-info.svg?style=social)
[![CHANGELOG.md](https://img.shields.io/badge/-changelog-blue.svg)](CHANGELOG.md)
![code-size](https://img.shields.io/github/languages/code-size/hilsonchang2018/lein-git-info.svg)
![license](https://img.shields.io/github/license/hilsonchang2018/lein-git-info.svg)


build | status
----- | -----
Latest Build | [![CircleCI](https://circleci.com/gh/hilsonchang2018/lein-git-info.svg?style=svg&circle-token=e3805d10dcb2507eaa2e281250032063acafa30a)](https://circleci.com/gh/hilsonchang2018/lein-git-info)
master branch | [![CircleCI](https://circleci.com/gh/hilsonchang2018/lein-git-info/tree/master.svg?style=svg&circle-token=e3805d10dcb2507eaa2e281250032063acafa30a)](https://circleci.com/gh/hilsonchang2018/lein-git-info/tree/master)

## Usage

Add this into the `:plugins` vector of your `:user` profile , or  `:plugins` vector of your project.clj.

![https://clojars.org/lein-git-info](https://clojars.org/lein-git-info/latest-version.svg)

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
