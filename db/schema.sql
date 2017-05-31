CREATE TABLE board
(
    board_id INTEGER PRIMARY KEY NOT NULL,
    name VARCHAR
);
CREATE UNIQUE INDEX board_name_uindex ON board (name);
CREATE TABLE board_list
(
    list_id INTEGER PRIMARY KEY NOT NULL,
    name VARCHAR,
    board_id INTEGER NOT NULL,
    CONSTRAINT board_list_board_board_id_fk FOREIGN KEY (board_id) REFERENCES board (board_id)
);
CREATE TABLE card
(
    card_id INTEGER PRIMARY KEY NOT NULL,
    name VARCHAR,
    description VARCHAR,
    list_id INTEGER NOT NULL,
    CONSTRAINT card_board_list_list_id_fk FOREIGN KEY (list_id) REFERENCES board_list (list_id)
);
CREATE TABLE "user"
(
    user_id INTEGER PRIMARY KEY NOT NULL,
    username VARCHAR,
    password VARCHAR
);