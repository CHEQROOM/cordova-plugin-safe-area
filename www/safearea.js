var exec = require('cordova/exec');

exports.getInsets = function (success, error) {
  exec(success, error, 'SafeArea', 'getInsets', []);
};