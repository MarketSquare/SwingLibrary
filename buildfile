require 'lib/helper_methods'
require 'lib/dependencies'

PROJECT_NAME   = 'swinglibrary'
GROUP          = 'org.robotframework'
VERSION_NUMBER = '1.1.4-SNAPSHOT'

repositories.remote << 'http://www.laughingpanda.org/maven2'
repositories.remote << 'http://repo1.maven.org/maven2'
repositories.remote << 'http://repository.codehaus.org'

Java.classpath << artifacts(PARANAMER_GENERATOR)
Java.classpath << File.expand_path('lib/swing-library-paranamer.jar')

desc "Robot Framework test library for Swing GUI testing"
define PROJECT_NAME do
  project.group   = GROUP
  project.version = VERSION_NUMBER

  compile.with DEPENDENCIES
  compile.options.source = "1.5"
  compile.options.target = "1.5"

  test.with [TEST_DEPENDENCIES]
  test.include '*Spec'

  package(:sources, :id => PROJECT_NAME)
  package(:jar, :id => PROJECT_NAME)
end

desc "Creates a distributable jar with dependencies"
task :dist do
  unless uptodate?(dist_jar, sources)
    main_project.package.invoke
    create_jar_with_dependencies
    assert_classes_have_correct_version(dist_jar)
    jarjar dist_jar
    puts "Successfully created #{dist_jar}"
  end
end

def create_jar_with_dependencies()
  temp_dir do |tmpdir|
    artifacts(dist_contents).each do |jar|
      puts "unzipping #{jar}"
      sh "unzip -qo \"#{jar}\"", :verbose => false
    end
    sh "zip -qr \"#{dist_jar}\" * -x '*.SF'", :verbose => false
  end
end

desc "Create keyword documentation using libdoc"
task :libdoc do
  generate_parameter_names(__('src/main/java'), __('target/classes'))
  output_dir = main_project._('doc')
  mkdir_p output_dir
  output_file = "#{output_dir}/#{PROJECT_NAME}-#{VERSION_NUMBER}-doc.html"
  runjython ("lib/libdoc/libdoc.py --output #{output_file} SwingLibrary")
  assert_doc_ok(VERSION_NUMBER)
end

desc "Run the swinglibrary acceptance tests"
task :at => :acceptance_tests
task :acceptance_tests  do
  run_robot
end

desc "Run the swinglibrary acceptance tests using xvfb (xvfb has to be installed separately)"
task :ci_at => :ci_acceptance_tests
task :ci_acceptance_tests => :dist do
  run_robot "--exclude display-required --monitorcolors off"
end

desc "Run Robot Framework tests during development, args can be given like rt[-t testname]"
task :rt => :robot_test
task :robot_test, :args do |t, args|
  run_robot args.args
end

def run_robot(args="")
  ENV['CLASSPATH'] = [dist_jar, Buildr.artifacts(ROBOT)].join(File::PATH_SEPARATOR)
  output_dir = get_output_dir
  cmd = "java org.robotframework.RobotFramework --outputdir #{output_dir} --debugfile debug.txt #{args} " +  __('src/test/resources/robot-tests')
  puts "running robot tests with command:\n#{cmd}"
  sh cmd
end

def get_output_dir()
  if ENV['ROBOT_OUTPUTDIR'].nil?
    return Dir.pwd+"/target/robot-results"
  end
  return ENV['ROBOT_OUTPUTDIR']
end

def runjython(cmd)
  dependencies = [DEPENDENCIES, TEST_DEPENDENCIES].flatten
  ENV['CLASSPATH'] = [__('target/classes') , Buildr.artifacts(dependencies).map(&:name)].join(File::PATH_SEPARATOR)
  sh "java org.python.util.jython #{cmd}"
end
