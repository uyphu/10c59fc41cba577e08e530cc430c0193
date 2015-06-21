'use strict';

angular.module('jhipsterApp')
    .controller('PasswordController', function ($scope, Auth, Principal) {
        Principal.identity().then(function(account) {
            $scope.account = account;
        });

        $scope.success = null;
        $scope.error = null;
        $scope.doNotMatch = null;
        $scope.changePassword = function () {
            if ($scope.password !== $scope.confirmPassword) {
                $scope.doNotMatch = 'ERROR';
            } else {
                $scope.doNotMatch = null;
                $scope.account.password = $scope.password;
                Auth.changePassword($scope.account).then(function (data) {
                	if (data.error != null) {
                		$scope.success = null;
                		$scope.error = 'ERROR';
					} else {
						$scope.error = null;
						$scope.success = 'OK';
					}
                });
            }
        };
    });
