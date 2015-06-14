'use strict';

angular.module('jhipsterApp')
   .factory('PlanningWeekIdSearch', function ($resource, $q) {
	   return {
		   searchPlanningWeekId: function(querySearch, cursor){
			    var p=$q.defer();
	   			var requestData = {};
	   			requestData.cursor = cursor;
	   			requestData.count = AppConstant.MAX_PAGE_SIZE;
	   			requestData.querySearch = querySearch;
	   			gapi.client.planningeekidendpoint.searchPlanningWeekId(requestData).execute(function(resp) {
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