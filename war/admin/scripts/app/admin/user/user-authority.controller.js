'use strict';

angular.module('jhipsterApp')
    .controller('UserAuthorityController', function ($scope, $rootScope, $stateParams, usSpinnerService, User, Authority, ParseLinks) {
        $scope.authoritys = [];
        $scope.page = 1;
        $scope.allAuthorities = [];
        $scope.loadAll = function() {
        	listData($stateParams.id);
        };
        
        function listData(userId) {
      	   $scope.startSpin();
      	   User.listAuthority(userId).then(function(data) {
      		   $scope.stopSpin();
      		   if (data != null) {
      			   if (data.items != null) {
  	    			   for (var i = 0; i < data.items.length; i++) {
  	                     $scope.authoritys.push(data.items[i]);
  	    			   }
  	    			   $scope.cursor = data.nextPageToken;
      			   }
      		   }
      	   });
         };
         
         function getAuthorities() {
        	 if (!AppConstant.AUTHORITY_ENDPOINT_LOADED) {
        		 Authority.init().then(function(data) {
        			 AppConstant.AUTHORITY_ENDPOINT_LOADED = true;
        			 listAuthorities();
        		 });
			} else {
				listAuthorities();
			}
         };
         
         function listAuthorities() {
        	 Authority.loadAll().then(function(data) {
        		 if (data != null) {
        			   if (data.items != null) {
    	    			   for (var i = 0; i < data.items.length; i++) {
    	                     $scope.allAuthorities.push(data.items[i]);
    	    			   }
        			   }
        		   }
        	 });
         };
         
        
        $scope.reset = function() {
            $scope.page = 1;
            $scope.authoritys = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            //$scope.page = page;
            //$scope.loadAll();
        };

        $scope.showUpdate = function (id) {
            Authority.get({id: id}, function(result) {
                $scope.authority = result;
                $('#saveAuthorityModal').modal('show');
            });
        };

        $scope.save = function () {
        	$scope.startSpin();
            User.addAuthority($scope.user.userId, $scope.user.role).then(function(data) {
            	$scope.stopSpin();
            	if (data != null) {
            		$scope.authoritys = [];
            		$scope.refresh();
				}
            });
        };

        $scope.delete = function (role) {
//            Authority.get({id: id}, function(result) {
                $scope.authority = {userId: $stateParams.id, role:role};
                $('#deleteAuthorityConfirmation').modal('show');
//            });
        };

        $scope.confirmDelete = function (role) {
        	$scope.startSpin();
        	User.removeAuthority($scope.authority.userId, $scope.authority.role).then(function(data) {
            	$scope.stopSpin();
            	if (data != null) {
            		$scope.authoritys = [];
            		$scope.reset();
                    $('#deleteAuthorityConfirmation').modal('hide');
                    $scope.clear();
				}
            });
        };

        $scope.refresh = function () {
            $scope.reset();
            $('#saveAuthorityModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.user = {userId: $stateParams.id, role:null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
        
        $scope.startSpin = function() {
     	   	if (!$scope.spinneractive) {
     		   usSpinnerService.spin('spinner-1');
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
 	   	
 	   	getAuthorities();
 	   	$scope.loadAll();
    });
