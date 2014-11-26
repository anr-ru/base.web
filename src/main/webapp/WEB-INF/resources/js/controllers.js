/**
 * 
 */
var phonecatApp = angular.module('phonecatApp', [ 'ngRoute', 'phonecatControllers', 'phonecatServices' ]);

phonecatApp.config([ '$routeProvider', function($routeProvider) {

    $routeProvider.when('/phones', {
            templateUrl: './templates/phone-list',
            controller: 'PhoneListCtrl'
    }).when('/phones/:phoneId', {
            templateUrl: './templates/phone-detail',
            controller: 'PhoneDetailCtrl'
    }).otherwise({
        redirectTo: '/phones'
    });
} ]);


var phonecatControllers = angular.module('phonecatControllers', []);

phonecatControllers.controller('PhoneListCtrl', [ '$scope','$http', 'PhoneREST', function($scope, $http, phoneRest) {

    $scope.phones = phoneRest.query();    
    $scope.orderProp = 'age';
} ]);

phonecatControllers.controller('PhoneDetailCtrl', [ '$scope', '$routeParams', function($scope, $routeParams) {

    $scope.phoneId = $routeParams.phoneId;
} ]);



var phonecatServices = angular.module('phonecatServices', ['ngResource']);

phonecatServices.factory('PhoneREST', ['$resource', function($resource){
    return $resource('api/v1/datas', {}, {
      query: {method:'GET', params:{}, isArray:true}
    });
  }]);
