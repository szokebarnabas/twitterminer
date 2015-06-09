'use strict';

angular.module('twitterminerApp')
    .controller('StatsCtrl', ['$scope','tweetstatsStore', function ($scope, tweetstatsStore) {
        $scope.stats = tweetstatsStore.getStats();
    }]);
