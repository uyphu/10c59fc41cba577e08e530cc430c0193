'use strict';

angular.module('jhipsterApp')
    .factory('PlanningWeekId', function ($resource, DateUtils) {
        return $resource('api/planningWeekIds/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
