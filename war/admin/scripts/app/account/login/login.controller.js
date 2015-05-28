'use strict';

angular.module('jhipsterApp')
    .controller('LoginController', function ($rootScope, $scope, $state, $timeout, $window, Auth) {
        $scope.user = {};
        $scope.errors = {};

        $scope.rememberMe = true;
        $timeout(function (){angular.element('[ng-model="username"]').focus();});
        $scope.login = function (event) {
            event.preventDefault();
            Auth.login({
                username: $scope.username,
                password: $scope.password,
                rememberMe: $scope.rememberMe
            }).then(function () {
                $scope.authenticationError = false;
                if ($rootScope.previousStateName === 'register') {
                    $state.go('home');
                } else {
                    $rootScope.back();
                }
            }).catch(function () {
                $scope.authenticationError = true;
            });
        };
        
        $window.init = function() {
		  	$scope.$apply($scope.initgapi);
		};
		
		$scope.initgapi = function() {
			if (!AppConstant.USER_PROFILE_ENDPOINT_LOADED) {
				Auth.init().then(function(){
					if (AppConstant.USER_PROFILE_ENDPOINT_LOADED) {
						//getCustomersSummary('customersSummary',vm.currentPage - 1,vm.pageSize);
						console.log("userprofileendpoint loaded...")
					}
				},
				function(){
					console.log(ErrorCode.ERROR_INIT_ENDPOINT_SERVICE);
				});
			}
			
		};
		
		function loadData() {
			if (AppConstant.USER_PROFILE_ENDPOINT_LOADED) {
				//getCustomersSummary('customersSummary',vm.currentPage - 1,vm.pageSize);
			} else {
				if (AppConstant.API_LOAD_TYPE != 0) {
					$scope.initgapi();
				} else {
					AppConstant.API_LOAD_TYPE = 2;
				}
			}
		};
		
		loadData();
    });
