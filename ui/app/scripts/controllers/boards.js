'use strict';

/**
 * @ngdoc function
 * @name uiApp.controller:BoardsCtrl
 * @description
 * # BoardsCtrl
 * Controller of the uiApp
 */
angular.module('uiApp')
  .controller('BoardsCtrl', ['$scope', 'Restangular', function ($scope, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    var boards = Restangular.all('boards');

    $scope.getBoards = function () {
      boards.getList().then(function (boards) {
        $scope.boardList = boards;
        $scope.boardCollection = [].concat($scope.boardList);
      });
    };

    $scope.deleteBoard = function (boardId) {
      var board = Restangular.one('boards', boardId);
      board.customDELETE().then(function () {
        // refresh boards list
        $scope.getBoards();
      });
    };

    $scope.createBoard = function () {
      boards.customPOST($scope.newBoard).then(function () {
        // refresh boards list
        $scope.newBoard = null;
        $scope.getBoards();
      }, function () {
        window.alert('Unable to create board, name must be unique.');
      });
    };

    function initController() {
      $scope.getBoards();
    }

    initController();
  }]);