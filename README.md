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

# Heroku
https://trello-clone-app.herokuapp.com
