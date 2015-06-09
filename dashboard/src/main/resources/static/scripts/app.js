'use strict';

/**
 * @ngdoc overview
 * @name twitterminerApp
 * @description
 * # twitterminerApp
 *
 * Main module of the application.
 */
var app = angular
  .module('twitterminerApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'springChat.services'
  ]);
  app.config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/statistics', {
        templateUrl: 'views/statistics.html',
        controller: 'MainCtrl'
      })
      .when('/admin', {
        templateUrl: 'views/admin.html',
        controller: 'AdminCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
