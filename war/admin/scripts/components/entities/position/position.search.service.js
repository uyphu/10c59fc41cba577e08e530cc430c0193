'use strict';

angular.module('jhipsterApp')
   .factory('PositionSearch', function ($resource, $q) {
//       return $resource('api/_search/positions/:query', {}, {
//           'query': { method: 'GET', isArray: true}
//       });
	   return {
		   searchPosition: function(querySearch, cursor){
			    var p=$q.defer();
	   			var requestData = {};
	   			requestData.cursor = cursor;
	   			requestData.count = AppConstant.MAX_PAGE_SIZE;
	   			requestData.querySearch = querySearch;
	   			gapi.client.positionendpoint.searchPosition(requestData).execute(function(resp) {
	                   if (resp != null) {
	                   	p.resolve(resp.result);
	   				} else {
	   					p.resolve(null);
	   				}
	   			});
	   			return p.promise;
		   }
	   }
   });