'use strict';

/**
 * @ngdoc function
 * @name uiApp.controller:BoardCtrl
 * @description
 * # BoardCtrl
 * Controller of the uiApp
 */
angular.module('trelloCloneApp')
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
      board.customGET('lists').then(function (boardLists) {
        $scope.boardLists = boardLists;
      });
    };

    $scope.deleteBoardList = function (listId) {
      var boardList = board.all('lists', listId);
      boardList.customDELETE(listId).then(function () {
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

    $scope.createCard = function () {
      console.log('create card for list');
    };

    function initController() {
      $scope.getBoard();
      $scope.getBoardLists();
    }

    initController();

    $scope.updateLists = function () {
      $scope.modelAsJson = angular.toJson($scope.boardLists.lists, true);

      board.one('lists').customPUT($scope.modelAsJson).then(function () {
        // refresh board lists
        // $scope.getBoardLists();
      }, function () {
        window.alert('Unable to create board list.');
      });
    };
  }]);
