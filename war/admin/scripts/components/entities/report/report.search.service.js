'use strict';

angular.module('jhipsterApp')
    .factory('ReportSearch', function ($resource) {
        return $resource('api/_search/reports/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
