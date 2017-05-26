'use strict';

/**
 * @ngdoc function
 * @name uiApp.controller:BoardCtrl
 * @description
 * # BoardCtrl
 * Controller of the uiApp
 */
angular.module('uiApp')
  .controller('BoardCtrl', ['$scope', '$routeParams', 'Restangular', function ($scope, $routeParams, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    var boardId = $routeParams.boardId;
    var board = Restangular.one('boards', boardId);

    // This will query /boards/:boardId and return a promise.
    $scope.getBoard = function () {
      board.customGET().then(function (board) {
        $scope.board = board;
      });
    };

    function initController() {
      $scope.getBoard();
    }

    initController();
  }]);
