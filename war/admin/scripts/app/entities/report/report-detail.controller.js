'use strict';

angular.module('jhipsterApp')
    .controller('ReportDetailController', function ($scope, $stateParams, Report, ReportId) {
        $scope.report = {};
        
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
        
        $scope.load($stateParams.id);
    });
