require './lib/build_helpers.rb'
require './lib/dependencies.rb'

PROJECT_NAME   = 'swinglibrary'
GROUP          = 'org.robotframework'
VERSION_NUMBER = '1.2'

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
task :uberjar do
  unless uptodate?(dist_jar, sources)
    main_project.package.invoke
    create_jar_with_dependencies
    verify_correct_class_versions(dist_jar)
    jarjar dist_jar
    puts "Successfully created #{dist_jar}"
  end
end

desc "Create keyword documentation using libdoc"
task :libdoc do
  output_dir = main_project._('target')
  mkdir_p output_dir
  outputfile = "#{output_dir}/#{PROJECT_NAME}-#{VERSION_NUMBER}-doc.html"
  unless uptodate?(outputfile, [dist_jar])
    puts "Creating library documentation"
    generate_parameter_names(__('src/main/java'), __('target/classes'))
    runjython ("lib/libdoc.py --output #{outputfile} SwingLibrary")
    assert_doc_ok(outputfile)
  end
end

desc "Run the swinglibrary acceptance tests"
task :at => :acceptance_tests
task :acceptance_tests  do
  run_robot
end

desc "Run the swinglibrary acceptance tests with virtual display"
task :at_headless => :headless_acceptance_tests
task :ci_at => :headless_acceptance_tests
task :headless_acceptance_tests => :uberjar do
  run_robot "--exclude display-required --monitorcolors off"
end

desc "Run Robot Framework tests during development, args can be given like robottest[-t testname]"
task :robottest, :args do |t, args|
  run_robot args.args
end

desc "Packages the SwingLibrary demo"
task :demo do
  demodir = "target/swinglibrary-demo"
  time = Time.new.strftime("%Y-%m-%d")
  demozip = "swinglibrary-demo-#{time}.zip"
  unless uptodate?(demozip, [dist_jar])
    sh "rm -rf #{demodir}"
    sh "mkdir -p #{demodir}/lib"
    sh "cp #{dist_jar} #{demodir}/lib/"
    sh "cp demo/*.* #{demodir}"
    sh "cd target && zip -r #{demozip} swinglibrary-demo"
    puts "created #{demozip}"
  end
end

desc "Create uberjar, demo and libdoc"
task :dist => [:uberjar, :demo, :libdoc] do
end

desc "Deploys file to Sonatype OSS repository"
task :deploy do
  sh  "mvn gpg:sign-and-deploy-file -Durl=https://oss.sonatype.org/service/local/staging/deploy/maven2/ -DrepositoryId=sonatype-nexus-staging -DpomFile=pom.xml -Dfile=#{dist_jar}"
end
