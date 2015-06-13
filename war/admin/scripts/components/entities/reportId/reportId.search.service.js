'use strict';

angular.module('jhipsterApp')
   .factory('ReportIdSearch', function ($resource, $q) {
	   return {
		   searchReportId: function(querySearch, cursor){
			    var p=$q.defer();
	   			var requestData = {};
	   			requestData.cursor = cursor;
	   			requestData.count = AppConstant.MAX_PAGE_SIZE;
	   			requestData.querySearch = querySearch;
	   			gapi.client.reportidendpoint.searchReportId(requestData).execute(function(resp) {
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