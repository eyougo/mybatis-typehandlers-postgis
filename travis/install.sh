#!/bin/bash

if [ $TRAVIS_JDK_VERSION == "oraclejdk7" ] || [ $TRAVIS_JDK_VERSION == "openjdk7" ]; then
  # Java 1.7
  mvn install -DskipTests=true -Dmaven.javadoc.skip=true -B -V -Pjava17
else
  # Java 1.8
  mvn install -DskipTests=true -Dmaven.javadoc.skip=true -B -V
fi
