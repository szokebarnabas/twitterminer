'use strict';

/* Services */

var services = angular.module('springChat.services', []);

services.factory('ChatSocket', ['$rootScope', function ($rootScope) {
    var stompClient;

    var wrappedSocket = {

        init: function (url) {
            stompClient = Stomp.over(new SockJS(url));
        },
        connect: function (headers, successCallback, errorCallback) {
            stompClient.connect(headers, function (frame) {
                $rootScope.$apply(function () {
                    successCallback(frame);
                });
            }, function (error) {
                $rootScope.$apply(function () {
                    errorCallback(error);
                });
            });
        },
        subscribe: function (destination, callback) {
            stompClient.subscribe(destination, function (message) {
                $rootScope.$apply(function () {
                    callback(message);
                });
            });
        },
        send: function (destination, headers, object) {
            stompClient.send(destination, headers, object);
        }
    }

    return wrappedSocket;

}]);


services.factory('UserFactory', function ($resource) {
    return $resource('http://localhost:8761/eureka/apps', {}, {
        query: {
            method: 'GET',
            params: {},
            isArray: false
        }
    })

});

services.factory('tweetStore', function () {
    var tweets = [];
    return {
        tweets: function () {
            // This exposed private data
            return tweets;
        },
        addTweet: function (tweet) {
            console.log("addTweet:" + tweet);
            tweets.unshift(tweet);
        },
        clear: function () {
            tweets = [];
        }
    };
})

services.factory('connectionService', function () {
    var connected = false;

    return {
        isConnected: function () {
            console.log("isConnected:" + connected);
            return connected;
        },
        setConnected: function (isConnected) {
            console.log("setConnected:" + isConnected);
            connected = isConnected;
        }
    };
})

services.factory('UUIDGenerator', function () {
    var uuid;
    return {
        getUUId: function () {
            if (uuid == null) {
                var d = new Date().getTime();
                var generatedUUID = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
                    var r = (d + Math.random() * 16) % 16 | 0;
                    d = Math.floor(d / 16);
                    return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
                });
                uuid = generatedUUID;
            }
            return uuid;
        }
    };
})
