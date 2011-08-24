module BuildHelpers
  def __(path)
    main_project._(path)
  end

  def main_project
    project(PROJECT_NAME)
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

  def jarjar(jar)
    sh "java -jar lib/jarjar-1.0.jar process lib/jarjar_rules.txt #{jar} #{jar}"
  end

  def assert_doc_ok(version)
    doc_name = "swinglibrary-#{version}-doc.html"
    IO.readlines("doc/"+doc_name).each do |line|
      if line =~ /\*<unknown>/
        raise "Errors in documentation: " + doc_name + " contains *<unknown>-tags."
      end
    end
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

  def verify_correct_class_versions(target)
    puts "Verifying classversions from '#{target}'"
    ClassVersionCheck.new(max_version).verify_correct_class_versions(target)
  end

  def dist_jar
    swinglib = main_project
    artifact = swinglib.package.to_hash
    classifier = "jar-with-dependencies"
    "#{swinglib.path_to(:target)}/#{artifact[:id]}-#{artifact[:version]}-#{classifier}.jar"
  end

  def dist_contents
    [DEPENDENCIES, main_project.package(:id => PROJECT_NAME)].flatten
  end

  def generate_parameter_names(from, to)
    unless File.exist? __('target/.paraname')
      touch __('target/.paraname')
      gen = Java.com.nsn.robot.paraname.Paranamer.new
      gen.processSourcePath(from, to)
    end
  end

  def max_version
    49
  end

  def sources
    Dir.glob(__("src/main") + "/**/*")
  end

  def temp_dir
    tmp_dir = "#{tmp}/#{File.basename(__FILE__)}_#{$$}"
    if block_given?
      mkdir_p tmp_dir unless File.directory? tmp_dir
      back = pwd
      cd tmp_dir
      begin
        yield(tmp_dir)
      ensure
        cd back
        rm_rf temp_dir
      end
    end
    tmp_dir
  end

  def tmp
    if ENV['TMP']; ENV['TMP'];
    else; '/tmp'; end
  end
end


class ClassVersionCheck
  MIN_VERSION = 45

  def initialize(max_version)
    @max_version = max_version
  end

  def verify_correct_class_versions(target)
    if File.directory?(target)
      verify_directory(target)
    elsif File.file?(target)
      verify_jar(target)
    end
  end

  def class_versions_ok?(target)
    begin
      verify_correct_class_versions(target)
    rescue
      return false
    end
    true
  end

  private

  def verify_directory(dir, src = dir)
    Dir.glob("#{dir}/**/*.class").each do |clss|
      actual_version = get_class_version(clss)
      if actual_version > @max_version.to_i || actual_version < MIN_VERSION
        raise "bad class version: max is #{@max_version} but found #{get_class_version(clss)} from #{clss} in #{src}"
      end
    end
  end

  def verify_jar(jar)
    temp_dir do
      system "unzip -qo #{jar}"
      verify_directory('.', jar)
    end
  end

  def get_class_version(file)
    File.open(file) do |f|
      f.seek 6
      ((0xFF & f.getbyte) << 8) | (0xFF & f.getbyte)
    end
  end
end


#Buildr tweaks
module Buildr
  module ActsAsArtifact
    def pom_xml
      xml = Builder::XmlMarkup.new(:indent=>2)
      xml.instruct!
      xml.project do
        xml.modelVersion  '4.0.0'
        xml.groupId       group
        xml.artifactId    id
        xml.version       version
        xml.dependencies do
          Buildr.artifacts(DEPENDENCIES).each do |jar|
            xml.dependency do
              xml.groupId    jar.group
              xml.artifactId jar.id
              xml.version    jar.version
            end
          end
        end
        xml.repositories do
          xml.repository do
            xml.id  'laughingpanda'
            xml.url 'http://www.laughingpanda.org/maven2'
          end
          xml.repository do
            xml.id  'codehaus'
            xml.url 'http://repository.codehaus.org'
          end
        end
      end
    end
  end

  module Eclipse
    class ClasspathEntryWriter
      def var(libs, var_name, var_value)
        paths = libs.map { |lib| lib.to_s.sub(var_value, var_name) }.sort.uniq
        paths.each do |path|
          path_to_sources = path.sub(/(.*).jar$/, '\1-sources.jar')
          if File.exist?(path_to_sources.sub(var_name, var_value))
            @xml.classpathentry :kind=>'var', :path=>path, :sourcepath=> path_to_sources
          else
            @xml.classpathentry :kind=>'var', :path=>path
          end
        end
      end
    end
  end

  class Artifact
    alias_method :old_download, :download

    def download
      old_download
      download_sources
    end

    def download_sources
      remote = Buildr.repositories.remote.map { |repo_url| URI === repo_url ? repo_url : URI.parse(repo_url) }
      remote = remote.each { |repo_url| repo_url.path += '/' unless repo_url.path[-1] == '/' }
      remote.find do |repo_url|
        begin
          source_name = name.sub(/(.*).jar$/, '\1-sources.jar')
          path = "#{group_path}/#{id}/#{version}/#{File.basename(source_name)}"
          URI.download repo_url + path, source_name
          true
        rescue URI::NotFoundError
          false
        rescue Exception=>error
          info error
          false
        end
      end
    end
  end
end


include BuildHelpers
