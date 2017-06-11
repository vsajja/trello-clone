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
  .controller('BoardsCtrl', ['$scope', 'Restangular', 'AuthenticationService', function ($scope, Restangular, AuthenticationService) {
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

    var userId = null;
    var currentUser = AuthenticationService.GetCurrentUser();
    if (currentUser) {
      userId = currentUser.userId;
    }

    var user = Restangular.one('users', userId);
    var boards = Restangular.all('boards');

    $scope.getBoards = function () {
      user.customGET('boards').then(function (boards) {
        $scope.boards = boards;
      });
    };

    $scope.getTeams = function () {
      user.customGET('teams').then(function (teams) {
        $scope.teams = teams.teams;
      });
    };

    $scope.createBoard = function (newBoard) {
      user.post('boards', angular.toJson(newBoard, true)).then(function () {
        $scope.newBoard = null;
        $scope.refreshBoards();
      }, function () {
        window.alert('Error! Unable to create board.');
      });
    };

    $scope.createTeam = function (newTeam) {
      user.post('teams', angular.toJson(newTeam, true)).then(function () {
        $scope.newTeam = null;
        $scope.refreshBoards();
      }, function () {
        window.alert('Error! Unable to create team.');
      });
    };

    $scope.createTeamBoard = function (newTeamBoard) {
      var team = Restangular.one('teams', newTeamBoard.teamId);
      team.post('boards', angular.toJson(newTeamBoard, true)).then(function () {
        $scope.refreshBoards();
      }, function () {
        window.alert('Error! Unable to create team board');
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
