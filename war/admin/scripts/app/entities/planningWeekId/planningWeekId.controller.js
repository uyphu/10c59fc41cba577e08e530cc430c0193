'use strict';

angular.module('jhipsterApp')
    .controller('PlanningWeekIdController', function ($scope, PlanningWeekId, User, PlanningWeek, PlanningWeekIdSearch, ParseLinks) {
        $scope.planningWeekIds = [];
        $scope.users = User.query();
        $scope.planningweeks = PlanningWeek.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            PlanningWeekId.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.planningWeekIds.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.planningWeekIds = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            PlanningWeekId.get({id: id}, function(result) {
                $scope.planningWeekId = result;
                $('#savePlanningWeekIdModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.planningWeekId.id != null) {
                PlanningWeekId.update($scope.planningWeekId,
                    function () {
                        $scope.refresh();
                    });
            } else {
                PlanningWeekId.save($scope.planningWeekId,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            PlanningWeekId.get({id: id}, function(result) {
                $scope.planningWeekId = result;
                $('#deletePlanningWeekIdConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PlanningWeekId.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deletePlanningWeekIdConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PlanningWeekIdSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.planningWeekIds = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.reset();
            $('#savePlanningWeekIdModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.planningWeekId = {week: null, year: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
'use strict';

angular.module('jhipsterApp')
    .controller('PlanningWeekIdController', function ($scope, $timeout, $rootScope, usSpinnerService, PlanningWeekId, User, PlanningWeekIdSearch, ParseLinks) {
    	$scope.planningWeekIds = [];
        $scope.page = 1;
        $scope.cursor = null;
        $scope.invalidQuerySearch = null;
        $scope.spinneractive = false;
        $scope.invalidName = null;
        //$scope.startcounter = 0;
        $scope.loadAll = function() {
     	   if (!AppConstant.PLANNINGWEEKID_ENDPOINT_LOADED) {
     		   PlanningWeekId.init().then(function(){
 					if (AppConstant.PLANNINGWEEKID_ENDPOINT_LOADED) {
 						console.log("planningWeekIdendpoint loaded...")
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
            $scope.planningWeekIds = [];
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
            PlanningWeekId.get(id).then(function(result) {
                $scope.planningWeekId = result;
                $('#savePlanningWeekIdModal').modal('show');
            });
        };
        
        function listData(cursor) {
     	   $scope.startSpin();
     	   PlanningWeekId.loadAll(cursor).then(function(data) {
     		   $scope.stopSpin();
     		   if (data != null) {
     			   if (data.items != null) {
 	    			   for (var i = 0; i < data.items.length; i++) {
 	                     $scope.planningWeekIds.push(data.items[i]);
 	    			   }
 	    			   $scope.cursor = data.nextPageToken;
     			   }
     		   }
     	   });
        };

        $scope.save = function () {
        	$scope.planningWeekId.userId = AppConstant.ACCOUNT.id;
            if ($scope.planningWeekId.id != null) {
                PlanningWeekId.update($scope.planningWeekId).then(function (data){
                	if (data.error != null) {
             		   showError(data.code);
             	   	} else {
             		   $scope.refresh();
             	   	}
                });
            } else {
         	   PlanningWeekId.insert($scope.planningWeekId).then(function (data){
         		   if (data.error != null) {
	           		   showError(data.code);
	           	   } else {
	           		   $scope.refresh();
	           	   }
                });
            }
        };

        $scope.delete = function (id) {
     	   PlanningWeekId.get(id).then(function (data){
     		   $scope.planningWeekId = data;
     		   $('#deletePlanningWeekIdConfirmation').modal('show');
     	   });
        };

        $scope.confirmDelete = function (id) {
            PlanningWeekId.delete(id).then(function (data){
         	   $scope.reset();
                $('#deletePlanningWeekIdConfirmation').modal('hide');
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
    					   PlanningWeekId.get(id).then(function(data){
    						   startTimer();
    						   $scope.planningWeekIds = [];
    						   if (data != null) {
    							   $scope.planningWeekIds.push(data.result);
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
    			   PlanningWeekIdSearch.searchPlanningWeekId($scope.searchQuery, $scope.cursor).then(function(data) {
    				   $scope.stopSpin();
    	    		   if ($scope.cursor == null) {
    	    			   $scope.planningWeekIds = [];
    	    		   }
    	    		   if (data != null) {
    	    			   for (var i = 0; i < data.items.length; i++) {
    	                       $scope.planningWeekIds.push(data.items[i]);
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
            $('#savePlanningWeekIdModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.planningWeekId = {week: null, year: null, id: null};
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
 	   
 	   function startTimer() {
            //var timer = $timeout(function () {
         	   $scope.stopSpin();
            //}, 6000);
        }
 	   
 	   function showError(errorCode) {
		   if (errorCode == 409) {
			   $scope.invalidName = 'ERROR';
		   }
 	   };
        
        $scope.loadAll();

    });