#!/usr/bin/ruby

args = ARGV.join(" ")
ARGV.clear

require 'rubygems'
require 'buildr'
require File.dirname(__FILE__) + '/../../../../../lib/dependencies'

module Buildr
  class Application
    def rakefile
      __FILE__
    end
  end
end

deps = ['../../../../target/classes',
        '../../../../../test-application/target/classes',
        '../../../../../test-keywords/target/classes',
        Buildr.artifacts(DEPENDENCIES),
        Buildr.artifacts(TEST_DEPENDENCIES)].flatten

deps.each do |dep|
  raise "#{dep} doesn't exist" unless File.exist? dep.to_s
end

ENV['CLASSPATH'] = deps.join(":")

system "jybot -d /tmp --critical regression #{args}"
