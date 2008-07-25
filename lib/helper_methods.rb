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
    lib = project(PROJECT_NAME)
    artifact = lib.package.to_hash
    "#{lib.path_to(:target)}/#{artifact[:id]}-#{artifact[:version]}-#{classifier}.jar"
  end

  def dist_jars
    [DEPENDENCIES, main_project.package].flatten
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
end

include HelperMethods
