angular.module('jhipsterApp')
    .controller('ApprovalController', function($scope, $rootScope, $timeout, $translate,
        usSpinnerService, User, UserSearch, ParseLinks, Account, Group) {
        $scope.users = [];
        $scope.page = 1;
        $scope.cursor = null;
        $scope.invalidQuerySearch = null;
        $scope.spinneractive = false;

        $scope.success = null;
        $scope.error = null;
        $scope.doNotMatch = null;
        $scope.errorUserExists = null;
        $scope.errorUserAndEmailExists = null;
        $scope.errorAccountExists = null;
        $scope.registerAccount = {};
        $timeout(function() {
            angular.element('[ng-model="user.login"]').focus();
        });
        //$scope.startcounter = 0;
        $scope.loadAll = function() {
            var account = AppConstant.ACCOUNT;
            if (account != null) {
            	var position = account.position;
            	if (position.roles != null) {
            		if (position.roles.indexOf('MANAGER') != -1) {
                		listGroup(null, null);
    				} else {
    					if (account.groupId != null) {
    						listGroup(account.groupId, null, null)
    					}
    				}
				}
            }

        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.users = [];
            $scope.loadAll();
        };

        $scope.showUpdate = function(id) {
            User.get(id).then(function(result) {
                $scope.user = result;
                $('#saveUserModal').modal('show');
            });
        };

        function listData(groupId, queryString, cursor) {
            $scope.startSpin();
            User.listUserByGroup(groupId, queryString, cursor).then(function(data) {
                $scope.stopSpin();
                if (data != null) {
                    if (data.items != null) {
                        for (var i = 0; i < data.items.length; i++) {
                            $scope.users.push(data.items[i]);
                        }
                        $scope.cursor = data.nextPageToken;
                    }
                }
            });
        };

        $scope.save = function() {
            if ($scope.user.password !== $scope.confirmPassword) {
                $scope.doNotMatch = 'ERROR';
            } else {
                $scope.user.langKey = $translate.use();
                $scope.doNotMatch = null;
                $scope.error = null;
                $scope.errorUserExists = null;
                $scope.errorEmailExists = null;
                $scope.errorUserAndEmailExists = null;
                $scope.errorAccountExists = null;

                if ($scope.user.id != null) {
                    User.update($scope.user).then(function(data) {
                        if (data.error != null) {
                            $scope.success = null;
                            showError(data.message);
                        } else {
                            $scope.success = 'OK';
                            $scope.refresh();
                        }
                    });
                } else {
                    User.insert($scope.user).then(function(data) {
                        if (data.error != null) {
                            $scope.success = null;
                            showError(data.message);
                        } else {
                            $scope.success = 'OK';
                            $scope.refresh();
                        }
                    });
                }
            }
        };

        $scope.delete = function(id) {
            User.get(id).then(function(data) {
                $scope.user = data;
                $('#deleteUserConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function(id) {
            User.delete(id).then(function(data) {
                $scope.reset();
                $('#deleteUserConfirmation').modal('hide');
                $scope.clear();
            });
        };

        $scope.search = function() {
            $scope.invalidQuerySearch = null;
            if ($scope.cursor == null) {
                $scope.users = [];
            }

            if ($scope.searchQuery != null && $scope.searchQuery != '') {
            	listData($scope.groupId, $scope.searchQuery, $scope.cursor);
            } else {
                listData($scope.groupId, null, null);
            }
        };

        $scope.refresh = function() {
            $scope.reset();
            $('#saveUserModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function() {
            $scope.user = {
                login: null,
                email: null,
                ctrTms: null,
                password: null,
                id: null
            };
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.change = function() {
            $scope.cursor = null;
        };

        $scope.startSpin = function() {
            if (!$scope.spinneractive) {
                usSpinnerService.spin('spinner-1');
                //$scope.startcounter++;
            }
        };

        $scope.stopSpin = function() {
            if ($scope.spinneractive) {
                usSpinnerService.stop('spinner-1');
            }
        };

        $rootScope.$on('us-spinner:spin', function(event, key) {
            $scope.spinneractive = true;
        });

        $rootScope.$on('us-spinner:stop', function(event, key) {
            $scope.spinneractive = false;
        });

        function startTimer() {
            //var timer = $timeout(function () {
            $scope.stopSpin();
            //}, 6000);
        };

        function showError(errorMsg) {
            if (errorMsg != null) {
                if (errorMsg.indexOf('[602]') != -1) {
                    //account already exists
                    $scope.errorAccountExists = 'ERROR';
                } else if (errorMsg.indexOf('[610]') != -1) {
                    //user and email already exists
                    $scope.errorUserAndEmailExists = 'ERROR';
                } else if (errorMsg.indexOf('[608]') != -1) {
                    //login already in use
                    $scope.errorUserExists = 'ERROR';
                } else if (errorMsg.indexOf('[609]') != -1) {
                    //e-mail address already in use
                    $scope.errorEmailExists = 'ERROR';
                } else {
                    $scope.error = 'ERROR';
                }
            }
        };

        function listGroup(groupId, cursor, count) {
            if (!AppConstant.GROUP_ENDPOINT_LOADED) {
                Group.init().then(function() {
                        if (AppConstant.GROUP_ENDPOINT_LOADED) {
                            console.log("groupendpoint loaded...")
                            getGroups(groupId, cursor, count)
                        }
                    },
                    function() {
                        console.log(ErrorCode.ERROR_INIT_ENDPOINT_SERVICE);
                    });
            } else {
            	getGroups(groupId, cursor, count);
            }
        };

        function getGroups(groupId, cursor, count) {
        	$scope.groups = [];
        	$scope.startSpin();
        	if (groupId != null) {
        		Group.get(groupId).then(function(data) {
        			$scope.stopSpin();
                    if (data != null) {
                        if (data.result != null) {
                            $scope.groups.push(data.result);
                            $scope.groupId = $scope.groups[0].id;
                            listData($scope.groupId, null, null);
                        }
                    }
                });
			} else {
				Group.loadAll(cursor, count).then(function(data) {
					$scope.stopSpin();
	                if (data != null) {
	                    if (data.items != null) {
	                        $scope.groups = data.items;
	                        $scope.groupId = $scope.groups[0].id;
	                        listData($scope.groupId, null, null);
	                    }
	                }
	            });
			}
        }
        
        $scope.onChange = function() {
        	var groupId = $scope.groupId;
        	$scope.users = [];
        	listData(groupId, null, null);
        }
        
        $scope.loadPage = function(page) {
            $scope.page = page;
            if ($scope.searchQuery != null) {
         	   $scope.search();
            } else {
         	   if ($scope.cursor != null) {
             	   listData($scope.groupId, null, $scope.cursor);
                }
            }
        };

        $scope.loadAll();

    });
