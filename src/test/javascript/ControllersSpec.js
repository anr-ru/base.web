/**
 * Tests for a control
 */
describe('AppCtrl tests', function(){

    var $httpBackend = null; // Mock http
    
    // After all - Checking invocations
    afterEach(function(){
       $httpBackend.verifyNoOutstandingExpectation();
       $httpBackend.verifyNoOutstandingRequest();
    });
    
    /**
     * AppCtrl tests
     */
    describe('AppCtrl', function(){
    
        beforeEach(module('App'));
        beforeEach( inject(function($injector){
            $httpBackend = $injector.get('$httpBackend');
        }));
        

       
        /**
         * Use case : the main page is loaded 
         */
        it ('Checking the scope', inject(function($controller){
           
            handler = $httpBackend.when('GET', 'api/v1/datas').respond([{'name' : 'xxx'}]);

            var scope = {}, rootScope = {},
            ctrl = $controller('AppCtrl', {$scope:scope, $rootScope:rootScope});

            $httpBackend.flush();
            
            console.log(scope);
            expect(scope.messages).toBe('');
        }));
    });    
});
