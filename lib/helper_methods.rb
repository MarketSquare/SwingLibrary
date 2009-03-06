require 'lib/class_version_check'

module HelperMethods
  def __(path)
    main_project._(path)
  end

  def assert_classes_have_correct_version(target)
    puts "Verifying classversions from '#{target}'"
    ClassVersionCheck.new(max_version).assert_classes_have_correct_version(target)
  end

  def dist_jar
    classifier = if Buildr.environment == 'legacy'
      '-jre1.4'
    else
      ''
    end
    swinglib = project(PROJECT_NAME)
    artifact = swinglib.package.to_hash
    "#{swinglib.path_to(:target)}/#{artifact[:id]}-#{artifact[:version]}#{classifier}.jar"
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

  def main_project
    project("#{PROJECT_NAME}:core")
  end

  def max_version
    if Buildr.environment == 'legacy' then 48 else 49 end
  end

  def retro_translate(target, options = {:include_retro => false})
    return if ClassVersionCheck.new(max_version).class_versions_ok?(target)
    translator = artifact(TRANSLATOR)
    translator.invoke
    jars = [translator] + artifacts(TRANSLATOR_RUNTIME)
    temp_dir do |tmpdir|
      cp jars.collect {|item| item.invoke; item.to_s}, tmpdir
      translator_jar = File.basename(translator.to_s)
      src = "#{if File.directory? target then "-srcdir" else "-srcjar" end} #{target}"
      sh "java -jar #{translator_jar} #{src}"
    end
    if options[:include_retro] 
      temp_dir do |tmpdir|
        artifacts(TRANSLATOR_RUNTIME).each do |jar|
          sh "unzip -qo #{jar}", :verbose => false
        end
        sh "zip -qru #{target} *", :verbose => false
      end
    end
  end

  def set_env(name, values)
    ENV[name] = "#{values.flatten.join(File::PATH_SEPARATOR)}"
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

  def java14_home 
    get_directory_from_settings('java14_home')
  end

  def python_path
    get_directory_from_settings('site_packages')
  end

  def get_directory_from_settings(directory_setting)
    dir = Buildr.settings.user[directory_setting]
    error_msg = %{Please define path to your #{directory_setting} directory in the ~/.buildr/settings.yaml by adding a line:
#{directory_setting}: /path/to/your/#{directory_setting}}
    raise error_msg if dir.nil? || !File.directory?(dir)
    dir
  end
end

# Buildr tweakings and fixes
module URI
  RW_CHUNK_SIZE = 2 ** 13
end

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


include HelperMethods
