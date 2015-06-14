'use strict';

angular.module('jhipsterApp')
    .controller('PlanningWeekIdDetailController', function ($scope, $stateParams, PlanningWeekId, User) {
    	$scope.planningWeekId = {};
        $scope.load = function (id) {
            PlanningWeekId.get(id).then(function(result) {
              $scope.planningWeekId = result;
            });
        };
        $scope.load($stateParams.id);
    });

