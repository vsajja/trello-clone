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
    
* user
    * GET /users
    * GET /users/{user_id}/boards
    * POST /users/{user_id}/boards
    * GET /users/{user_id}/teams
    * POST /users/{user_id}/teams

* team
    * POST /teams/{team_id}/boards
    * GET /teams/{team_id}/members
    * POST /teams/{team_id}/members

* team_member
    * DELETE /teams/members/{team_member_id}
       
* board
    * GET /boards/{board_id}
    * DELETE /boards/{board_id}

* board_list
    * GET /boards/{board_id}/lists
    * POST /boards/{board_id}/lists
    * PUT /boards/{board_id}/lists
    * DELETE /boards/{board_id}/lists/{board_list_id}

* card
    * POST /lists/{list_id}/cards
    * PUT /lists/{list_id}/cards
    * DELETE /cards/{card_id}

* POST /login
* POST /register

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
