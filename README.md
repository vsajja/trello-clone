# trello-clone

The purpose of this project is to create a simple Trello clone for demonstration purposes.

# UI

## Views

* main.html
    * Login
* boards.html
    * View list of boards
    * Create boards
    * Delete boards
* board.html
    * View board lists including cards
    * Drag & drop cards onto lists
    * Create board lists
    * Delete board lists
    * Add card to board list
    * View card details

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
    * PUT /boards/{board_id}/lists
    * DELETE /boards/{board_id}/lists/{board_list_id}

* card
    * POST /lists/{list_id}/cards
    * DELETE /cards/{card_id}

# Database

## Schema 
![current trello-clone database schema](https://i.imgur.com/MsWygug.png)

# Heroku
https://trello-clone-app.herokuapp.com
