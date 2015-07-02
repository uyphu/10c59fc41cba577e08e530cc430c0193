'use strict';

angular.module('jhipsterApp')
    .controller('ApprovalDetailController', function ($scope, $stateParams, User) {
    	$scope.user = {};
        $scope.load = function (id) {
            User.get(id).then(function(result) {
              $scope.user = result;
            });
        };
        $scope.load($stateParams.id);
    });
