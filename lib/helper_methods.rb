require './lib/class_version_check.rb'

module HelperMethods
  def __(path)
    main_project._(path)
  end

  def main_project
    project(PROJECT_NAME)
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

  def assert_classes_have_correct_version(target)
    puts "Verifying classversions from '#{target}'"
    ClassVersionCheck.new(max_version).assert_classes_have_correct_version(target)
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
