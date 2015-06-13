'use strict';

angular.module('jhipsterApp')
    .controller('ReportIdDetailController', function ($scope, $stateParams, ReportId, User) {
    	$scope.reportId = {};
        $scope.load = function (id) {
            ReportId.get(id).then(function(result) {
              $scope.reportId = result;
            });
        };
        $scope.load($stateParams.id);
    });