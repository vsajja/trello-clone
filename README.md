# trello-clone

The purpose of this project is to create a simple Trello clone for demonstration purposes.

# UI

## Views

* main.html
    * login
    * register
* boards.html
    * boards
    * teams
        * team boards
        * team members
        * team settings
* board.html
    * board lists
        * cards

# Server

## REST API

#### user
- **[<code>POST</code> register](https://github.com/vsajja/trello-clone/blob/master/docs/api/v1/endpoints/user/POST_register.md)**
- **[<code>POST</code> login](https://github.com/vsajja/trello-clone/blob/master/docs/api/v1/endpoints/user/POST_login.md)**
- **[<code>GET</code> users](https://github.com/vsajja/trello-clone/blob/master/docs/api/v1/endpoints/user/GET_users.md)**

#### board
- **[<code>GET</code> users/:user_id/boards](https://github.com/vsajja/trello-clone/blob/master/docs/api/v1/endpoints/board/GET_user_boards.md)**
- **[<code>POST</code> users/:user_id/boards](https://github.com/vsajja/trello-clone/blob/master/docs/api/v1/endpoints/board/POST_user_boards.md)**
- **[<code>GET</code> boards/:board_id](https://github.com/vsajja/trello-clone/blob/master/docs/api/v1/endpoints/board/)**
- **[<code>DELETE</code> boards/:board_id](https://github.com/vsajja/trello-clone/blob/master/docs/api/v1/endpoints/board/)**
- **[<code>POST</code> teams/:team_id/boards]()**

#### board_list
- **[<code>GET</code> boards/:board_id/lists]()**
- **[<code>POST</code> boards/:board_id/lists]()**
- **[<code>DELETE</code> boards/:board_id/lists/board_list_id]()**

#### card
- **[<code>POST</code> lists/:list_id/cards]()**
- **[<code>PUT</code> lists/:list_id/cards]()**
- **[<code>DELETE</code> cards/:card_id]()**

#### team
- **[<code>GET</code> users/:user_id/teams]()**
- **[<code>POST</code> users/:user_id/teams]()**
- **[<code>GET</code> teams/:team_id/members]()**
- **[<code>POST</code> teams/:team_id/members]()**

#### team_member
- **[<code>DELETE</code> teams/members/:team_member_id]()**

# Database

## Schema 
![current trello-clone database schema](http://i.imgur.com/rJjfidm.png)

# Testing

The project is setup with both front-end and back-end tests which should be updated and run before committing changes.

## Front-end tests

The front-end tests are unit tests run by karma. The initial tests are automatically generated when creating controllers
and services using the [Yeoman angular generators](https://github.com/yeoman/generator-angular#readme).

Location:
* The front-end tests are located in the *trello-clone/ui/test* directory.

Running front-end tests:
* Navigate to the *trello-clone/ui* directory
* Execute `grunt test` to run the unit tests with karma.

## Back-end tests

The back-end tests are written in Groovy using the [Spock testing framework](http://spockframework.org/). The back-end tests
will contain unit tests as well as bigger tests that test the REST API using a test database.

Location:
* The back-end tests are located in the *trello-clone/src/test* directory.

Running back-end tests:
* Navigate to the *trello-clone* main directory
* Execute `./gradlew clean test` to run the back-end tests

# Heroku
https://trello-clone-app.herokuapp.com
