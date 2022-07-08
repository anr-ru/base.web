//const genDefaultConfig = require('@angular/dist/server/config/defaults/webpack.config.js');

module.exports = (baseConfig, env) => {
  //const config = genDefaultConfig(baseConfig, env);

  // Add .scss rule
  module: {
    rules: [{
      test: /\.scss$/,
      loaders: ['raw-loader']
    }];
  }

  // Overwrite default .css rule
  //const cssRule = config.module.rules.find(rule => rule.test && rule.test.toString() === '/\\.css$/');
  //if (cssRule) cssRule.exclude = /\.component\.css$/;

  //return config;
};
