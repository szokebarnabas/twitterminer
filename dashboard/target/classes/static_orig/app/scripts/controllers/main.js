'use strict';

/**
 * @ngdoc function
 * @name twitterminerApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the twitterminerApp
 */
angular.module('twitterminerApp')
  .controller('MainCtrl', ['$scope', 'ChatSocket', function ($scope, chatSocket) {

    $scope.tweets = [];
    $scope.keywords = "";

    $scope.sendMessage = function() {
      var destination = "/app/search";

      //if($scope.sendTo != "everyone") {
      //  destination = "/app/chat.private." + $scope.sendTo;
      //  $scope.messages.unshift({message: $scope.newMessage, username: 'you', priv: true, to: $scope.sendTo});
      //}

      chatSocket.send(destination, {}, JSON.stringify({criteria: $scope.newMessage}));
      $scope.keywords = 'Keywords : ' + $scope.newMessage;
      $scope.newMessage = '';
      $scope.tweets = [];
    };

    $scope.startTyping = function() {
      //alert("startTyping");
    }

    var initStompClient = function() {
      chatSocket.init('http://localhost:8050/search');

      chatSocket.connect(function(frame) {

        $scope.username = frame.headers['user-name'];

        chatSocket.subscribe("/app/chat.participants", function(message) {
          $scope.participants = JSON.parse(message.body);
        });

        chatSocket.subscribe("/topic/chat.login", function(message) {
          $scope.participants.unshift({u3rname: JSON.parse(message.body).username, typing : false});
        });

        chatSocket.subscribe("/topic/chat.logout", function(message) {
          var username = JSON.parse(message.body).username;
          for(var index in $scope.participants) {
            if($scope.participants[index].username == username) {
              $scope.participants.splice(index, 1);
            }
          }
        });

        chatSocket.subscribe("/queue/tweets", function(message) {
          var tweet = JSON.parse(message.body);
          console.log('Tweet: ' + tweet)
          $scope.tweets.unshift(tweet);
        });

        chatSocket.subscribe("/topic/chat.typing", function(message) {
          var parsed = JSON.parse(message.body);
          if(parsed.username == $scope.username) return;

          for(var index in $scope.participants) {
            var participant = $scope.participants[index];

            if(participant.username == parsed.username) {
              $scope.participants[index].typing = parsed.typing;
            }
          }
        });

        chatSocket.subscribe("/topic/chat.message", function(message) {
          $scope.messages.unshift(JSON.parse(message.body));
        });

        chatSocket.subscribe("/user/queue/chat.message", function(message) {
          var parsed = JSON.parse(message.body);
          parsed.priv = true;
          $scope.messages.unshift(parsed);
        });

        chatSocket.subscribe("/user/queue/errors", function(message) {
          toaster.pop('error', "Error", message.body);
        });

      }, function(error) {
        toaster.pop('error', 'Error', 'Connection error ' + error);

      });
    };

    initStompClient();
  }]);
