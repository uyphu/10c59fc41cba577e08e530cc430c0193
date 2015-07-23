'use strict';

angular.module('jhipsterApp')
    .controller('ReportIdController', function ($scope, $timeout, $rootScope, $stateParams, usSpinnerService, ReportId, User, ReportIdSearch, ParseLinks) {
    	$scope.reportIds = [];
        $scope.page = 1;
        $scope.cursor = null;
        $scope.invalidQuerySearch = null;
        $scope.spinneractive = false;
        $scope.invalidName = null;
        $scope.userId = ($stateParams.id != null && $stateParams.id !== undefined) ? $stateParams.id : AppConstant.ACCOUNT.id;
        //$scope.startcounter = 0;
        $scope.loadAll = function() {
     	   if (!AppConstant.REPORTID_ENDPOINT_LOADED) {
     		   ReportId.init().then(function(){
 					if (AppConstant.REPORTID_ENDPOINT_LOADED) {
 						console.log("reportIdendpoint loaded...")
 						listData($scope.userId, null);
 					}
 				},
 				function(){
 					console.log(ErrorCode.ERROR_INIT_ENDPOINT_SERVICE);
 				});
     	   } else {
     		   listData($scope.userId, null);
     	   }
     	   
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.reportIds = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            if ($scope.searchQuery != null) {
         	   $scope.search();
            } else {
         	   if ($scope.cursor != null) {
             	   listData($scope.cursor);
                }
            }
        };
        
        $scope.showUpdate = function (id) {
            ReportId.get(id).then(function(result) {
                $scope.reportId = result;
                $('#saveReportIdModal').modal('show');
            });
        };
        
        function listData(userId, cursor) {
     	   $scope.startSpin();
     	   ReportId.loadAll(userId, cursor).then(function(data) {
     		   $scope.stopSpin();
     		   if (data != null) {
     			   if (data.items != null) {
 	    			   for (var i = 0; i < data.items.length; i++) {
 	    				  $scope.reportIds.push(new AppUtils().convert(data.items[i]));
 	    			   }
 	    			   $scope.cursor = data.nextPageToken;
     			   }
     		   }
     	   });
        };

        $scope.save = function () {
        	$scope.reportId.userId = $scope.userId;
            if ($scope.reportId.id != null) {
                ReportId.update($scope.reportId).then(function (data){
                	if (data.error != null) {
                		showError(data.message);
             	   	} else {
             	   		$scope.refresh();
             	   	}
                });
            } else {
         	   ReportId.insert($scope.reportId).then(function (data){
         		   if (data.error != null) {
         			  showError(data.message);
	           	   } else {
	           		   $scope.refresh();
	           	   }
                });
            }
        };

        $scope.delete = function (id) {
     	   ReportId.get(id).then(function (data){
     		   $scope.reportId = data;
     		   $('#deleteReportIdConfirmation').modal('show');
     	   });
        };

        $scope.confirmDelete = function (id) {
            ReportId.delete(id).then(function (data){
         	   $scope.reset();
                $('#deleteReportIdConfirmation').modal('hide');
                $scope.clear();
     	   });
        };

        $scope.search = function () {
     	   $scope.invalidQuerySearch = null;
     	   if ($scope.searchQuery != null && $scope.searchQuery != '') {
     		  $scope.startSpin();
     		  var userId = $scope.userId;
			   ReportIdSearch.searchReportId($scope.searchQuery, $scope.cursor, userId).then(function(data) {
				   $scope.stopSpin();
	    		   if ($scope.cursor == null) {
	    			   $scope.reportIds = [];
	    		   }
	    		   if (data != null) {
	    			   for (var i = 0; i < data.items.length; i++) {
	                       $scope.reportIds.push(new AppUtils().convert(data.items[i]));
	      			   }
	    			   $scope.cursor = data.nextPageToken;
	    		   }
	       		}, 
	       		function(response) {
	               if(response.status === 404) {
	                   $scope.loadAll();
	               }
	       		});
     	   } else {
     		   listData($scope.userId, null);
     	   }
        };
        
        $scope.refresh = function () {
            $scope.reset();
            $('#saveReportIdModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.reportId = {week: new AppUtils().getWeek(), year: new Date().getFullYear(), id: null, status:0};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
            $scope.invalidName = null;
        };
        
        $scope.change = function() {
     	   $scope.cursor = null;
        };
        
        $scope.startSpin = function() {
     	   if (!$scope.spinneractive) {
     		   usSpinnerService.spin('spinner-1');
     		   //$scope.startcounter++;
     	   }
        };

 	   $scope.stopSpin = function() {
 		   if ($scope.spinneractive) {
 			   usSpinnerService.stop('spinner-1');
 		   }
 	   };

 	   $rootScope.$on('us-spinner:spin', function(event, key) {
 		   $scope.spinneractive = true;
 	   });

 	   $rootScope.$on('us-spinner:stop', function(event, key) {
 		   $scope.spinneractive = false;
 	   });
 	   
 	   function showError(errorMsg) {
 		   if (errorMsg.indexOf('[602]') != -1) {
 			   $scope.invalidName = 'ERROR';
 		   } else if (errorMsg.indexOf('[606]') != -1) {
 			   $scope.insertError = 'ERROR';
 		   } else {
 			  $scope.insertError = true;
 		   }
	   };
 	   
 	   function startTimer() {
            //var timer = $timeout(function () {
         	   $scope.stopSpin();
            //}, 6000);
        }
 	   
        $scope.loadAll();

    });