'use strict';

angular.module('jhipsterApp')
	.controller('ReportEditController', function ($scope, Report, ReportId, ReportSearch, ParseLinks) {
		$scope.report = {};
        $scope.load = function (id) {
            Report.get({id: id}, function(result) {
              $scope.report = result;
            });
        };
        $scope.load($stateParams.id);
    });
