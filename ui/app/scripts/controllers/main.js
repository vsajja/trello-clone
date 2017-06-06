'use strict';

/**
 * @ngdoc function
 * @name uiApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the uiApp
 */
angular.module('trelloCloneApp')
  .controller('MainCtrl', function ($scope, $location, AuthenticationService, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    // reset login status
    AuthenticationService.ClearCredentials();

    $scope.alerts = [];

    $scope.closeAlert = function (index) {
      $scope.alerts.splice(index, 1);
    };

    $scope.login = function login() {
      Restangular.one('login').customPOST($scope.user)
        .then(function (user) {
          AuthenticationService.SetCredentials($scope.user.username, $scope.user.password, user);
          $location.path('/boards');
        }, function () {
          $scope.alerts.push({type: 'danger', msg: 'Error! Invalid credentials!'});
        });
    };

    $scope.register = function () {
      Restangular.one('register').customPOST($scope.newUser).then(function (registeredUser) {
        $scope.alerts.push({type: 'success', msg: 'Success! Registered user! Username: ' + registeredUser.username});
        $location.path('/');
      }, function (error) {
        // conflict
        if (error.status === 409) {
          $scope.alerts.push({type: 'danger', msg: 'Error! Username ' + $scope.newUser.username + ' already exists!'});
        }
        else {
          $scope.alerts.push({type: 'danger', msg: 'Error! Unable to register user.'});
        }
      });
    };
  });
