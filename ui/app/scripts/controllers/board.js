'use strict';

/**
 * @ngdoc function
 * @name uiApp.controller:BoardCtrl
 * @description
 * # BoardCtrl
 * Controller of the uiApp
 */
angular.module('trelloCloneApp')
  .controller('BoardCtrl', ['$scope', '$routeParams', 'Restangular', function ($scope, $routeParams, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    var boardId = $routeParams.boardId;
    var board = Restangular.one('boards', boardId);

    $scope.getBoard = function () {
      board.customGET().then(function (board) {
        $scope.board = board;
      });
    };

    $scope.getBoardLists = function () {
      board.customGET('lists').then(function (boardLists) {
        $scope.boardLists = boardLists;
      });
    };

    $scope.updateLists = function () {
      $scope.modelAsJson = angular.toJson($scope.boardLists.lists, true);

      board.one('lists').customPUT($scope.modelAsJson).then(function () {
        // refresh board lists
        // $scope.getBoardLists();
      }, function () {
        window.alert('Unable to create board list.');
      });
    };

    $scope.deleteCard = function (cardId) {
      var card = Restangular.one('cards', cardId);
      card.customDELETE().then(function () {
        // refresh board lists
        $scope.getBoardLists();
      });
    };

    $scope.deleteBoardList = function (listId) {
      var boardList = board.all('lists', listId);
      boardList.customDELETE(listId).then(function () {
        // refresh board lists
        $scope.getBoardLists();
      });
    };

    $scope.createBoardList = function () {
      board.post('lists', $scope.newBoardList).then(function () {
        // refresh board lists
        $scope.getBoardLists();
      }, function () {
        window.alert('Unable to create board list.');
      });
    };

    $scope.addCard = function (listId, newCard) {
      var list = Restangular.one('lists', listId);
      list.post('cards', angular.toJson(newCard, true)).then(function () {
        // refresh board lists
        $scope.getBoardLists();
      }, function () {
        window.alert('Unable to create board list.');
      });
    };

    function initController() {
      $scope.getBoard();
      $scope.getBoardLists();
    }

    initController();
  }]);


angular.module('trelloCloneApp').controller('CardDetailCtrl', function ($scope, $uibModal) {
  var $ctrl = this;
  $ctrl.open = function (card, listDetails) {
    var modalInstance = $uibModal.open({
      animation: false,
      ariaLabelledBy: 'modal-title',
      ariaDescribedBy: 'modal-body',
      templateUrl: 'cardContent.html',
      controller: 'CardDetailContentCtrl',
      controllerAs: '$ctrl',
      resolve: {
        card: function () {
          return card;
        },
        list: function () {
          return listDetails;
        }
      }
    });

    modalInstance.result.then(function (card) {
      $ctrl.card = card;
    }, function () {
      // $log.info('Modal dismissed at: ' + new Date());
    });
  };
});

angular.module('trelloCloneApp').controller('CardDetailContentCtrl', function ($scope, $uibModalInstance, card, list, Restangular) {
  var $ctrl = this;

  $ctrl.card = card;
  $ctrl.list = list;

  $ctrl.ok = function () {
    $uibModalInstance.close();
  };

  $ctrl.cancel = function () {
    $uibModalInstance.dismiss('cancel');
  };

  $ctrl.updateCard = function (listId, card) {
    console.log(listId);
    console.log(card);
    var cards = Restangular.one('lists', listId).all('cards');
    cards.customPUT(angular.toJson(card, true)).then(function () {
      // refresh board lists
      // $scope.getBoardLists();
    }, function () {
      window.alert('Unable to update card details.');
    });
  };
});
