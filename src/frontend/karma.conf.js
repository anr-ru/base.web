module.exports = function(config) {
    config.set({
        basePath: '.',
        frameworks: ['jasmine'],
        files: [
            // External libraries (using a 'usual' and 'min' to run with both build profile)
            './test-classes/static/js/bundle.js',
            './test-classes/static/js/scripts.js',
            // External test libs
            './node_modules/angular-mocks/angular-mocks.js',
            // Project files
            './test-classes/static/js/main.js',
            // Tests
            '../src/test/javascript/**/*.js'
        ],        
        reporters: ['progress','junit'],
        junitReporter: {
          // Coping to usual JUnit results directory
          outputFile: '../../../target/surefire-reports/test-javascript-results.xml'  
        },
        port: 9876,
        logLevel: config.LOG_DEBUG,
        browsers: ['PhantomJS'], 
        singleRun: false,
        autoWatch: true,
        plugins: [
            'karma-jasmine',
            'karma-phantomjs-launcher',
            'karma-junit-reporter'
        ]
    });
};
