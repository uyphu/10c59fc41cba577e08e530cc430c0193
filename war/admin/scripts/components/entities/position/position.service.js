'use strict';

angular.module('jhipsterApp')
   .factory('Position', function ($q, DateUtils) {
	   return {
		   	init: function() {
				var hwdefer=$q.defer();
				var oauthloaddefer=$q.defer();
				var oauthdefer=$q.defer();
				if (!AppConstant.POSITION_ENDPOINT_LOADED) {
					gapi.client.load('positionendpoint', AppConstant.ENDPOINT_VERSION, function() {
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
   			
   			loadAll: function (cursor, count){
    			var p=$q.defer();
    			var requestData = {};
    			requestData.cursor = cursor;
    			requestData.count = count;
    			gapi.client.positionendpoint.listPosition(requestData).execute(function(resp) {
                    if (resp != null) {
                    	p.resolve(resp);
    				} else {
    					p.resolve(null);
    				}
    			});
    			return p.promise;
   			},
   			
   			insert: function (position) {
   				var p=$q.defer();
    			gapi.client.positionendpoint.insertPosition(position).execute(function(resp) {
                    if (resp != null) {
                    	p.resolve(resp);
    				} else {
    					p.resolve(null);
    				}
    			});
    			return p.promise;
   			},
   			
   			update: function (position) {
   				var p=$q.defer();
    			gapi.client.positionendpoint.updatePosition(position).execute(function(resp) {
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
    			gapi.client.positionendpoint.removePosition(requestData).execute(function(resp) {
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
    			gapi.client.positionendpoint.getPosition(requestData).execute(function(resp) {
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