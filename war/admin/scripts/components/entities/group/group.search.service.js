'use strict';

angular.module('jhipsterApp')
    .factory('GroupSearch', function ($resource, $q) {
    	return {
 		   searchGroup: function(querySearch, cursor){
 			    var p=$q.defer();
 	   			var requestData = {};
 	   			requestData.cursor = cursor;
 	   			requestData.count = AppConstant.MAX_PAGE_SIZE;
 	   			requestData.querySearch = querySearch;
 	   			gapi.client.groupendpoint.searchGroup(requestData).execute(function(resp) {
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
