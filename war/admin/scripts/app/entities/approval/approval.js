'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('approval', {
                parent: 'entity',
                url: '/approval',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'proconcoappApp.approval.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/approval/approvals.html',
                        controller: 'ApprovalController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('approval');
                        return $translate.refresh();
                    }]
                }
            })
            .state('approvalDetail', {
                parent: 'entity',
                url: '/approval/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'proconcoappApp.approval.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/approval/approval-detail.html',
                        controller: 'ApprovalDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('approval');
                        return $translate.refresh();
                    }]
                }
            })
            .state('approvalEdit', {
                parent: 'entity',
                url: '/approval/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'proconcoappApp.approval.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/approval/approval-edit.html',
                        controller: 'ApprovalEditController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('approval');
                        return $translate.refresh();
                    }]
                }
            });
    });
