'use strict';

angular.module('jhipsterApp')
    .controller('ReportIdController', function ($scope, $timeout, $rootScope, usSpinnerService, ReportId, User, ReportIdSearch, ParseLinks) {
    	$scope.reportIds = [];
        $scope.page = 1;
        $scope.cursor = null;
        $scope.invalidQuerySearch = null;
        $scope.spinneractive = false;
        $scope.invalidName = null;
        //$scope.startcounter = 0;
        $scope.loadAll = function() {
     	   if (!AppConstant.REPORTID_ENDPOINT_LOADED) {
     		   ReportId.init().then(function(){
 					if (AppConstant.REPORTID_ENDPOINT_LOADED) {
 						console.log("reportIdendpoint loaded...")
 						listData(null);
 					}
 				},
 				function(){
 					console.log(ErrorCode.ERROR_INIT_ENDPOINT_SERVICE);
 				});
     	   } else {
     		   listData(null);
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
        
        function listData(cursor) {
     	   $scope.startSpin();
     	   ReportId.loadAll(cursor).then(function(data) {
     		   $scope.stopSpin();
     		   if (data != null) {
     			   if (data.items != null) {
 	    			   for (var i = 0; i < data.items.length; i++) {
 	                     $scope.reportIds.push(data.items[i]);
 	    			   }
 	    			   $scope.cursor = data.nextPageToken;
     			   }
     		   }
     	   });
        };

        $scope.save = function () {
        	$scope.reportId.userId = AppConstant.ACCOUNT.id;
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
     		  if ($scope.searchQuery.indexOf('id:') != -1) {
    			   var query = $scope.searchQuery.split(':', 2);
    			   try {
    				   var id = parseInt(query[1]);
    				   if (!isNaN(id)) {
    					   $scope.startSpin();
    					   ReportId.get(id).then(function(data){
    						   startTimer();
    						   $scope.reportIds = [];
    						   if (data != null) {
    							   $scope.reportIds.push(data.result);
    						   }
    					   });
    				   } else {
    					   $scope.invalidQuerySearch = 'ERROR';
    				   }
    				} catch (e) {
    					$scope.invalidQuerySearch = 'ERROR';
    				}
    			   
    			   
    		   } else {
    			   $scope.startSpin();
    			   ReportIdSearch.searchReportId($scope.searchQuery, $scope.cursor).then(function(data) {
    				   $scope.stopSpin();
    	    		   if ($scope.cursor == null) {
    	    			   $scope.reportIds = [];
    	    		   }
    	    		   if (data != null) {
    	    			   for (var i = 0; i < data.items.length; i++) {
    	                       $scope.reportIds.push(data.items[i]);
    	      			   }
    	    			   $scope.cursor = data.nextPageToken;
    	    		   }
    	       		}, 
    	       		function(response) {
    	               if(response.status === 404) {
    	                   $scope.loadAll();
    	               }
    	       		});
    		   }
     	   } else {
     		   listData(null);
     	   }
        };

        $scope.refresh = function () {
            $scope.reset();
            $('#saveReportIdModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.reportId = {week: null, year: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
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