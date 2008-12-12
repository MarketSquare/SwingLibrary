require 'buildr/cobertura'
require 'yaml'
require 'lib/helper_methods'
require 'lib/dependencies'

PROJECT_NAME   = 'swinglibrary'
GROUP          = 'org.robotframework'
VERSION_NUMBER = '0.9-SNAPSHOT'

repositories.remote << 'http://www.laughingpanda.org/maven2'
repositories.remote << 'http://repo1.maven.org/maven2'
repositories.remote << 'http://repository.codehaus.org'
repositories.remote << 'lib' #"file://#{File.dirname(__FILE__)}/lib"

Java.classpath << artifacts(PARANAMER_GENERATOR)
Java.classpath << File.expand_path('lib/swing-library-paranamer.jar')

desc "Robot library for Swing GUI testing"
define PROJECT_NAME do
  project.group   = GROUP
  project.version = VERSION_NUMBER

  define "core" do
    compile.with DEPENDENCIES, ABBOT
    compile.options.source = "1.5"
    compile.options.target = "1.5"

    test.with [TEST_DEPENDENCIES, project("test-application").package]
    test.include '*Spec'
    
    build do
      generate_parameter_names(_('src/main/java'), _('target/classes'))
    end

    package(:sources, :id => PROJECT_NAME)
    package(:jar, :id => PROJECT_NAME).enhance do
      include_abbot
    end
  end

  desc "Test application"
  define "test-application" do
    compile.with [TEST_DEPENDENCIES, JAVALIB_CORE, JEMMY]
    package :jar
  end

  define "test-keywords" do
    compile.with [project("core"), project("test-application"), JEMMY, JDOM, JAVALIB_CORE, JAVALIB_CORE_DEPENDENCIES] 
    package :jar
  end
end

task :dist => :package do
  unless uptodate?(dist_jar, sources)
    puts "Creating #{dist_jar}"
    mkdir_p File.dirname(dist_jar)
    temp_dir do |tmpdir|
      artifacts(dist_dependencies).each do |jar|
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
    retro_translate(test_app.to_s)
    retro_translate(test_keywords.to_s)
    set_env('PATH', ["#{java14_home}/bin"])
  end

  output_dir = if ENV['ROBOT_OUTPUTDIR'].nil?
    Dir.tmpdir
  else
    ENV['ROBOT_OUTPUTDIR']
  end
  
  sh "jybot --outputdir #{output_dir} --critical regression " + __('src/test/resources/robot-tests')
end

task :doc => :compile do
  generate_parameter_names(__('src/main/java'), __('target/classes'))
  output_dir = project(PROJECT_NAME)._('doc')
  output_file = "#{output_dir}/#{PROJECT_NAME}-#{VERSION_NUMBER}-doc.html"
  mkdir_p output_dir
  set_env('CLASSPATH', [__('target/classes'), artifacts(DEPENDENCIES, TEST_DEPENDENCIES)])
  sh "jython -Dpython.path=#{python_path} lib/libdoc/libdoc.py --output #{output_file} SwingLibrary"
end
