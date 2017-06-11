CREATE TABLE board
(
    board_id INTEGER PRIMARY KEY NOT NULL,
    name VARCHAR,
    user_id INTEGER,
    CONSTRAINT board_user_user_id_fk FOREIGN KEY (user_id) REFERENCES "user" (user_id)
);
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
CREATE TABLE team
(
    team_id INTEGER PRIMARY KEY NOT NULL,
    name VARCHAR,
    description VARCHAR
);
CREATE UNIQUE INDEX team_name_uindex ON team (name);
CREATE TABLE team_board
(
    team_board_id INTEGER PRIMARY KEY NOT NULL,
    team_id INTEGER,
    board_id INTEGER,
    CONSTRAINT team_board_team_team_id_fk FOREIGN KEY (team_id) REFERENCES team (team_id),
    CONSTRAINT team_board_board_board_id_fk FOREIGN KEY (board_id) REFERENCES board (board_id)
);
CREATE TABLE team_member
(
    team_member_id INTEGER PRIMARY KEY NOT NULL,
    team_id INTEGER,
    user_id INTEGER,
    CONSTRAINT team_member_team_team_id_fk FOREIGN KEY (team_id) REFERENCES team (team_id),
    CONSTRAINT team_member_user_user_id_fk FOREIGN KEY (user_id) REFERENCES "user" (user_id)
);
CREATE TABLE "user"
(
    user_id INTEGER PRIMARY KEY NOT NULL,
    username VARCHAR,
    password VARCHAR
);
CREATE UNIQUE INDEX user_username_uindex ON "user" (username);