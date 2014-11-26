/**
 * 
 */

function HelloWorlder() {
    
}

HelloWorlder.prototype.sayHello = function(value) {
  
    this.xx = value;
    return "Hello, world! " + value;
};
