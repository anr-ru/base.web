module.exports = (baseConfig, env) => {

  // Add .scss rule
  module: {
    rules: [{
      test: /\.scss$/,
      loaders: ['raw-loader']
    }];
  }
};
