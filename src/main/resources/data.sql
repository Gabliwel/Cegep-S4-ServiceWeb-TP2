INSERT INTO TRAIL(ID, CITY, DESCRIPTION, DIFFICULTY, LAST_MAINTENANCE_DATE, NAME, OPENING_DATE, STATUS, AVERAGE_SCORE)
VALUES('1', 'quebec', 'premier trail', 1, parseDatetime('2021-12-31', 'yyyy-MM-dd'), 'bonsoir1', parseDatetime('1999-12-31', 'yyyy-MM-dd'), 1, 1.0);

INSERT INTO TRAIL(ID, CITY, DESCRIPTION, DIFFICULTY, LAST_MAINTENANCE_DATE, NAME, OPENING_DATE, STATUS, AVERAGE_SCORE)
VALUES('2', 'montreal', 'deuxieme trail', 1, parseDatetime('2021-12-30', 'yyyy-MM-dd'), 'bonsoir2', parseDatetime('2025-12-31', 'yyyy-MM-dd'), 0, 2.0);

INSERT INTO TRAIL(ID, CITY, DESCRIPTION, DIFFICULTY, LAST_MAINTENANCE_DATE, NAME, OPENING_DATE, STATUS, AVERAGE_SCORE)
VALUES('3', 'jsp', 'troisieme trail', 1, parseDatetime('2021-12-29', 'yyyy-MM-dd'), 'bonsoir3', parseDatetime('1997-12-31', 'yyyy-MM-dd'), 1, 4);

INSERT INTO EVENT(ID, DESCRIPTION, NAME, ORGANIZER, START_DATE, TRAIL)
VALUES('1', 'un endroit magnifiiiiique', 'event de bob1', 'bob1', parseDatetime('2022-01-02', 'yyyy-MM-dd'), '1');

INSERT INTO EVENT(ID, DESCRIPTION, NAME, ORGANIZER, START_DATE, TRAIL)
VALUES('2', 'un endroit incroyaaable', 'event de bob2', 'bob2', parseDatetime('2022-01-02', 'yyyy-MM-dd'), '2');

INSERT INTO EVENT(ID, DESCRIPTION, NAME, ORGANIZER, START_DATE, TRAIL)
VALUES('3', 'jsp', 'event de bob2', 'bob2', parseDatetime('2022-03-14', 'yyyy-MM-dd'), '3');

insert into ROLE (ID, ROLE_NAME)
values ('1', 'ADMIN');

insert into ROLE (ID, ROLE_NAME)
values ('2', 'USER');

--Le mot de passe est "admin"
INSERT INTO USER(ID, FIRSTNAME, LASTNAME, EMAIL, PASSWORD, PREFERRED_DIFFICULTY, ROLE_ID)
VALUES('1', 'Bob', 'JSP', 'a@bc.de', '$2a$10$FF7aPyru4XeO2vUdz0rvZulO7dPWVPuEzux7FiPm3XuPCkzF9UgrG', 0, '1');

--Le mot de passe est "test"
INSERT INTO USER(ID, FIRSTNAME, LASTNAME, EMAIL, PASSWORD, PREFERRED_DIFFICULTY, ROLE_ID)
VALUES('2', 'Bob2', 'JSP encore', 'z@a.ca', '$2a$10$qe5.ImGIOuqlJ24LSDE/2OnEiiIM0vtvDABaalo3UTRiK3OBScrvW', 1, '2');


INSERT INTO USER(ID, FIRSTNAME, LASTNAME, EMAIL, PASSWORD, PREFERRED_DIFFICULTY, ROLE_ID)
VALUES('3', 'Bob3', 'JSP encore', 'john.smith@invalidemail.com', '$2y$12$IZoojYcMZkgcST/CUBTQaO0sfPGcBjUsq4U7U8KcKK16eAH7ZgbUC', 1, '2');

INSERT INTO RATING (ID, COMMENT, NOTE, TRAIL, USER)
VALUES('1', 'ark!', 1, '1', '2');

INSERT INTO RATING (ID, COMMENT, NOTE, TRAIL, USER)
VALUES('2', 'bof.....', 2, '2', '1');

INSERT INTO RATING (ID, COMMENT, NOTE, TRAIL, USER)
VALUES('3', 'Incroyable!!!!!!!!!!!!!!!!!!!', 4, '3', '1');

INSERT INTO USER_FAVORITE_TRAILS(USER_ID, TRAIL_ID)
VALUES('2', '2');

INSERT INTO USER_TRAILS_TO_TRY(USER_ID, TRAIL_ID)
VALUES('2', '1');

INSERT INTO BADGE(ID, NAME, DESCRIPTION, ICONE, CATEGORY)
VALUES('1', 'Thor God of Thunder', 'Complete an event in raining conditions', 'thor_icon.jpg', 0);

INSERT INTO BADGE(ID, NAME, DESCRIPTION, ICONE, CATEGORY)
VALUES('2', 'Windy Darling', 'Complete an event where the wind is at least 10km/h', 'wind_icon.jpg', 0);

INSERT INTO BADGE(ID, NAME, DESCRIPTION, ICONE, CATEGORY)
VALUES('3', 'Frozen', 'Complete an event in a winterstorm', 'snow_icon.jpg', 0);

INSERT INTO BADGE(ID, NAME, DESCRIPTION, ICONE, CATEGORY)
VALUES('4', 'The Social Network', 'Complete three events in the same week', 'social_icon.jpg', 1);

INSERT INTO BADGE(ID, NAME, DESCRIPTION, ICONE, CATEGORY)
VALUES('5', 'The Busy Weekend', 'Complete two events in a weekend', 'weekend_icon.jpg', 1);

INSERT INTO USER_BADGES(USER_ID, BADGE_ID)
VALUES('2', '1');

INSERT INTO USER_BADGES(USER_ID, BADGE_ID)
VALUES('2', '2');

INSERT INTO USER_BADGES(USER_ID, BADGE_ID)
VALUES('1', '4');