require 'lib/class_version_check'

module HelperMethods
  def __(path)
    main_project._(path)
  end

  def assert_classes_have_correct_version(target)
    puts "Verifying classversions from '#{target}'"
    ClassVersionCheck.new(max_version).assert_classes_have_correct_version(target)
  end

  def dist_jar(classifier = "with-dependencies")
    swinglib = project(PROJECT_NAME)
    artifact = swinglib.package.to_hash
    "#{swinglib.path_to(:target)}/#{artifact[:id]}-#{artifact[:version]}-#{classifier}.jar"
  end

  def dist_dependencies
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
    new_value = values.flatten.inject {|memo,obj| "#{memo}:#{obj}" }
    ENV[name] = "#{new_value}:#{ENV[name]}"
  end

  def sources
    Dir.glob(__("src/main") + "/**/*")
  end

  def temp_dir
    tmp_dir = "/tmp/#{File.basename(__FILE__)}_#{$$}"
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

  def install_abbot
    mkdir_p _('target/classes')
    version = "1.0.2"
    ['abbot', 'costello'].each do |jar|
      file = "lib/#{jar}-#{version}.jar"
      install artifact("abbot:#{jar}:jar:#{version}").from(file)
    end
  end

  def include_abbot
    mkdir_p _('target/classes')
    artifacts(ABBOT).each do |jar|
      sh "unzip -qo #{jar} -d #{_('target/classes')}", :verbose => false
    end
  end
end

# Add dependencies to project.pom
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
end
include HelperMethods
