# trello-clone

The purpose of this project is to create a simple Trello clone for demonstration purposes.

# UI

## Views

* main.html
    * Login
    * Register
* boards.html
    * View boards
    * Create boards
    * Delete boards
    * Create team
    * View team boards
    * Delete team boards
* board.html
    * View board lists including cards
    * Drag & drop cards onto lists
    * Create board lists
    * Delete board lists
    * Add card to board list
    * View card details

# Server

## REST API

* user
    * GET /users/{user_id}/boards
    * POST /users/{user_id}/boards

* team
    * GET /teams
    * POST /teams
    * POST /teams/{team_id}/boards
   
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
    * DELETE /cards/{card_id}
    
* POST /login
* POST /register

# Database

## Schema 
![current trello-clone database schema](http://i.imgur.com/QIQv7nj.png)

# Heroku
https://trello-clone-app.herokuapp.com
