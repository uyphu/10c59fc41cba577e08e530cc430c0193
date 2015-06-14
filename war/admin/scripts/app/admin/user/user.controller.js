'use strict';

angular.module('jhipsterApp')
    .controller('UserController', function ($scope, $rootScope, $timeout, usSpinnerService, User, UserSearch, ParseLinks) {
    	$scope.users = [];
        $scope.page = 1;
        $scope.cursor = null;
        $scope.invalidQuerySearch = null;
        $scope.spinneractive = false;
        //$scope.startcounter = 0;
        $scope.loadAll = function() {
     	   if (!AppConstant.USER_PROFILE_ENDPOINT_LOADED) {
     		   User.init().then(function(){
 					if (AppConstant.USER_PROFILE_ENDPOINT_LOADED) {
 						console.log("userendpoint loaded...")
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
            $scope.users = [];
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
            User.get(id).then(function(result) {
                $scope.user = result;
                $('#saveUserModal').modal('show');
            });
        };
        
        function listData(cursor) {
     	   $scope.startSpin();
     	   User.loadAll(cursor).then(function(data) {
     		   $scope.stopSpin();
     		   if (data != null) {
     			   if (data.items != null) {
 	    			   for (var i = 0; i < data.items.length; i++) {
 	                     $scope.users.push(data.items[i]);
 	    			   }
 	    			   $scope.cursor = data.nextPageToken;
     			   }
     		   }
     	   });
        };

        $scope.save = function () {
            if ($scope.user.id != null) {
            	user.updUid = AppConstant.ACCOUNT.login;
                User.update($scope.user).then(function (data){
                	if (data.error != null) {
             		   showError(data.code);
             	   } else {
             		   $scope.refresh();
             	   }
                });
            } else {
            	user.crtUid = AppConstant.ACCOUNT.login;
    			user.updUid = AppConstant.ACCOUNT.login;
         	   	User.insert($scope.user).then(function (data){
         		  if (data.error != null) {
	           		   showError(data.code);
	           	   } else {
	           		   $scope.refresh();
	           	   }
                });
            }
        };

        $scope.delete = function (id) {
     	   User.get(id).then(function (data){
     		   $scope.user = data;
     		   $('#deleteUserConfirmation').modal('show');
     	   });
        };

        $scope.confirmDelete = function (id) {
            User.delete(id).then(function (data){
         	   $scope.reset();
                $('#deleteUserConfirmation').modal('hide');
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
    					   User.get(id).then(function(data){
    						   startTimer();
    						   $scope.users = [];
    						   if (data != null) {
    							   $scope.users.push(data.result);
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
    			   UserSearch.searchUser($scope.searchQuery, $scope.cursor).then(function(data) {
    				   $scope.stopSpin();
    	    		   if ($scope.cursor == null) {
    	    			   $scope.users = [];
    	    		   }
    	    		   if (data != null) {
    	    			   for (var i = 0; i < data.items.length; i++) {
    	                       $scope.users.push(data.items[i]);
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
            $('#saveUserModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.user = {login: null, email: null, ctrTms: null, password: null, id: null};
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
