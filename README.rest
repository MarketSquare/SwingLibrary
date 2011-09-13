SwingLibrary
============

Swing Library is a Robot Framework library for testing Java Swing
applications. This file describes the building and testing process of
SwingLibrary. For general information about SwingLibrary please see
http://code.google.com/p/robotframework-swinglibrary/

Prerequisites for building
--------------------------

SwingLibrary uses Apache Buildr (http://buildr.apache.org/) as a build tool.
All other dependencies are managed with Buildr.


Build tasks for Buildr
----------------------

In addition to Buildr's default tasks we define few of our own:

acceptance_tests (at):
  Runs the acceptance tests with Robot Framework, on physical display.

at_headless:
  Runs Robot Framework acceptance tests, but excludes the
  tests that require physical display. For example, to run the test
  with xvfb, invoke:

    xvfb-run buildr at

robot_test:
  Run acceptance tests using arguments. For example, to run only suite
  called ButtonKeywords, invoke

    (xvfb-run) robot_test[--suite ButtonKeywords]

dist:
  Builds SwingLibrary distribution package by creating a jar file containing
  the library and all its dependencies.

dist_devel (dd):
  Builds same distribution package as dist, but without running any tests.

demo:
  Package the SwingLibrary demo as a zip file.

libdoc:
  Generate library documentation with Robot Framework's libdoc tool.
