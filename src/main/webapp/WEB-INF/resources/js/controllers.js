/**
 * 
 */
var phonecatApp = angular.module('phonecatApp', [ 'ngRoute', 'phonecatControllers' ]);

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

phonecatControllers.controller('PhoneListCtrl', [ '$scope','$http', function($scope, $http) {

    $http.get('api/v1/datas').success(function(data) {
        $scope.phones = data;
      });
    
    $scope.orderProp = 'age';
} ]);

phonecatControllers.controller('PhoneDetailCtrl', [ '$scope', '$routeParams', function($scope, $routeParams) {

    $scope.phoneId = $routeParams.phoneId;
} ]);
