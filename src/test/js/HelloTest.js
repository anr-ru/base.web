/**
 * Jasmine test example
 */
describe("HelloWorld test group", function() {
  
    var hello;

  beforeEach(function() {
    hello = new HelloWorlder();
  });

  it("should say result with Hello world", function() {
    
      var x = hello.sayHello('xxx');
      
      // This console really works and tell us what is going here
      console.log(x);
      
      expect(x).toEqual('Hello, world! xxx');
      
  });

  it("should save internaly sayHello() value", function() {
      
      hello.sayHello('Привет');
      
      expect(hello.xx).not.toEqual('Hello, world! xxx');
      expect(hello.xx).toEqual('Привет');
      
  }); 
  
  /*
   * Writing x befor it or describe to mark a test as suspended
   */
  xit("Sample suspended test", function() {
     
      console.log('Test suspended');
      
  });
  
});
