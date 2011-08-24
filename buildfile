require './lib/build_helpers.rb'
require './lib/dependencies.rb'

PROJECT_NAME   = 'swinglibrary'
GROUP          = 'org.robotframework'
VERSION_NUMBER = '1.1.4-SNAPSHOT'

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

desc "Creates a jar distribution with dependencies"
task :dist do
  unless uptodate?(dist_jar, sources)
    main_project.package.invoke
    create_jar_with_dependencies
    verify_correct_class_versions(dist_jar)
    jarjar dist_jar
    puts "Successfully created #{dist_jar}"
  end
end

desc "Creates a jar distribution without running tests"
task :dd => :dist_devel
task :dist_devel do
  options.test = false
  main_project.package.invoke
  create_jar_with_dependencies
  puts "Successfully created #{dist_jar}"
end

desc "Create keyword documentation using libdoc"
task :libdoc do
  generate_parameter_names(__('src/main/java'), __('target/classes'))
  output_dir = main_project._('doc')
  mkdir_p output_dir
  output_file = "#{output_dir}/#{PROJECT_NAME}-#{VERSION_NUMBER}-doc.html"
  runjython ("lib/libdoc.py --output #{output_file} SwingLibrary")
  assert_doc_ok(VERSION_NUMBER)
end

desc "Run the swinglibrary acceptance tests"
task :at => :acceptance_tests
task :acceptance_tests  do
  run_robot
end

desc "Run the swinglibrary acceptance tests with virtual display"
task :at_headless => :headless_acceptance_tests
task :ci_at => :headless_acceptance_tests
task :headless_acceptance_tests => :dist do
  run_robot "--exclude display-required --monitorcolors off"
end

desc "Run Robot Framework tests during development, args can be given like rt[-t testname]"
task :robot_test, :args do |t, args|
  run_robot args.args
end

desc "Packages the SwingLibrary demo"
task :demo do
  demodir = "target/demo"
  sh "rm -rf #{demodir}"
  sh "mkdir -p #{demodir}/lib"
  sh "cp target/swinglibrary-*-jar-with-dependencies.jar demo/lib"
  sh "zip -r target/swinglibrary-demo.zip demo -x '*/.svn/*'"
  puts "created target/swinglibrary-demo.zip"
end
