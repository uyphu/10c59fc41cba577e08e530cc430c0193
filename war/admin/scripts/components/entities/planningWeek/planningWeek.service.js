'use strict';

angular.module('jhipsterApp')
    .factory('PlanningWeek', function ($resource, $q, DateUtils) {
    	return {
		   	init: function() {
				var hwdefer=$q.defer();
				var oauthloaddefer=$q.defer();
				var oauthdefer=$q.defer();
				if (!AppConstant.PLANNINGWEEK_ENDPOINT_LOADED) {
					gapi.client.load('planningweekendpoint', AppConstant.ENDPOINT_VERSION, function() {
						AppConstant.PLANNINGWEEK_ENDPOINT_LOADED = true;
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
    			gapi.client.planningweekendpoint.listPlanningWeek(requestData).execute(function(resp) {
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
    			gapi.client.planningweekendpoint.insertPlanningWeek(planningWeekId).execute(function(resp) {
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
    			gapi.client.planningweekendpoint.updatePlanningWeek(planningWeekId).execute(function(resp) {
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
    			gapi.client.planningweekendpoint.removePlanningWeek(requestData).execute(function(resp) {
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
    			gapi.client.planningweekendpoint.getPlanningWeek(requestData).execute(function(resp) {
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

