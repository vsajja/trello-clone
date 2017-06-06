'use strict';

/**
 * @ngdoc function
 * @name trelloCloneApp.controller:NavCtrl
 * @description
 * # NavCtrl
 * Controller of the trelloCloneApp
 */
angular.module('trelloCloneApp')
  .controller('NavCtrl', function ($scope, $rootScope, AuthenticationService) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    $rootScope.$on('userLoggedIn', function () {
      $scope.currentUser = AuthenticationService.GetCurrentUser();
    });

    $rootScope.$on('userLoggedOut', function () {
      $scope.currentUser = AuthenticationService.GetCurrentUser();
    });

    $scope.currentUser = AuthenticationService.GetCurrentUser();
  });
