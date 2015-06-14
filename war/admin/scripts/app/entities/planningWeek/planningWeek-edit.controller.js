'use strict';

angular.module('jhipsterApp')
	.controller('PlanningWeekEditController', function ($scope, $stateParams, PlanningWeek, PlanningWeekId, PlanningWeekSearch, ParseLinks) {
        $scope.planningWeek = {};
        $scope.success = null;
        $scope.updateError = null;
        $scope.load = function (id) {
        	if (!AppConstant.PLANNINGWEEK_ENDPOINT_LOADED) {
      		   PlanningWeek.init().then(function(){
  					if (AppConstant.PLANNINGWEEK_ENDPOINT_LOADED) {
  						console.log("planningWeekendpoint loaded...")
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
        	PlanningWeek.get(id).then(function(result) {
                $scope.planningWeek = result;
              });
        };
        
        $scope.save = function () {
        	$scope.success = null;
        	$scope.updateError = null;
            if ($scope.planningWeek.id != null) {
            	$scope.planningWeek.delFlag = 'N';
                PlanningWeek.update($scope.planningWeek).then(function (data){
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
