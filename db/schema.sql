CREATE TABLE board
(
    board_id INTEGER PRIMARY KEY NOT NULL,
    name VARCHAR
);
CREATE TABLE board_list
(
    board_list_id INTEGER PRIMARY KEY NOT NULL,
    name VARCHAR,
    board_id INTEGER NOT NULL
);
CREATE TABLE "user"
(
    user_id INTEGER PRIMARY KEY NOT NULL,
    username VARCHAR,
    password VARCHAR
);
CREATE UNIQUE INDEX board_name_uindex ON board (name);
ALTER TABLE board_list ADD FOREIGN KEY (board_id) REFERENCES board (board_id);