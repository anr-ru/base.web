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
            dependencies:  [
                '<%= app.dest %>/bower_components/jquery/dist/jquery.js',
                '<%= app.dest %>/bower_components/bootstrap/dist/js/bootstrap.js',
                '<%= app.dest %>/bower_components/angular/angular.js',
                '<%= app.dest %>/bower_components/angular-resource/angular-resource.js'
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
        }        
    });
    
    grunt.registerTask('dev', ['clean', 'jshint', 'uglify:dev' ]);
    grunt.registerTask('build', [ 'clean', 'jshint', 'concat', 'uglify:dev']);

    grunt.registerTask('default', ['build']);    
};
