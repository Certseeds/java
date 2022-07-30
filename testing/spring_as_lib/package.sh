#!/usr/bin/env bash
set -euox pipefail
main() {
  mvn package spring-boot:repackage \
    -pl app \
    -am
  # -pl 后接目录名...
  # 在往上一层就可以是 spring_as_lib/app
}
main
