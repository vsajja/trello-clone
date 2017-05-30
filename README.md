# trello-clone

The purpose of this project is to create a simple Trello clone for demonstration purposes.

#UI

## Views

* main.html
    * Login
* boards.html
    * View list of boards
    * Create boards
    * Delete boards
* board.html
    * View board lists
    * Create board lists
    * Delete board lists

# Server

## REST API

* board
    * GET /boards
    * GET /boards/{board_id}
    * POST /boards
    * DELETE /boards/{board_id}

* board_list
    * GET /boards/{board_id}/lists
    * POST /boards/{board_id}/lists   
    * DELETE /boards/{board_id}/lists/{board_list_id}

# Database

## Schema 
![current trello-clone database schema](http://i.imgur.com/gRFAUiz.png)

# Heroku
https://trello-clone-app.herokuapp.com