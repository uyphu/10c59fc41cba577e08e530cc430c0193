'use strict';

angular.module('jhipsterApp')
    .controller('ApprovalEditController', function ($scope, $rootScope, $timeout, $translate, $stateParams, 
    		usSpinnerService, User, ParseLinks, Group, Position) {
        $scope.spinneractive = false;
        
        $scope.success = null;
        $scope.error = null;
        $scope.errorEmailExists = null;
        $timeout(function (){angular.element('[ng-model="user.firstName"]').focus();});
        
        $scope.user = {};
        $scope.load = function (id) {
            User.get(id).then(function(result) {
              $scope.user = result;
            });
            
            listGroup(null, null); 
            listPosition(null, null);
        };
        
        $scope.save = function () {
        	if ($scope.user.id != null) {
                User.update($scope.user).then(function (data){
                	if (data.error != null) {
                		$scope.success = null;
                		showError(data.message);
             	   } else {
             		   $scope.success = 'OK';
             	   }
                });
            }
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
        
        function listPosition(cursor, count) {
        	if (!AppConstant.POSITION_ENDPOINT_LOADED) {
     		   Position.init().then(function(){
 					if (AppConstant.POSITION_ENDPOINT_LOADED) {
 						console.log("positionendpoint loaded...")
 						//listData(null);
 						getPositionList(cursor, count);
 					}
 				},
 				function(){
 					console.log(ErrorCode.ERROR_INIT_ENDPOINT_SERVICE);
 				});
     	   } else {
     		   //listData(null);
     		  getPositionList(cursor, count);
     	   }
        };
        
        function listGroup(cursor, count) {
        	if (!AppConstant.GROUP_ENDPOINT_LOADED) {
     		   Group.init().then(function(){
 					if (AppConstant.GROUP_ENDPOINT_LOADED) {
 						console.log("groupendpoint loaded...")
 						//listData(null);
 						getGroupList(cursor, count)
 					}
 				},
 				function(){
 					console.log(ErrorCode.ERROR_INIT_ENDPOINT_SERVICE);
 				});
     	   } else {
     		   //listData(null);
     		  getGroupList(cursor, count);
     	   }
        };
        
        function getPositionList(cursor, count) {
        	Position.loadAll(cursor, count).then(function(data) {
       		   if (data != null) {
       			   if (data.items != null) {
       				   $scope.positions = data.items;
       			   }
       		   }
       	   });
        }
        
        function getGroupList(cursor, count) {
        	Group.loadAll(cursor, count).then(function(data) {
       		   if (data != null) {
       			   if (data.items != null) {
       				   $scope.groups = data.items;
       			   }
       		   }
       	   });
        }
        
        $scope.load($stateParams.id);
    });
