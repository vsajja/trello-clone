'use strict';

/**
 * @ngdoc function
 * @name uiApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the uiApp
 */
angular.module('uiApp')
  .controller('MainCtrl', function ($scope, $location) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    $scope.login = function login() {
      // TODO: auth against server
      if ($scope.user.username === $scope.user.password) {
        $location.path('/boards');
      }
      else {
        window.alert('Invalid credentials! Username must match password!');
      }
    };
  });
