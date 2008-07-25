class ClassVersionCheck
  MIN_VERSION = 45

  def initialize(max_version)
    @max_version = max_version
  end

  def assert_classes_have_correct_version(target)
    if File.directory?(target)
      verify_directory(target)
    elsif File.file?(target)
      verify_jar(target)
    end
  end

  def class_versions_ok?(target)
    begin
      assert_classes_have_correct_version(target)
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
      ((0xFF & f.getc) << 8) | (0xFF & f.getc)
    end
  end
end
