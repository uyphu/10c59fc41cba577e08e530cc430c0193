'use strict';

angular.module('jhipsterApp')
   .factory('PlanningWeekId', function ($q, DateUtils) {
	   return {
		   	init: function() {
				var hwdefer=$q.defer();
				var oauthloaddefer=$q.defer();
				var oauthdefer=$q.defer();
				if (!AppConstant.PLANNINGWEEKID_ENDPOINT_LOADED) {
					gapi.client.load('planningweekidendpoint', AppConstant.ENDPOINT_VERSION, function() {
						AppConstant.PLANNINGWEEKID_ENDPOINT_LOADED = true;
						hwdefer.resolve(gapi);
					}, AppConstant.ROOT_API);
				}
				gapi.client.load(AppConstant.OAUTH2, AppConstant.OAUTH2_VERSION, function(){
					oauthloaddefer.resolve(gapi);
				});
				var chain=$q.all([hwdefer.promise,oauthloaddefer.promise]);
				return chain;
   			},
   			
   			loadAll: function (cursor){
    			var p=$q.defer();
    			var requestData = {};
    			requestData.cursor = cursor;
    			requestData.count = AppConstant.MAX_PAGE_SIZE;
    			gapi.client.planningweekidendpoint.listPlanningWeekId(requestData).execute(function(resp) {
                    if (resp != null) {
                    	p.resolve(resp);
    				} else {
    					p.resolve(null);
    				}
    			});
    			return p.promise;
   			},
   			
   			insert: function (planningWeekId) {
   				var p=$q.defer();
    			gapi.client.planningweekidendpoint.insertPlanningWeekId(planningWeekId).execute(function(resp) {
                    if (resp != null) {
                    	p.resolve(resp);
    				} else {
    					p.resolve(null);
    				}
    			});
    			return p.promise;
   			},
   			
   			update: function (planningWeekId) {
   				var p=$q.defer();
    			gapi.client.planningweekidendpoint.updatePlanningWeekId(planningWeekId).execute(function(resp) {
                    if (resp != null) {
                    	p.resolve(resp);
    				} else {
    					p.resolve(null);
    				}
    			});
    			return p.promise;
   			},
   			
   			delete: function (id) {
   				var p=$q.defer();
   				var requestData = {};
   				requestData.id = id;
    			gapi.client.planningweekidendpoint.removePlanningWeekId(requestData).execute(function(resp) {
                    if (resp != null) {
                    	p.resolve(resp);
    				} else {
    					p.resolve(null);
    				}
    			});
    			return p.promise;
   			}, 
   			
   			get: function (id) {
   				var p=$q.defer();
   				var requestData = {};
   				requestData.id = id;
    			gapi.client.planningweekidendpoint.getPlanningWeekId(requestData).execute(function(resp) {
                    if (resp != null) {
                    	p.resolve(resp);
    				} else {
    					p.resolve(null);
    				}
    			});
    			return p.promise;
   			}
	   };
   });