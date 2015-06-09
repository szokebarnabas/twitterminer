'use strict';

/* Services */

var services = angular.module('springChat.services', []);

services.factory('ChatSocket', ['$rootScope', function($rootScope) {
        var stompClient;

        var wrappedSocket = {

            init: function(url) {
                stompClient = Stomp.over(new SockJS(url));
            },
            connect: function(successCallback, errorCallback) {

                stompClient.connect({}, function(frame) {
                    $rootScope.$apply(function() {
                        successCallback(frame);
                    });
                }, function(error) {
                    $rootScope.$apply(function(){
                        errorCallback(error);
                    });
                });
            },
            subscribe : function(destination, callback) {
                stompClient.subscribe(destination, function(message) {
                    $rootScope.$apply(function(){
                        callback(message);
                    });
                });
            },
            send: function(destination, headers, object) {
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

services.factory('tweetStore', function ($resource) {
  return $resource('http://localhost:8761/eureka/apps', {}, {
    query: {
      method: 'GET',
      params: {},
      isArray: false
    }
  })
});

//services.factory('tweetStore', function () {
//  var tweets = [];
//
//  return {
//    notes:function () {
//      // This exposed private data
//      return tweets;
//    },
//    addTweet:function (tweet) {
//      console.log("addTweet:" + tweet);
//      data.unshift(tweet);
//    }
//  };
//})
