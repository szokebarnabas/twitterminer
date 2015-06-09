'use strict';

/**
 * @ngdoc function
 * @name twitterminerApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the twitterminerApp
 */
angular.module('twitterminerApp')
  .controller('AdminCtrl', ['$scope', 'UserFactory', '$http', function ($scope, UserFactory, $http) {

    UserFactory.get({}, function (userFactory) {
      $scope.healthStatus = userFactory;
    })

  }]);
