require 'buildr/cobertura'
require 'yaml'
require 'lib/helper_methods'
require 'lib/dependencies'

PROJECT_NAME   = 'swing-library'
GROUP          = 'org.robotframework'
VERSION_NUMBER = '0.4-SNAPSHOT'
SETTINGS       = YAML::load(File.open('settings.yaml'))

repositories.remote << 'http://www.laughingpanda.org/maven2'
repositories.remote << 'http://repo1.maven.org/maven2'
repositories.remote << 'http://repository.codehaus.org'

Java.classpath << artifacts(PARANAMER_GENERATOR)
Java.classpath << File.expand_path('lib/swing-library-paranamer.jar')

desc "Robot library for Swing GUI testing"
define PROJECT_NAME do
  project.group   = GROUP
  project.version = VERSION_NUMBER

  define "core" do
    handle_abbot

    compile.with DEPENDENCIES, ABBOT
    compile.options.source = "1.5"
    compile.options.target = "1.5"

    test.with [TEST_DEPENDENCIES, project("test-application").package]
    test.include '*Spec'
    
    build do
      generate_parameter_names(_('src/main/java'), _('target/classes'))
    end

    package(:sources, :id => PROJECT_NAME)
    package(:jar, :id => PROJECT_NAME)
  end

  desc "Test application"
  define "test-application" do
    compile.with [TEST_DEPENDENCIES, JAVALIB_CORE, JEMMY]
    package :jar
  end

  define "test-keywords" do
    compile.with [project("core"), JEMMY, JDOM, JAVALIB_CORE] 
    package :jar
  end
end

task :dist => :package do
  unless uptodate?(dist_jar, sources)
    puts "Creating #{dist_jar}"
    temp_dir do |tmpdir|
      artifacts(dist_jars).each do |jar|
        sh "unzip -qo #{jar}", :verbose => false
      end
      sh "zip -qr #{dist_jar} * -x \*.SF", :verbose => false
    end

    if !Buildr.environment.nil? && Buildr.environment == 'legacy'
      retro_translate(dist_jar, :include_retro => true)
    end
  end
  assert_classes_have_correct_version(dist_jar)
end

task :at => :acceptance_tests
task :acceptance_tests => :dist do
  test_app = project("#{PROJECT_NAME}:test-application").package
  test_keywords = project("#{PROJECT_NAME}:test-keywords").package
  set_env('CLASSPATH', [test_keywords, test_app, dist_jar])

  if !Buildr.environment.nil? && Buildr.environment == 'legacy'
    if !SETTINGS || SETTINGS[:java14_home].nil? || !File.directory?(SETTINGS[:java14_home])
      raise 'Please define java14_home in the settings.yaml' 
    end
    retro_translate(test_app.to_s)
    retro_translate(test_keywords.to_s)
    set_env('PATH', [SETTINGS[:java14_home] + "/bin"])
  end

  sh "jybot -d /tmp --critical regression " + __('src/test/resources/robot-tests')
end

task :doc => :compile do
  generate_parameter_names(__('src/main/java'), __('target/classes'))
  output_dir = project(PROJECT_NAME)._('doc')
  output_file = "#{output_dir}/#{PROJECT_NAME}-#{VERSION_NUMBER}-doc.html"
  mkdir_p output_dir
  set_env('CLASSPATH', [__('target/classes'), artifacts(DEPENDENCIES, TEST_DEPENDENCIES)])
  sh "jython -Dpython.path=/usr/lib/python2.5/site-packages/ lib/libdoc/libdoc.py --output #{output_file} SwingLibrary"
end

def handle_abbot
  mkdir_p _('target/classes')
  version = "1.0.2"
  ['abbot', 'costello'].each do |jar|
    file = "lib/#{jar}-#{version}.jar"
    install artifact("abbot:#{jar}:jar:#{version}").from(file)
    sh "unzip -qo #{file} -d #{_('target/classes')}", :verbose => false
  end
end
