'use strict';

/**
 * @ngdoc overview
 * @name uiApp
 * @description
 * # uiApp
 *
 * Main module of the application.
 */
angular
  .module('uiApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'restangular',
    'smart-table'
  ])
  .config(function ($routeProvider, $locationProvider, $compileProvider, RestangularProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/boards.html',
        controller: 'BoardsCtrl',
        controllerAs: 'boards'
      })
      .when('/main', {
        // templateUrl: 'views/main.html',
        // controller: 'MainCtrl',
        // controllerAs: 'main'
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

    // release
    $compileProvider.debugInfoEnabled(false);
    RestangularProvider.setBaseUrl('/api/v1');

    // dev
    // RestangularProvider.setBaseUrl('http://localhost:5050/api/v1');
  });
