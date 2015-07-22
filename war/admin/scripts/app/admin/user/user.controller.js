'use strict';

angular.module('jhipsterApp')
    .controller('UserController', function ($scope, $rootScope, $timeout, $translate, usSpinnerService, User, UserSearch, ParseLinks) {
    	$scope.users = [];
        $scope.page = 1;
        $scope.cursor = null;
        $scope.invalidQuerySearch = null;
        $scope.spinneractive = false;
        
        $scope.orderby = 'login';
        $scope.reverse = false;
        
        $scope.success = null;
        $scope.error = null;
        $scope.doNotMatch = null;
        $scope.errorUserExists = null;
        $scope.errorUserAndEmailExists = null;
        $scope.errorAccountExists = null;
        $scope.registerAccount = {};
        $timeout(function (){angular.element('[ng-model="user.login"]').focus();});
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
        	
        	if ($scope.user.password !== $scope.confirmPassword) {
                $scope.doNotMatch = 'ERROR';
            } else {
                $scope.user.langKey = $translate.use();
                $scope.doNotMatch = null;
                $scope.error = null;
                $scope.errorUserExists = null;
                $scope.errorEmailExists = null;
                $scope.errorUserAndEmailExists = null;
                $scope.errorAccountExists = null;
                
                if ($scope.user.id != null) {
                    User.update($scope.user).then(function (data){
                    	if (data.error != null) {
                    		$scope.success = null;
                    		showError(data.message);
                 	   } else {
                 		   $scope.success = 'OK';
                 		   $scope.refresh();
                 	   }
                    });
                } else {
             	   	User.insert($scope.user).then(function (data){
             		  if (data.error != null) {
             			  $scope.success = null;
             			  showError(data.message);
    	           	   } else {
    	           		   $scope.success = 'OK';
    	           		   $scope.refresh();
    	           	   }
                    });
                }
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
     	   if ($scope.cursor == null) {
     		   $scope.users = [];
     	   }
     	   
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
        	$scope.success = null;
            $scope.error = null;
            $scope.doNotMatch = null;
            $scope.errorUserExists = null;
            $scope.errorUserAndEmailExists = null;
            $scope.errorAccountExists = null;
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
 	   	
 	   $scope.setOrder = function (orderby) {
           if (orderby === $scope.orderby) {
        	   $scope.reverse = !$scope.reverse;
           }
           $scope.orderby = orderby;
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
        
        function showError(errorMsg) {
        	if (errorMsg != null) {
				if (errorMsg.indexOf('[602]') != -1) {
					//account already exists
					$scope.errorAccountExists = 'ERROR';
				} else if (errorMsg.indexOf('[610]') != -1) {
					//user and email already exists
					$scope.errorUserAndEmailExists = 'ERROR';
				} else if (errorMsg.indexOf('[608]') != -1) {
					//login already in use
					$scope.errorUserExists = 'ERROR';
				} else if (errorMsg.indexOf('[609]') != -1) {
					//e-mail address already in use
					$scope.errorEmailExists = 'ERROR';
				} else {
					$scope.error = 'ERROR';
				}
			}
        };
        
        $scope.loadAll();

    });
