'use strict';

/**
 * @ngdoc overview
 * @name trelloCloneApp
 * @description
 * # trelloCloneApp
 *
 * Main module of the application.
 */
var trelloCloneApp = angular
  .module('trelloCloneApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'ui.bootstrap',
    'restangular',
    'angular-loading-bar',
    'smart-table',
    'dndLists',
    'base64',
    'LocalStorageModule'
  ])
  .config(function ($routeProvider, $locationProvider, $compileProvider, RestangularProvider, localStorageServiceProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .when('/boards', {
        templateUrl: 'views/boards.html',
        controller: 'BoardsCtrl',
        controllerAs: 'boards'
      })
      .when('/board/:boardId', {
        templateUrl: 'views/board.html',
        controller: 'BoardCtrl',
        controllerAs: 'board'
      })
      .otherwise({
        redirectTo: '/'
      });

    $locationProvider.html5Mode(false);
    $locationProvider.hashPrefix('');

    RestangularProvider.setDefaultHeaders({'Content-Type': 'application/json'});

    localStorageServiceProvider.setPrefix('trelloCloneApp');

    // release
    // $compileProvider.debugInfoEnabled(false);
    // RestangularProvider.setBaseUrl('/api/v1');

    // dev
    RestangularProvider.setBaseUrl('http://localhost:5050/api/v1');
  });

trelloCloneApp.run(function ($rootScope, $location, $cookieStore, $http) {
  // keep user logged in after page refresh
  $rootScope.globals = $cookieStore.get('globals') || {};
  if ($rootScope.globals.currentUser) {
    $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
  }

  $rootScope.$on('$locationChangeStart', function () {
    // redirect to login page if not logged in and trying to access a restricted page
    var restrictedPage = $.inArray($location.path(), ['/']) === -1;
    var loggedIn = $rootScope.globals.currentUser;

    if (restrictedPage && !loggedIn) {
      $location.path('/');
    }
  });
});
