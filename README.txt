SwingLibrary
============

Swing Library is a Robot Framework library for testing Java Swing
applications. This file describes the building and testing process of
SwingLibrary. For general information about SwingLibrary please see
http://code.google.com/p/robotframework-swinglibrary/

Prerequisites for building
--------------------------

SwingLibrary uses buildr and robot for building and testing:
 - buildr: http://incubator.apache.org/buildr and
 - robot: http://www.robotframework.org

Build tasks
-----------

In addition to buildr's default tasks we define few of our own:

acceptance_tests:
  Runs the acceptance tests, it is recommended to use a virtual display such as
  vnc when this is run, so that the GUI operations are not disturbed. For
  example on linux machine you could start vncserver and reset the DISPLAY=:1.0
  environment variable:

  export DISPLAY=:1.0
  buildr acceptance_tests

doc:
  Builds the robot documentation for this library. The path to python site-packages
  must be defined in the $HOME/.buildr/settings.yaml file:

  site_packages: <path-to-site-packages>

dist:
  Builds SwingLibrary distribution package by creating a jar file containing
  the library and all its dependencies.


Legacy Profile
--------------

You can run all the tasks with '-e legacy' modifier (eg. buildr dist -e
legacy).  This will force buildr to use java 1.4 for the task. This is
especially useful when creating the distribution package as we want
SwingLibrary to be compatible with jre 1.4. The path to your j2sdk1.4 must be
defined in the $HOME/.buildr/settings.yaml:

  java14_home: <path-to-site-j2sdk1.4.2>


Example $HOME/.buildr/settings.yaml
-----------------------------------

Here is my settings.yaml:

------------------ 8< ------------------------
site_packages: /usr/lib/python2.5/site-packages/
java14_home: /home/hhulkko/opt/j2sdk1.4.2_13

repositories:
  release_to:
    url: sftp://www.laughingpanda.org/var/www/localhost/htdocs/maven2   
    username: hhulkko
    password: ********
------------------ 8< ------------------------

See http://incubator.apache.org/buildr/settings_profiles.html#personal_settings
for more details.

