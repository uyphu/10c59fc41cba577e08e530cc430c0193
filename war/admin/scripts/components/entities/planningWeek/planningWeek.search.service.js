'use strict';

angular.module('jhipsterApp')
    .factory('PlanningWeekSearch', function ($resource) {
        return $resource('api/_search/planningWeeks/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
