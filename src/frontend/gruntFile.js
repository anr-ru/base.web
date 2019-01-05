/*
 * Copyright 2014 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

module.exports = function(grunt) {
    
    require('time-grunt')(grunt);
    require('jit-grunt')(grunt);
    
    // All paths should be relative to ./target directory    
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        app: {
            source: '../src/test/resources/static/',
            test: '../src/test/javascript/',
            dest:   './test-classes/static',
            deps:   './node_modules',
            dependencies:  [
                '<%= app.deps %>/jquery/dist/jquery.js',
                '<%= app.deps %>/bootstrap/dist/js/bootstrap.js'
                // The rest is located under the 'browserify' section below
            ]
        },
        
        // Cleaning the directories
        clean: {
            dist: ['<%= app.dest %>/js']
        },

        // Checking JavaScript files for errors
        jshint: {
            files: ['gruntFile.js', '<%= app.source %>/js/*.js', '<%= app.test %>/**/*.js'],
            ignores: []
        },
        // Concatenation to a single file
        concat: {
            options: { separator: ';' },
            dist: {
                src:  [
                   '<%= app.source %>/js/init.js',
                   '<%= app.source %>/js/services.js',
                   '<%= app.source %>/js/controllers.js'
                ],
                dest: './main.min'
            }
        },
        copy: {
           main: {
             files: [
                  { expand: true,
                    cwd: '<%= app.deps %>/bootstrap/dist/css/',
                    src: '*.*', 
                    dest: '<%= app.dest %>/css/',
                    filter: 'isFile',
                  }
                ]
           }
        },
        
        // Obfuscation
        uglify: {
            dev: {
                options: {
                    mangle: false, beautify: true
                },
                files: {
                    '<%= app.dest %>/js/main.js': 
                        ['<%= concat.dist.src %>' ],
                    '<%= app.dest %>/js/scripts.js': 
                        ['<%= app.dependencies %>']
                }
            }
        },
        // Here we put Node-based modules which need the 'require' function
        // in order to build.
        browserify: {
            dist: {
              files: {
                '<%= app.dest %>/js/bundle.js': [
                    '<%= app.deps %>/angular/index.js',
                    '<%= app.deps %>/angular-resource/index.js'
                 ]
              }
            }
          }        
    });
    
    grunt.registerTask('dev', ['clean', 'jshint','browserify', 'uglify:dev', 'copy' ]);
    grunt.registerTask('build', [ 'clean', 'jshint', 'concat', 'browserify', 'uglify:dev', 'copy']);

    grunt.registerTask('default', ['build']);    
};
