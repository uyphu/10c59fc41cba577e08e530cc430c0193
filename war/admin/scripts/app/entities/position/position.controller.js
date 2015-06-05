'use strict';

angular.module('jhipsterApp')
   .controller('PositionController', function ($scope, Position, PositionSearch, ParseLinks) {
       $scope.positions = [];
       $scope.page = 1;
       $scope.cursor = null;
       $scope.loadAll = function() {
    	   if (!AppConstant.POSITION_ENDPOINT_LOADED) {
    		   Position.init().then(function(){
					if (AppConstant.POSITION_ENDPOINT_LOADED) {
						console.log("positionendpoint loaded...")
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
           $scope.positions = [];
           $scope.loadAll();
       };
       $scope.loadPage = function(page) {
           $scope.page = page;
           if ($scope.cursor != null) {
        	   listData($scope.cursor);
           }
       };
       
       $scope.showUpdate = function (id) {
           Position.get(id).then(function(result) {
               $scope.position = result;
               $('#savePositionModal').modal('show');
           });
       };
       
       function listData(cursor) {
    	   Position.loadAll(cursor).then(function(data) {
    		   if (data != null) {
    			   for (var i = 0; i < data.items.length; i++) {
                     $scope.positions.push(data.items[i]);
    			   }
    			   $scope.cursor = data.nextPageToken;
    		   }
    	   });
       };

       $scope.save = function () {
           if ($scope.position.id != null) {
               Position.update($scope.position).then(function (data){
            	   $scope.refresh();
               });
           } else {
        	   Position.insert($scope.position).then(function (data){
            	   $scope.refresh();
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
//           PositionSearch.query({query: $scope.searchQuery}, function(result) {
//               $scope.positions = result;
//           }, function(response) {
//               if(response.status === 404) {
//                   $scope.loadAll();
//               }
//           });
    	   PositionSearch.searchPosition($scope.searchQuery, null).then(function(result) {
               $scope.positions = result;
           		}, function(response) {
	               if(response.status === 404) {
	                   $scope.loadAll();
	               }
           });
       };

       $scope.refresh = function () {
           $scope.reset();
           $('#savePositionModal').modal('hide');
           $scope.clear();
       };

       $scope.clear = function () {
           $scope.position = {postName: null, delFlag: null, crtUid: null, id: null};
           $scope.editForm.$setPristine();
           $scope.editForm.$setUntouched();
       };
       
       $scope.loadAll();
   });