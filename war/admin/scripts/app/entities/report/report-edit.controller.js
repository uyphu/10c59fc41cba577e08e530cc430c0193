'use strict';

angular.module('jhipsterApp')
	.controller('ReportEditController', function ($scope, $stateParams, Report, ReportId, ReportSearch, ParseLinks) {
        $scope.report = {};
        $scope.success = null;
        $scope.updateError = null;
        $scope.load = function (id) {
        	if (!AppConstant.REPORT_ENDPOINT_LOADED) {
      		   Report.init().then(function(){
  					if (AppConstant.REPORT_ENDPOINT_LOADED) {
  						console.log("reportendpoint loaded...")
  						$scope.get(id);
  					}
  				},
  				function(){
  					console.log(ErrorCode.ERROR_INIT_ENDPOINT_SERVICE);
  				});
      	   } else {
      		   $scope.get(id);
      	   }
            
        };
        
        $scope.get = function(id) {
        	Report.get(id).then(function(result) {
                $scope.report = result;
              });
        };
        
        $scope.save = function () {
        	$scope.success = null;
        	$scope.updateError = null;
            if ($scope.report.id != null) {
            	$scope.report.delFlag = 'N';
                Report.update($scope.report).then(function (data){
                	if (data.error != null) {
             		   showError(data.code);
             	   	} else {
             	   		$scope.success = 'OK';
             	   	}
                });
            } 
        };
        
        function showError(errorCode) {
 		   if (errorCode == 410) {
 			   $scope.updateError = 'ERROR';
 		   }
  	   };
        
        $scope.load($stateParams.id);
    });
