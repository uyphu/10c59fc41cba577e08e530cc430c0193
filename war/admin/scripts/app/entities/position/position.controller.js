'use strict';

angular.module('jhipsterApp')
   .controller('PositionController', function ($scope, $rootScope, $timeout, usSpinnerService, Position, PositionSearch, ParseLinks) {
       $scope.positions = [];
       $scope.page = 1;
       $scope.cursor = null;
       $scope.invalidQuerySearch = null;
       $scope.spinneractive = false;
       $scope.invalidName = null;
       //$scope.startcounter = 0;
       $scope.loadAll = function() {
    	   if (!AppConstant.POSITION_ENDPOINT_LOADED) {
    		   Position.init().then(function(){
					if (AppConstant.POSITION_ENDPOINT_LOADED) {
						console.log("positionendpoint loaded...")
						//listData(null);
						listData($scope.cursor, AppConstant.MAX_PAGE_SIZE)
					}
				},
				function(){
					console.log(ErrorCode.ERROR_INIT_ENDPOINT_SERVICE);
				});
    	   } else {
    		   //listData(null);
    		   listData($scope.cursor, AppConstant.MAX_PAGE_SIZE)
    	   }
    	   
       };
       $scope.reset = function() {
           $scope.page = 1;
           $scope.cursor = null;
           $scope.positions = [];
           $scope.loadAll();
       };
       $scope.loadPage = function(page) {
           $scope.page = page;
           if ($scope.searchQuery != null) {
        	   $scope.search();
           } else {
        	   if ($scope.cursor != null) {
            	   //listData($scope.cursor);
        		   listData($scope.cursor, AppConstant.MAX_PAGE_SIZE);
               }
           }
       };
       
       $scope.showUpdate = function (id) {
           Position.get(id).then(function(result) {
               $scope.position = result;
               $('#savePositionModal').modal('show');
           });
       };
       
       function listData(cursor, count) {
    	   $scope.startSpin();
    	   Position.loadAll(cursor, count).then(function(data) {
    		   $scope.stopSpin();
    		   if (data != null) {
    			   if (data.items != null) {
	    			   for (var i = 0; i < data.items.length; i++) {
	                     $scope.positions.push(data.items[i]);
	    			   }
	    			   $scope.cursor = data.nextPageToken;
    			   }
    		   }
    	   });
       };

       $scope.save = function () {
           if ($scope.position.id != null) {
        	   $scope.position.updUid = AppConstant.ACCOUNT.login;
               Position.update($scope.position).then(function (data){
            	   if (data.error != null) {
            		   showError(data.code);
            	   } else {
            		   $scope.refresh();
            	   }
               });
           } else {
        	   $scope.position.crtUid = AppConstant.ACCOUNT.login;
        	   $scope.position.updUid = AppConstant.ACCOUNT.login;
        	   Position.insert($scope.position).then(function (data){
        		   if (data.error != null) {
            		   showError(data.code);
            	   } else {
            		   $scope.refresh();
            	   }
               });
           }
       };

       $scope.delete = function (id) {
    	   Position.get(id).then(function (data){
    		   $scope.position = data;
    		   $('#deletePositionConfirmation').modal('show');
    	   });
       };

       $scope.confirmDelete = function (id) {
           Position.delete(id).then(function (data){
        	   $scope.reset();
               $('#deletePositionConfirmation').modal('hide');
               $scope.clear();
    	   });
       };

       $scope.search = function () {
    	   $scope.invalidQuerySearch = null;
    	   if ($scope.cursor == null) {
     		   $scope.positions = [];
     	   }
    	   if ($scope.searchQuery != null && $scope.searchQuery != '') {
    		   if ($scope.searchQuery.indexOf('id:') != -1) {
    			   var query = $scope.searchQuery.split(':', 2);
    			   try {
    				   var id = parseInt(query[1]);
    				   if (!isNaN(id)) {
    					   $scope.startSpin();
    					   Position.get(id).then(function(data){
    						   startTimer();
    						   $scope.positions = [];
    						   if (data != null) {
    							   $scope.positions.push(data.result);
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
    			   PositionSearch.searchPosition($scope.searchQuery, $scope.cursor).then(function(data) {
    				   $scope.stopSpin();
    	    		   if ($scope.cursor == null) {
    	    			   $scope.positions = [];
    	    		   }
    	    		   if (data != null) {
    	    			   for (var i = 0; i < data.items.length; i++) {
    	                       $scope.positions.push(data.items[i]);
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
    		   listData($scope.cursor, AppConstant.MAX_PAGE_SIZE);
    	   }
       };

       $scope.refresh = function () {
           $scope.reset();
           $('#savePositionModal').modal('hide');
           $scope.clear();
       };

       $scope.clear = function () {
    	   $scope.invalidName = null;
           $scope.position = {postName: null, delFlag: null, crtUid: null, id: null};
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