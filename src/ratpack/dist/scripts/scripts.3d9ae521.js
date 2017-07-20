"use strict";var trelloCloneApp=angular.module("trelloCloneApp",["ngAnimate","ngCookies","ngResource","ngRoute","ngSanitize","ngTouch","ui.bootstrap","restangular","angular-loading-bar","smart-table","dndLists","base64","LocalStorageModule","xeditable","btford.markdown"]).config(["$routeProvider","$locationProvider","$compileProvider","RestangularProvider","localStorageServiceProvider",function(a,b,c,d,e){a.when("/",{templateUrl:"views/main.html",controller:"MainCtrl",controllerAs:"main"}).when("/boards",{templateUrl:"views/boards.html",controller:"BoardsCtrl",controllerAs:"boards"}).when("/board/:boardId",{templateUrl:"views/board.html",controller:"BoardCtrl",controllerAs:"board"}).otherwise({redirectTo:"/"}),b.html5Mode(!1),b.hashPrefix(""),d.setDefaultHeaders({"Content-Type":"application/json"}),e.setPrefix("trelloCloneApp"),c.debugInfoEnabled(!1),d.setBaseUrl("/api/v1")}]);trelloCloneApp.run(["$rootScope","$location","$cookieStore","$http",function(a,b,c,d){a.globals=c.get("globals")||{},a.globals.currentUser&&(d.defaults.headers.common.Authorization="Basic "+a.globals.currentUser.authdata),a.$on("$locationChangeStart",function(){var c=-1===$.inArray(b.path(),["/"]),d=a.globals.currentUser;c&&!d&&b.path("/")})}]),angular.module("trelloCloneApp").controller("MainCtrl",["$scope","$location","AuthenticationService","Restangular",function(a,b,c,d){this.awesomeThings=["HTML5 Boilerplate","AngularJS","Karma"],c.ClearCredentials(),a.alerts=[],a.closeAlert=function(b){a.alerts.splice(b,1)},a.login=function(){d.one("login").customPOST(a.user).then(function(d){c.SetCredentials(a.user.username,a.user.password,d),b.path("/boards")},function(){a.alerts.push({type:"danger",msg:"Error! Invalid credentials!"})})},a.register=function(){d.one("register").customPOST(a.newUser).then(function(c){a.alerts.push({type:"success",msg:"Success! Registered user! Username: "+c.username}),b.path("/")},function(b){409===b.status?a.alerts.push({type:"danger",msg:"Error! Username "+a.newUser.username+" already exists!"}):a.alerts.push({type:"danger",msg:"Error! Unable to register user."})})}}]),angular.module("trelloCloneApp").controller("BoardsCtrl",["$scope","Restangular","AuthenticationService",function(a,b,c){this.awesomeThings=["HTML5 Boilerplate","AngularJS","Karma"],a.createBoardPopover={templateUrl:"CreateBoardPopoverTemplate.html"},a.createTeamBoardPopover={templateUrl:"CreateTeamBoardPopoverTemplate.html"},a.createTeamPopover={templateUrl:"CreateTeamPopoverTemplate.html"},a.createTeamMemberPopover={templateUrl:"CreateTeamMemberPopoverTemplate.html"};var d=null,e=c.GetCurrentUser();e&&(d=e.userId);var f=b.one("users",d),g=b.all("boards");a.getUsers=function(){var c=b.all("users");c.customGET().then(function(b){a.users=b})},a.getBoards=function(){f.customGET("boards").then(function(b){a.boards=b})},a.getTeams=function(){f.customGET("teams").then(function(b){a.teams=b.teams})},a.addTeamMember=function(c,d){var e=b.one("teams",c);e.post("members",angular.toJson(d,!0)).then(function(){a.getTeamMembers(c)},function(a){409===a.status?window.alert("Unable to add team member, username "+d.username+" already exists!"):window.alert("Unable to add team member!")})},a.removeTeamMember=function(c,d){var e=b.one("teams/members",d);e.remove().then(function(){console.log("deleted "+d),a.getTeamMembers(c)})},a.createBoard=function(b){f.post("boards",angular.toJson(b,!0)).then(function(){a.newBoard=null,a.refreshBoards()},function(){window.alert("Error! Unable to create board.")})},a.createTeam=function(b){f.post("teams",angular.toJson(b,!0)).then(function(){a.newTeam=null,a.refreshBoards()},function(){window.alert("Error! Unable to create team.")})},a.createTeamBoard=function(c){var d=b.one("teams",c.teamId);d.post("boards",angular.toJson(c,!0)).then(function(){a.refreshBoards()},function(){window.alert("Error! Unable to create team board")})},a.deleteBoard=function(b){g.customDELETE(b).then(function(){a.refreshBoards()})},a.getTeamMembers=function(c){var d=b.one("teams",c);d.getList("members").then(function(c){a.teamMembers=b.stripRestangular(c)})},a.refreshBoards=function(){a.getBoards(),a.getTeams(),a.getUsers()},a.refreshBoards()}]),angular.module("trelloCloneApp").controller("BoardCtrl",["$scope","$routeParams","Restangular",function(a,b,c){function d(){a.getBoard(),a.getBoardLists()}this.awesomeThings=["HTML5 Boilerplate","AngularJS","Karma"];var e=b.boardId,f=c.one("boards",e);a.getBoard=function(){f.customGET().then(function(b){a.board=b})},a.getBoardLists=function(){f.customGET("lists").then(function(b){a.boardLists=b})},a.updateLists=function(){a.modelAsJson=angular.toJson(a.boardLists.lists,!0),f.one("lists").customPUT(a.modelAsJson).then(function(){},function(){window.alert("Unable to create board list.")})},a.deleteCard=function(b){var d=c.one("cards",b);d.customDELETE().then(function(){a.getBoardLists()})},a.deleteBoardList=function(b){var c=f.all("lists",b);c.customDELETE(b).then(function(){a.getBoardLists()})},a.createBoardList=function(){f.post("lists",a.newBoardList).then(function(){a.getBoardLists()},function(){window.alert("Unable to create board list.")})},a.addCard=function(b,d){var e=c.one("lists",b);e.post("cards",angular.toJson(d,!0)).then(function(){a.getBoardLists()},function(){window.alert("Unable to create board list.")})},d()}]),angular.module("trelloCloneApp").controller("CardDetailCtrl",["$scope","$uibModal",function(a,b){var c=this;c.open=function(a,d){var e=b.open({animation:!1,ariaLabelledBy:"modal-title",ariaDescribedBy:"modal-body",templateUrl:"cardContent.html",controller:"CardDetailContentCtrl",controllerAs:"$ctrl",resolve:{card:function(){return a},list:function(){return d}}});e.result.then(function(a){c.card=a},function(){})}}]),angular.module("trelloCloneApp").controller("CardDetailContentCtrl",["$scope","$uibModalInstance","card","list","Restangular",function(a,b,c,d,e){var f=this;f.card=c,f.list=d,f.ok=function(){b.close()},f.cancel=function(){b.dismiss("cancel")},f.updateCard=function(a,b){console.log(a),console.log(b);var c=e.one("lists",a).all("cards");c.customPUT(angular.toJson(b,!0)).then(function(){},function(){window.alert("Unable to update card details.")})}}]),angular.module("trelloCloneApp").service("AuthenticationService",["$http","$cookies","$base64","$rootScope","localStorageService",function(a,b,c,d,e){function f(f,g,h){var i=c.encode(f+":"+g),j={username:f,userId:h.userId,authdata:i};d.globals={currentUser:j},a.defaults.headers.common.Authorization="Basic "+i;var k=new Date;k.setDate(k.getDate()+7),b.putObject("globals",d.globals,{expires:k}),e.set("currentUser",j),d.$broadcast("userLoggedIn")}function g(){d.globals={},b.remove("globals"),a.defaults.headers.common.Authorization="Basic",e.remove("currentUser"),d.$broadcast("userLoggedOut")}function h(){return e.get("currentUser")}var i={};return i.SetCredentials=f,i.ClearCredentials=g,i.GetCurrentUser=h,i}]),angular.module("trelloCloneApp").controller("NavCtrl",["$scope","$rootScope","AuthenticationService",function(a,b,c){this.awesomeThings=["HTML5 Boilerplate","AngularJS","Karma"],b.$on("userLoggedIn",function(){a.currentUser=c.GetCurrentUser()}),b.$on("userLoggedOut",function(){a.currentUser=c.GetCurrentUser()}),a.currentUser=c.GetCurrentUser()}]),angular.module("trelloCloneApp").run(["$templateCache",function(a){a.put("views/board.html",'<div class="row" ng-cloak> <div class="col-md-12" ng-if="boardLists"> <h4><strong>{{board.name}}</strong></h4> <div class="boardList row"> <div ng-repeat="(listId, list) in boardLists.lists" class="col-md-3"> <div class="panel panel-info"> <div class="panel-heading"><i ng-click="deleteBoardList(listId)" class="fa fa-trash" aria-hidden="true" style="color: lightgrey;float: right"></i> <h3 class="panel-title">{{list.details.name}}</h3> </div> <div class="panel-body"> <ul dnd-list="list.cards"> <li class="list-card" ng-repeat="card in list.cards" dnd-draggable="card" dnd-moved="list.cards.splice($index, 1);updateLists()" dnd-effect-allowed="move" dnd-selected="boardLists.selected = card"> <div ng-controller="CardDetailCtrl as $ctrl"> <a ng-click="$ctrl.open(card, list.details)"> {{card.name}} </a> <i ng-click="deleteCard(card.cardId)" class="fa fa-trash" style="color: lightgrey;float:right" aria-hidden="true"></i> </div> </li> </ul> <div class="panel-footer addCard"> <form ng-submit="addCard(listId, newCard); newCard=null"> <!-- NAME --> <div class="form-group"> <input type="text" name="name" class="form-control" ng-model="newCard.name" required> <span class="help-block"></span> </div> <button type="submit" class="btn btn-success">Add Card</button> </form> </div> </div> </div> </div> </div> <div> <button type="button" class="btn btn-success" data-toggle="modal" data-target=".bs-create-list-modal-sm">Create List </button> </div> </div> </div> <div class="modal fade bs-create-list-modal-sm" role="dialog"> <div class="modal-dialog modal-sm" role="document"> <div class="modal-content" style="padding: 5px"> <!-- FORM --> <form ng-submit="createBoardList()"> <!-- NAME --> <div class="form-group"> <label>Name</label> <input type="text" name="name" class="form-control" placeholder="List name.." - ng-model="newBoardList.name" - required> <span class="help-block"></span> </div> <!-- SUBMIT BUTTON --> <button type="submit" class="btn btn-success btn-block"> Create </button> <!--<br/>--> <!--<textarea style="height:125px; resize: none;" cols="30" rows="10" name="description"--> <!--class="form-control">{{ newBoardList }}</textarea>--> </form> </div> </div> </div> <script type="text/ng-template" id="cardContent.html"><div class="modal-header">\n    <i ng-click="$ctrl.cancel()" class="fa fa-times fa-2x" style="float: right; color: gray" aria-hidden="true"></i>\n    <h4 class="modal-title" id="modal-title">{{$ctrl.card.name}}</h4>\n    in List <strong>{{$ctrl.list.name}}</strong>\n  </div>\n  <div class="modal-body" id="modal-body" style="min-height: 300px;">\n    <!--{{$ctrl.card}}-->\n    <a onaftersave="$ctrl.updateCard($ctrl.card.listId, $ctrl.card)" editable-textarea="$ctrl.card.description"\n       e-rows="7" e-cols="40">\n      <i class="fa fa-pencil" aria-hidden="true" style="float: right"></i>\n      <div btf-markdown="$ctrl.card.description || \'Click to add a card description!\'">\n      </div>\n    </a>\n  </div>\n  <div class="modal-footer">\n    <!--<button class="btn btn-primary" type="button" ng-click="$ctrl.ok()">OK</button>-->\n    <!--<button class="btn btn-warning" type="button" ng-click="$ctrl.cancel()">Cancel</button>-->\n  </div></script>'),a.put("views/boards.html",'<div class="row" ng-cloak> <div class="col-md-12" ng-if="boards.length>=0"> <div class="col-md-12"> <h4><i class="fa fa-user" aria-hidden="true"></i>&nbsp;&nbsp;<strong>Personal Boards</strong></h4> </div> <div class="col-md-12"> <div class="col-md-3" ng-repeat="board in boards"> <div class="panel panel-default"> <div class="panel-heading trelloCloneBoardsHeading"> <a ng-href="#/board/{{board.boardId}}" style="text-decoration: none"><span class="panel-title">{{board.name}} </span></a> <i class="fa fa-trash" ng-click="deleteBoard(board.boardId)" style="color:lightgrey; float:right"></i> </div> <a ng-href="#/board/{{board.boardId}}" style="text-decoration: none"> <div class="trelloCloneBoardsBody panel-body"> </div> </a> </div> </div> <div class="col-md-3"> <div class="panel panel-default"> <div class="panel-heading trelloCloneBoardsCreateHeading"> <a popover-trigger="\'outsideClick\'" popover-placement="auto bottom-left" uib-popover-template="createBoardPopover.templateUrl"><span class="panel-title">Create a board... </span></a> </div> </div> <script type="text/ng-template" id="CreateBoardPopoverTemplate.html"><div style="text-align: center">Create Board</div>\n\n          <form ng-submit="createBoard(newBoard)">\n            <!-- NAME -->\n            <div class="form-group">\n              <label>Name</label>\n              <input type="text" name="name" class="form-control" placeholder="Board name.."\n                     ng-model="newBoard.name"\n                     required>\n              <span class="help-block"></span>\n            </div>\n\n            <br/>\n            <!-- SUBMIT BUTTON -->\n            <button type="submit" class="btn btn-success btn-block">\n              Create\n            </button>\n          </form></script> </div> </div> </div> <div class="col-md-12" ng-if="teams"> <!--<h4><i class="fa fa-users" aria-hidden="true"></i>--> <!--&nbsp;&nbsp;<strong>Team Boards</strong></h4>--> <div class="col-md-12" ng-repeat="(teamName, team) in teams"> <h4><i class="fa fa-users" aria-hidden="true"></i> &nbsp;&nbsp;<strong>{{teamName}}</strong></h4> <style type="text/css">form.tab-form-demo .tab-pane {\n          margin: 20px 20px;\n        }</style> <div> <uib-tabset active="active"> <uib-tab index="0" heading="Boards" disable="tab.disabled"> <br> <div class="col-md-3" ng-repeat="board in team.boards"> <div class="panel panel-default"> <div class="panel-heading trelloCloneBoardsHeading"> <!--<strong>{{board.name}}{{board.name}}</strong>--> <a ng-href="#/board/{{board.boardId}}" style="text-decoration: none"><span class="panel-title">{{board.name}} </span></a> <i class="fa fa-trash" ng-click="deleteBoard(board.boardId)" style="color:lightgrey; float:right"></i> </div> <a ng-href="#/board/{{board.boardId}}" style="text-decoration: none"> <div class="trelloCloneBoardsBody panel-body"> </div> </a> </div> </div> <div class="col-md-3"> <div class="panel panel-default"> <div class="panel-heading trelloCloneBoardsCreateHeading"> <a popover-trigger="\'outsideClick\'" popover-placement="auto bottom-left" uib-popover-template="createTeamBoardPopover.templateUrl"><span class="panel-title">Create a board... </span></a> </div> </div> <script type="text/ng-template" id="CreateTeamBoardPopoverTemplate.html"><div style="text-align: center">Create Team Board</div>\n\n                <form ng-submit="createTeamBoard(newTeamBoard)">\n                  <!-- NAME -->\n                  <div class="form-group">\n                    <label>Name</label>\n                    <input type="text" name="name" class="form-control" placeholder="Board name.."\n                           ng-model="newTeamBoard.name"\n                           required>\n                    <span class="help-block"></span>\n                  </div>\n\n                  <div ng-init="newTeamBoard.teamId=team.details.teamId"><strong>Team</strong> {{team.details.name}}\n                  </div>\n\n                  <br/>\n                  <!-- SUBMIT BUTTON -->\n                  <button type="submit" class="btn btn-success btn-block">\n                    Create\n                  </button>\n                </form></script> </div> </uib-tab> <uib-tab index="1" heading="Members" select="getTeamMembers(team.details.teamId)"> <br> <div class="col-md-12" ng-if="teamMembers"> <div class="col-md-3"> <a popover-trigger="\'outsideClick\'" popover-placement="auto bottom-left" uib-popover-template="createTeamMemberPopover.templateUrl" style="border-bottom: 1px solid black; text-decoration: none"><span class="panel-title">Add a team member... </span></a> <script type="text/ng-template" id="CreateTeamMemberPopoverTemplate.html"><form ng-submit="addTeamMember(team.details.teamId, selected)">\n                    <div style="text-align: center">Add Team Member</div>\n                    <br/>\n\n                    <input type="text" ng-model="selected"\n                           uib-typeahead="user as user.username for user in users | filter:$viewValue | limitTo:8"\n                           class="form-control">\n\n                    <br/>\n\n                    <!-- SUBMIT BUTTON -->\n                    <button type="submit" class="btn btn-success btn-block">\n                      Add\n                    </button>\n                  </form></script> </div> <div class="col-md-9"> <div ng-repeat="teamMember in teamMembers"> <div> {{teamMember.username}} <i class="fa fa-trash" ng-click="removeTeamMember(team.details.teamId, teamMember.teamMemberId)" style="color:red; float:right"></i> </div> <hr> </div> </div> </div> </uib-tab> <uib-tab index="2" heading="Settings" disable="true"></uib-tab> </uib-tabset> </div> </div> <div class="col-md-12"> <br> <div class="col-md-3"> <a popover-trigger="\'outsideClick\'" popover-placement="auto bottom-left" uib-popover-template="createTeamPopover.templateUrl" style="border-bottom: 1px solid black; text-decoration: none"><span class="panel-title">Create a new team... </span></a> <script type="text/ng-template" id="CreateTeamPopoverTemplate.html"><div style="text-align: center">Create Team</div>\n\n          <!-- FORM -->\n          <form ng-submit="createTeam(newTeam)">\n            <!-- NAME -->\n            <div class="form-group">\n              <label>Name</label>\n              <input type="text" name="name" class="form-control" placeholder="Team name.."\n                     ng-model="newTeam.name"\n                     required>\n              <span class="help-block"></span>\n            </div>\n\n            <!-- DESCRIPTION -->\n            <div id="name-group" class="form-group">\n              <label>Description (Optional)</label>\n\n              <textarea style="height:125px; resize: none;" cols="30" rows="10" name="description"\n                        class="form-control" placeholder="Description" ng-model="newTeam.description"></textarea>\n              <span class="help-block"></span>\n            </div>\n\n            <!-- SUBMIT BUTTON -->\n            <button type="submit" class="btn btn-success btn-block">\n              Create\n            </button>\n\n            <p>A team is a group of boards and people. It helps keep your company, team, or family organized.</p>\n          </form></script> </div> </div> </div> </div>'),a.put("views/main.html",'<div class="row"> <div class="col-sm-12" style="text-align: center"> <h2>Welcome to the Trello-clone project!</h2> <br> <div class="col-sm-12"> <div class="col-sm-2"> </div> <div class="col-sm-8"> <!-- SHOW ERROR/SUCCESS MESSAGES --> <div uib-alert ng-repeat="alert in alerts" ng-class="\'alert-\' + (alert.type || \'warning\')" close="closeAlert($index)">{{alert.msg}} </div> </div> <div class="col-sm-2"> </div> </div> </div> <div> <div class="col-sm-2"> </div> <div class="col-sm-4"> <div class="panel panel-default" style="text-align: left"> <div class="panel-heading"> <h4>Login</h4> </div> <div class="panel-body"> <div class="box box-info"> <div class="box-body"> <!-- FORM --> <form ng-submit="login()"> <!-- USERNAME --> <div class="form-group"> <label>Username</label> <input type="text" name="username" class="form-control" placeholder="Username" ng-model="user.username" required> <span class="help-block"></span> </div> <!-- PASSWORD --> <div class="form-group"> <label>Password</label> <input type="password" name="password" class="form-control" placeholder="Password" ng-model="user.password" required> <span class="help-block"></span> </div> <!-- SUBMIT BUTTON --> <button type="submit" class="btn btn-success btn-lg btn-block"> Login </button> </form> <!--<br/>--> <!--<textarea style="height:125px; resize: none;" cols="30" rows="10" name="description"--> <!--class="form-control">{{ user }}</textarea>--> </div> </div> </div> </div> </div> <div class="col-sm-4"> <div class="panel panel-default"> <div class="panel-heading"> <h4>Register</h4> </div> <div class="panel-body"> <div class="box box-info"> <div class="box-body"> <!-- FORM --> <form ng-submit="register()"> <!-- USERNAME --> <div class="form-group"> <label>Username</label> <input type="text" name="username" class="form-control" placeholder="Username" ng-model="newUser.username" required> <span class="help-block"></span> </div> <!-- PASSWORD --> <div class="form-group"> <label>Password</label> <input type="password" name="password" class="form-control" placeholder="Password" ng-model="newUser.password" required> <span class="help-block"></span> </div> <!-- SUBMIT BUTTON --> <button type="submit" class="btn btn-success btn-lg btn-block"> Register </button> </form> <!--&lt;!&ndash;<br/>&ndash;&gt;--> <!--<textarea style="height:125px; resize: none;" cols="30" rows="10" name="description"--> <!--class="form-control">{{ newUser }}</textarea>--> </div> </div> </div> </div> </div> <div class="col-sm-2"> </div> </div> </div>')}]);