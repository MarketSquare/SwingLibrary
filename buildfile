require 'lib/helper_methods'
require 'lib/dependencies'

PROJECT_NAME   = 'swinglibrary'
GROUP          = 'org.robotframework'
VERSION_NUMBER = 'trunk-1.1.1'

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
    compile.with DEPENDENCIES
    compile.options.source = "1.5"
    compile.options.target = "1.5"

    test.with [TEST_DEPENDENCIES]
    test.include '*Spec'
    
    build do
      generate_parameter_names(_('src/main/java'), _('target/classes'))
    end

    package(:sources, :id => PROJECT_NAME)
    package(:jar, :id => PROJECT_NAME)
  end

  define "test-application" do
    compile.with [project("core"), TEST_DEPENDENCIES, JRETROFIT, JAVALIB_CORE, JEMMY]
    package :jar
  end

  define "test-keywords" do
    compile.with [project("core"), project("test-application"), DEPENDENCIES, TEST_DEPENDENCIES] 
    package :jar
  end

  define "demo-application" do
    compile.with [HSQLDB]
    package :jar
  end
end

task :dist => :package do
  unless uptodate?(dist_jar, sources)
    puts "Creating #{dist_jar}"
    mkdir_p File.dirname(dist_jar)
    temp_dir do |tmpdir|
      artifacts(dist_contents).each do |jar|
        puts "unzipping #{jar}"
        sh "unzip -qo \"#{jar}\"", :verbose => false
      end
      sh "zip -qr \"#{dist_jar}\" * -x '*.SF'", :verbose => false
    end

    if !Buildr.environment.nil? && Buildr.environment == 'legacy'
      retro_translate(dist_jar, :include_retro => true)
    end
  end
  assert_classes_have_correct_version(dist_jar)
  jarjar dist_jar
end

task :at => :acceptance_tests
task :acceptance_tests => :dist do
  setup_at_environment
  output_dir = get_output_dir
  sh "jybot --loglevel TRACE --outputdir #{output_dir} --debugfile debug.txt  --noncritical development --tagstatcombine *NOTdevelopment:regression " + __('src/test/resources/robot-tests')
end

task :ci_at => :ci_acceptance_tests
task :ci_acceptance_tests => :dist do
  setup_at_environment
  output_dir = get_output_dir
  sh "jybot --exclude display-required --loglevel TRACE --monitorcolors off --outputdir #{output_dir} --debugfile debug.txt --noncritical development " + __('src/test/resources/robot-tests')
end

def setup_at_environment()
  test_app = project("#{PROJECT_NAME}:test-application").package
  test_keywords = project("#{PROJECT_NAME}:test-keywords").package
  jarjar test_keywords

  ENV['CLASSPATH'] = [test_keywords, test_app, dist_jar].flatten.join(File::PATH_SEPARATOR)

  if !Buildr.environment.nil? && Buildr.environment == 'legacy'
    retro_translate(test_app.to_s)
    retro_translate(test_keywords.to_s)
    ENV['PATH'] = "#{java14_home}/bin#{File::PATH_SEPARATOR}#{ENV['PATH']}"
  end
end

def get_output_dir()
  if ENV['ROBOT_OUTPUTDIR'].nil?
    return Dir.pwd+"/results"
  end
  return ENV['ROBOT_OUTPUTDIR']
end

task :doc do
  generate_parameter_names(__('src/main/java'), __('target/classes'))
  output_dir = project(PROJECT_NAME)._('doc')
  output_file = "#{output_dir}/#{PROJECT_NAME}-#{VERSION_NUMBER}-doc.html"
  mkdir_p output_dir
  ENV['CLASSPATH'] = [__('target/classes'), artifacts(DEPENDENCIES, TEST_DEPENDENCIES)].flatten.join(File::PATH_SEPARATOR)
  sh "jython -Dpython.path=#{python_path} lib/libdoc/libdoc.py --output #{output_file} SwingLibrary"
  assert_doc_ok(VERSION_NUMBER)
end

task :runjython do
  dependencies = [DEPENDENCIES, TEST_DEPENDENCIES].flatten
  ENV['CLASSPATH'] = Buildr.artifacts([project(PROJECT_NAME).projects, dependencies]).map(&:name).join(File::PATH_SEPARATOR)
  puts ENV['CLASSPATH']
  sh "jython -Dpython.path=#{python_path}"
end
