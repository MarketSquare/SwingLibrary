#!/bin/bash

rm -rf target/demo
mkdir -p target/demo/lib
cp target/swinglibrary-*-jar-with-dependencies.jar demo/lib
zip -r target/swinglibrary-demo.zip demo -x '*/.svn/*'

