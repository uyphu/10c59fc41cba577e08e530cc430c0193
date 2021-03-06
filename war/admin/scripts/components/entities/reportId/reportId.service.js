'use strict';

angular.module('jhipsterApp')
   .factory('ReportId', function ($q, DateUtils) {
	   return {
		   	init: function() {
				var hwdefer=$q.defer();
				var oauthloaddefer=$q.defer();
				var oauthdefer=$q.defer();
				if (!AppConstant.REPORTID_ENDPOINT_LOADED) {
					gapi.client.load('reportidendpoint', AppConstant.ENDPOINT_VERSION, function() {
						AppConstant.REPORTID_ENDPOINT_LOADED = true;
						hwdefer.resolve(gapi);
					}, AppConstant.ROOT_API);
				}
				gapi.client.load(AppConstant.OAUTH2, AppConstant.OAUTH2_VERSION, function(){
					oauthloaddefer.resolve(gapi);
				});
				var chain=$q.all([hwdefer.promise,oauthloaddefer.promise]);
				return chain;
   			},
   			
   			loadAll: function (userId, cursor){
    			var p=$q.defer();
    			var requestData = {};
    			requestData.userId = userId;
    			requestData.cursor = cursor;
    			requestData.count = AppConstant.MAX_PAGE_SIZE;
    			gapi.client.reportidendpoint.listReportId(requestData).execute(function(resp) {
                    if (resp != null) {
                    	p.resolve(resp);
    				} else {
    					p.resolve(null);
    				}
    			});
    			return p.promise;
   			},
   			
   			insert: function (reportId) {
   				var p=$q.defer();
    			gapi.client.reportidendpoint.insertReportId(reportId).execute(function(resp) {
                    if (resp != null) {
                    	p.resolve(resp);
    				} else {
    					p.resolve(null);
    				}
    			});
    			return p.promise;
   			},
   			
   			update: function (reportId) {
   				var p=$q.defer();
    			gapi.client.reportidendpoint.updateReportId(reportId).execute(function(resp) {
                    if (resp != null) {
                    	p.resolve(resp);
    				} else {
    					p.resolve(null);
    				}
    			});
    			return p.promise;
   			},
   			
   			approve: function (id, login) {
   				var p=$q.defer();
   				var requestData = {};
   				requestData.id = id;
    			requestData.login = login;
    			gapi.client.reportidendpoint.approveReportId(requestData).execute(function(resp) {
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
    			gapi.client.reportidendpoint.removeReportId(requestData).execute(function(resp) {
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
    			gapi.client.reportidendpoint.getReportId(requestData).execute(function(resp) {
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