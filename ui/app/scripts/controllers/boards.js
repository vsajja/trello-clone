'use strict';

/**
 * @ngdoc function
 * @name uiApp.controller:BoardsCtrl
 * @description
 * # BoardsCtrl
 * Controller of the uiApp
 *
 */
angular.module('trelloCloneApp')
  .controller('BoardsCtrl', ['$scope', 'Restangular', function ($scope, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    $scope.createBoardPopover = {
      templateUrl: 'CreateBoardPopoverTemplate.html'
    };

    $scope.createTeamBoardPopover = {
      templateUrl: 'CreateTeamBoardPopoverTemplate.html'
    };

    $scope.createTeamPopover = {
      templateUrl: 'CreateTeamPopoverTemplate.html'
    };

    var boards = Restangular.all('boards');
    var teams = Restangular.all('teams');

    $scope.getBoards = function () {
      boards.getList().then(function (boards) {
        $scope.boards = boards;
      });
    };

    $scope.getTeams = function () {
      teams.customGET().then(function (response) {
        $scope.teams = response.teams;
      });
    };

    $scope.createBoard = function (newBoard) {
      boards.customPOST(angular.toJson(newBoard, true)).then(function () {
        $scope.newBoard = null;
        $scope.refreshBoards();
      }, function () {
        window.alert('Unable to create board, name must be unique.');
      });
    };

    $scope.createTeam = function (newTeam) {
      teams.customPOST(angular.toJson(newTeam, true)).then(function () {
        $scope.newTeam = null;
        $scope.refreshBoards();
      }, function () {
        window.alert('Unable to create team, name must be unique.');
      });
    };

    $scope.createTeamBoard = function (newTeamBoard) {
      var team = Restangular.one('teams', newTeamBoard.teamId);
      team.post('boards', angular.toJson(newTeamBoard, true)).then(function () {
        $scope.refreshBoards();
      }, function () {
        window.alert('Unable to create team board');
      });
    };

    $scope.deleteBoard = function (boardId) {
      boards.customDELETE(boardId).then(function () {
        $scope.refreshBoards();
      });
    };

    $scope.refreshBoards = function refreshBoards() {
      $scope.getBoards();
      $scope.getTeams();
    };

    $scope.refreshBoards();
  }]);
