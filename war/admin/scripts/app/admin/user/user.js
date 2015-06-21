'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('user', {
                parent: 'entity',
                url: '/user',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'proconcoappApp.user.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/admin/user/users.html',
                        controller: 'UserController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('user');
                        return $translate.refresh();
                    }]
                }
            })
            .state('userDetail', {
                parent: 'entity',
                url: '/user/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'proconcoappApp.user.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/admin/user/user-detail.html',
                        controller: 'UserDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('user');
                        return $translate.refresh();
                    }]
                }
            })
            .state('userEdit', {
                parent: 'entity',
                url: '/user/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'proconcoappApp.user.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/admin/user/user-edit.html',
                        controller: 'UserEditController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('user');
                        return $translate.refresh();
                    }]
                }
            });
    });
