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

    $scope.createTeamMemberPopover = {
      templateUrl: 'CreateTeamMemberPopoverTemplate.html'
    };

    var userId = null;
    var currentUser = AuthenticationService.GetCurrentUser();
    if (currentUser) {
      userId = currentUser.userId;
    }

    var user = Restangular.one('users', userId);
    var boards = Restangular.all('boards');

    $scope.getUsers = function () {
      var users = Restangular.all('users');
      users.customGET().then(function (users) {
        $scope.users = users;
      });
    };

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

    $scope.addTeamMember = function (teamId, user) {
      var team = Restangular.one('teams', teamId);
      team.post('members', angular.toJson(user, true)).then(function () {
        $scope.getTeamMembers(teamId);
      }, function (error) {
        // conflict
        if (error.status === 409) {
          window.alert('Unable to add team member, username ' + user.username + ' already exists!');
        }
        else {
          window.alert('Unable to add team member!');
        }
      });
    };

    $scope.removeTeamMember = function (teamId, teamMemberId) {
      var teamMember = Restangular.one('teams/members', teamMemberId);
      teamMember.remove().then(function () {
        console.log('deleted ' + teamMemberId);
        $scope.getTeamMembers(teamId);
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

    $scope.getTeamMembers = function (teamId) {
      var team = Restangular.one('teams', teamId);
      team.getList('members').then(function (members) {
        $scope.teamMembers = Restangular.stripRestangular(members);
      });
    };

    $scope.refreshBoards = function refreshBoards() {
      $scope.getBoards();
      $scope.getTeams();
      $scope.getUsers();
    };

    $scope.refreshBoards();
  }]);
