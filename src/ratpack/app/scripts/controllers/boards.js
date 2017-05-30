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
        $scope.boards = boards;
        $scope.boardCollection = [].concat($scope.boards);
      });
    };

    $scope.deleteBoard = function (boardId) {
      boards.customDELETE(boardId).then(function () {
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
