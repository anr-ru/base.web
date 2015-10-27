/**
 * SERVICES: Main logic implementations
 */
var services = angular.module('Services', ['ngResource']);

services.factory('RestService', ['$resource', function($resource){
    return $resource('api/v1/datas', {}, {
      query: {
          method:'GET', 
          params:{}, 
          isArray:true
       }
    });
  }]);
