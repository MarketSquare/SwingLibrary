#!/bin/bash

mkdir -p target/demo/lib
cp target/swinglibrary-*-jar-with-dependencies.jar target/demo/lib
cp run_demo.sh target/demo
cp run_demo.py target/demo
cd target
zip -r swinglibrary-demo.zip demo
cd ..

