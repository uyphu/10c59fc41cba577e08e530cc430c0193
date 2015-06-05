'use strict';

angular.module('jhipsterApp')
.factory('Register', function ($q, $resource) {
//    return $resource('api/register', {}, {
//    });
	return {
		save: function(account) {
			var p=$q.defer();
	    	gapi.client.userendpoint.insertUser(account).execute(function(resp){
	    		if (resp != null) {
	    			console.log(resp)
	        		p.resolve(resp.result);
				}
	    	});
	    	return p.promise;
		}
	};
	
});

//angular.module('jhipsterApp')
//    .factory('Register', function ($resource) {
//        return $resource('api/register', {}, {
//        });
//    });


