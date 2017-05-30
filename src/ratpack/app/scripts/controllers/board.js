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

    $scope.getBoard = function () {
      board.customGET().then(function (board) {
        $scope.board = board;
      });
    };

    $scope.getBoardLists = function () {
      board.getList('lists').then(function (boardLists) {
        $scope.boardLists = boardLists;
        $scope.boardListCollection = [].concat($scope.boardLists);
      });
    };

    $scope.deleteBoardList = function (boardListId) {
      var boardList = board.all('lists', boardListId);
      boardList.customDELETE(boardListId).then(function () {
        // refresh board lists
        $scope.getBoardLists();
      });
    };

    $scope.createBoardList = function () {
      board.post('lists', $scope.newBoardList).then(function () {
        // refresh board lists
        $scope.getBoardLists();
      }, function () {
        window.alert('Unable to create board list.');
      });
    };

    function initController() {
      $scope.getBoard();
      $scope.getBoardLists();
    }

    initController();
  }]);
