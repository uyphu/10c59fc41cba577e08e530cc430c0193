'use strict';

angular.module('jhipsterApp')
    .controller('GroupDetailController', function ($scope, $stateParams, Group) {
    	$scope.group = {};
        $scope.load = function (id) {
            Group.get(id).then(function(result) {
              $scope.group = result;
            });
        };
        $scope.load($stateParams.id);
    });
