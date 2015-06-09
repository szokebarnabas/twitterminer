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

    //$http.defaults.useXDomain = true;
    //$http.get('http://ip.jsontest.com/')
    //  .success(function(data) {
    //    $scope.healthStatus = data;
    //  })
    //  .error(function(d){ console.log( "Health check is not available" ); });

    //$http({method: 'GET', url: 'http://ip.jsontest.com/',
    //  headers:{
    //    'Access-Control-Allow-Origin': '*',
    //    'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
    //    'Access-Control-Allow-Headers': 'Content-Type, X-Requested-With',
    //    'X-Random-Shit':'123123123'
    //  }})
    //  .success(function(d){ console.log( "yay" ); })
    //  .error(function(d){ console.log( "nope" ); });
  }]);
