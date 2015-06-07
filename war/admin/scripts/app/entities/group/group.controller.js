'use strict';

angular.module('jhipsterApp')
    .controller('GroupController', function ($scope, Group, GroupSearch, ParseLinks) {
    	$scope.groups = [];
        $scope.page = 1;
        $scope.cursor = null;
        $scope.loadAll = function() {
     	   if (!AppConstant.POSITION_ENDPOINT_LOADED) {
     		   Group.init().then(function(){
 					if (AppConstant.POSITION_ENDPOINT_LOADED) {
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
     	   Group.loadAll(cursor).then(function(data) {
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
                Group.update($scope.group).then(function (data){
             	   $scope.refresh();
                });
            } else {
         	   Group.insert($scope.group).then(function (data){
             	   $scope.refresh();
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
     	   GroupSearch.searchGroup($scope.searchQuery, $scope.cursor).then(function(data) {
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
        
        $scope.loadAll();

    });
