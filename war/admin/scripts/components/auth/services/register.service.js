'use strict';

angular.module('jhipsterApp')
	.factory('Register', function ($q) {
		return {
			save: function(account) {
				var p=$q.defer();
		    	gapi.client.userendpoint.insertUser(account).execute(function(resp){
		    		if (resp != null) {
		    			//console.log(resp)
		        		p.resolve(resp);
					} else {
						p.resolve(null);
					}
		    	});
		    	return p.promise;
			}
		};
		
	});
