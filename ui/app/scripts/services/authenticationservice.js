'use strict';

/**
 * @ngdoc service
 * @name trelloCloneApp.AuthenticationService
 * @description
 * # authenticationservice
 * Service in the trelloCloneApp.
 */
angular.module('trelloCloneApp')
  .service('AuthenticationService', function ($http, $cookies, $base64, $rootScope, localStorageService) {

    function SetCredentials(username, password, user) {
      var authdata = $base64.encode(username + ':' + password);

      var currentUser = {
        username: username,
        userId: user.userId,
        authdata: authdata
      };

      $rootScope.globals = {
        currentUser: currentUser
      };

      // set default auth header for http requests
      $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata; // jshint ignore:line

      // store user details in globals cookie that keeps user logged in for 1 week (or until they logout)
      var cookieExp = new Date();
      cookieExp.setDate(cookieExp.getDate() + 7);
      $cookies.putObject('globals', $rootScope.globals, {expires: cookieExp});

      localStorageService.set('currentUser', currentUser);

      $rootScope.$broadcast('userLoggedIn');
    }

    function ClearCredentials() {
      $rootScope.globals = {};
      $cookies.remove('globals');
      $http.defaults.headers.common.Authorization = 'Basic';
      localStorageService.remove('currentUser');
      $rootScope.$broadcast('userLoggedOut');
    }

    function GetCurrentUser() {
      return localStorageService.get('currentUser');
    }

    var service = {};

    service.SetCredentials = SetCredentials;
    service.ClearCredentials = ClearCredentials;
    service.GetCurrentUser = GetCurrentUser;

    return service;
  });
