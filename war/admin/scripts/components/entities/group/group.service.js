'use strict';

angular.module('jhipsterApp')
    .factory('Group', function ($q, DateUtils) {
    	return {
		   	init: function() {
				var hwdefer=$q.defer();
				var oauthloaddefer=$q.defer();
				var oauthdefer=$q.defer();
				if (!AppConstant.POSITION_ENDPOINT_LOADED) {
					gapi.client.load('groupendpoint', AppConstant.ENDPOINT_VERSION, function() {
						AppConstant.POSITION_ENDPOINT_LOADED = true;
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
    			gapi.client.groupendpoint.listGroup(requestData).execute(function(resp) {
                    if (resp != null) {
                    	p.resolve(resp);
    				} else {
    					p.resolve(null);
    				}
    			});
    			return p.promise;
   			},
   			
   			insert: function (group) {
   				var p=$q.defer();
    			group.crtUid = AppConstant.ACCOUNT.login;
    			group.updUid = AppConstant.ACCOUNT.login;
    			gapi.client.groupendpoint.insertGroup(group).execute(function(resp) {
                    if (resp != null) {
                    	p.resolve(resp.result);
    				} else {
    					p.resolve(null);
    				}
    			});
    			return p.promise;
   			},
   			
   			update: function (group) {
   				var p=$q.defer();
    			group.updUid = AppConstant.ACCOUNT.login;
    			gapi.client.groupendpoint.updateGroup(group).execute(function(resp) {
                    if (resp != null) {
                    	p.resolve(resp.result);
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
    			gapi.client.groupendpoint.removeGroup(requestData).execute(function(resp) {
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
    			gapi.client.groupendpoint.getGroup(requestData).execute(function(resp) {
                    if (resp != null) {
                    	p.resolve(resp.result);
    				} else {
    					p.resolve(null);
    				}
    			});
    			return p.promise;
   			}
	   };
    });
