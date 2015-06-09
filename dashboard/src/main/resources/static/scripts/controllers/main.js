'use strict';

angular.module('twitterminerApp')
    .controller('MainCtrl', ['$scope', 'ChatSocket', 'tweetStore','connectionService','UUIDGenerator','tweetstatsStore','hashTagStore', function ($scope, chatSocket, tweetStore, connectionService, UUIDGenerator, tweetstatsStore, hashTagStore) {

        $scope.tweets = tweetStore.tweets();
        $scope.hashTagStats = hashTagStore.getStats();
        $scope.tweetStats = tweetstatsStore.getStats();
        $scope.keywords = "";
        var isConnected = connectionService.isConnected();
        var header = {
            'client-id': UUIDGenerator.getUUId()
        };

        $scope.sendMessage = function() {
            var destination = "/app/search";

            tweetStore.clear();
            hashTagStore.clear();
            tweetstatsStore.clear();

            chatSocket.send(destination, header, JSON.stringify({criteria: $scope.newMessage}));
            $scope.keywords = 'Keywords : ' + $scope.newMessage;
            $scope.newMessage = '';
            $scope.tweets = tweetStore.tweets();
            $scope.hashTagStats = hashTagStore.getStats();
            $scope.tweetStats = tweetstatsStore.getStats();
        };

        var subscribeToStatisticQueues = function () {
            chatSocket.subscribe("/queue/hashtagstats/" + UUIDGenerator.getUUId(), function (message) {
                var hashtagstats = JSON.parse(message.body);
                hashTagStore.setStats(hashtagstats);
                $scope.hashTagStats = hashTagStore.getStats();
            });
            chatSocket.subscribe("/queue/tweetstats/" + UUIDGenerator.getUUId(), function (message) {
                var data = JSON.parse(message.body);
                tweetstatsStore.setStats(data);
                $scope.tweetStats = tweetstatsStore.getStats();

            });
        };

        var initStompClient = function() {

            chatSocket.init('http://localhost:8050/search/');

            chatSocket.connect(header, function(frame) {

                console.log(frame);
                connectionService.setConnected(true);

                chatSocket.subscribe("/queue/tweets/" + UUIDGenerator.getUUId() , function(message) {
                    var tweet = JSON.parse(message.body);
                    tweetStore.addTweet(tweet);
                });

                subscribeToStatisticQueues();

                //chatSocket.subscribe("/user/queue/errors", function(message) {
                //    toaster.pop('error', "Error", message.body);
                //});

            }, function(error) {
                toaster.pop('error', 'Error', 'Connection error ' + error);

            });
        };

        if (!isConnected) {
            initStompClient();
        } else {
            subscribeToStatisticQueues();
        }
    }]);
