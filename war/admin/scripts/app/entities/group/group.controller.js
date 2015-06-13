'use strict';

angular.module('jhipsterApp')
    .controller('GroupController', function ($scope, $rootScope, $timeout, usSpinnerService, Group, GroupSearch, ParseLinks) {
    	$scope.groups = [];
        $scope.page = 1;
        $scope.cursor = null;
        $scope.invalidQuerySearch = null;
        $scope.spinneractive = false;
        //$scope.startcounter = 0;
        $scope.loadAll = function() {
     	   if (!AppConstant.GROUP_ENDPOINT_LOADED) {
     		   Group.init().then(function(){
 					if (AppConstant.GROUP_ENDPOINT_LOADED) {
 						console.log("groupendpoint loaded...")
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
            $scope.groups = [];
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
            Group.get(id).then(function(result) {
                $scope.group = result;
                $('#saveGroupModal').modal('show');
            });
        };
        
        function listData(cursor) {
     	   $scope.startSpin();
     	   Group.loadAll(cursor).then(function(data) {
     		   $scope.stopSpin();
     		   if (data != null) {
     			   if (data.items != null) {
 	    			   for (var i = 0; i < data.items.length; i++) {
 	                     $scope.groups.push(data.items[i]);
 	    			   }
 	    			   $scope.cursor = data.nextPageToken;
     			   }
     		   }
     	   });
        };

        $scope.save = function () {
            if ($scope.group.id != null) {
            	group.updUid = AppConstant.ACCOUNT.login;
                Group.update($scope.group).then(function (data){
                	if (data.error != null) {
             		   showError(data.code);
             	   } else {
             		   $scope.refresh();
             	   }
                });
            } else {
            	group.crtUid = AppConstant.ACCOUNT.login;
    			group.updUid = AppConstant.ACCOUNT.login;
         	   	Group.insert($scope.group).then(function (data){
         		  if (data.error != null) {
	           		   showError(data.code);
	           	   } else {
	           		   $scope.refresh();
	           	   }
                });
            }
        };

        $scope.delete = function (id) {
     	   Group.get(id).then(function (data){
     		   $scope.group = data;
     		   $('#deleteGroupConfirmation').modal('show');
     	   });
        };

        $scope.confirmDelete = function (id) {
            Group.delete(id).then(function (data){
         	   $scope.reset();
                $('#deleteGroupConfirmation').modal('hide');
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
    					   Group.get(id).then(function(data){
    						   startTimer();
    						   $scope.groups = [];
    						   if (data != null) {
    							   $scope.groups.push(data.result);
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
    			   GroupSearch.searchGroup($scope.searchQuery, $scope.cursor).then(function(data) {
    				   $scope.stopSpin();
    	    		   if ($scope.cursor == null) {
    	    			   $scope.groups = [];
    	    		   }
    	    		   if (data != null) {
    	    			   for (var i = 0; i < data.items.length; i++) {
    	                       $scope.groups.push(data.items[i]);
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
            $('#saveGroupModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.group = {grpName: null, delFlag: null, crtUid: null, ctrTms: null, updUid: null, updTms: null, id: null};
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
        };
        
        function showError(errorCode) {
 		   if (errorCode == 409) {
 			   $scope.invalidName = 'ERROR';
 		   }
        };
        
        $scope.loadAll();

    });
