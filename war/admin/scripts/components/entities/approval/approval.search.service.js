'use strict';

angular.module('jhipsterApp')
    .factory('ApprovalSearch', function ($resource, $q) {
    	return {
 		   searchUser: function(querySearch, cursor){
 			    var p=$q.defer();
 	   			var requestData = {};
 	   			requestData.cursor = cursor;
 	   			requestData.count = AppConstant.MAX_PAGE_SIZE;
 	   			requestData.querySearch = querySearch;
 	   			gapi.client.userendpoint.searchUser(requestData).execute(function(resp) {
 	                if (resp != null && resp.items != null) {
 	                   	p.resolve(resp);
 	   				} else {
 	   					p.resolve(null);
 	   				}
 	   			});
 	   			return p.promise;
 		   }
 	   }
    });
