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
    
    grunt.registerTask('dev', ['clean', 'jshint','browserify', 'uglify:dev' ]);
    grunt.registerTask('build', [ 'clean', 'jshint', 'concat', 'browserify', 'uglify:dev']);

    grunt.registerTask('default', ['build']);    
};
