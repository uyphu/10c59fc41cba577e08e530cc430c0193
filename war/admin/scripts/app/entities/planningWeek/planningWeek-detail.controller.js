'use strict';

angular.module('jhipsterApp')
    .controller('PlanningWeekDetailController', function ($scope, $stateParams, PlanningWeek, PlanningWeekId) {
        $scope.planningWeek = {};
        
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
        
        $scope.load($stateParams.id);
    });
