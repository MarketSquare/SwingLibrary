(defproject SwingLibrary "1.2.1-SNAPSHOT"
    :description "some"
    :url "https://github.com/robotframework/SwingLibrary"
    :dependencies [[org.robotframework/robotframework "2.7.1"]
                   [org.netbeans/jemmy "2.2.7.5"]
                   [org.robotframework/javalib-core "0.9.1"]
                   [junit/junit "4.5"]
                   [jretrofit/jretrofit "1.0"]
                   [abbot/abbot "1.0.2"]
                   ]
    :local-repo "/home/jth/.lein/repo"
    :repositories {"laughingpanda" "http://maven.laughingpanda.org/maven2"}
    :java-source-paths ["src/main/java"])
