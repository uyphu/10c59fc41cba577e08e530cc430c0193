'use strict';

angular.module('jhipsterApp')
  .controller('PositionDetailController', function ($scope, $stateParams, Position) {
      $scope.position = {};
      $scope.load = function (id) {
          Position.get(id).then(function(result) {
            $scope.position = result;
          });
      };
      $scope.load($stateParams.id);
  });