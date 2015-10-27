/**
 * 
 */
var controllers = angular.module('Controllers', []);

controllers.controller('AppCtrl', [ '$scope','RestService', function($scope, rest) {

    $scope.items = rest.query();    
    $scope.orderProp = 'age';
    $scope.messages='';
    
    $scope.clicked=function() {
        $scope.messages=$scope.items;
    }
    
} ]);
