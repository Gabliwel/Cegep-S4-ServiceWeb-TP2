INSERT INTO TRAIL(ID, CITY, DESCRIPTION, DIFFICULTY, LAST_MAINTENANCE_DATE, NAME, OPENING_DATE)
VALUES('1', 'quebec', 'premier trail', 1, parseDatetime('2021-12-31', 'yyyy-mm-dd'), 'bonsoir1', parseDatetime('1999-12-31', 'yyyy-mm-dd'));

INSERT INTO TRAIL(ID, CITY, DESCRIPTION, DIFFICULTY, LAST_MAINTENANCE_DATE, NAME, OPENING_DATE)
VALUES('2', 'montreal', 'deuxieme trail', 1, parseDatetime('2021-12-30', 'yyyy-mm-dd'), 'bonsoir2', parseDatetime('1998-12-31', 'yyyy-mm-dd'));

INSERT INTO TRAIL(ID, CITY, DESCRIPTION, DIFFICULTY, LAST_MAINTENANCE_DATE, NAME, OPENING_DATE)
VALUES('3', 'jsp', 'troisieme trail', 1, parseDatetime('2021-12-29', 'yyyy-mm-dd'), 'bonsoir3', parseDatetime('1997-12-31', 'yyyy-mm-dd'));

INSERT INTO EVENT(ID, DESCRIPTION, NAME, ORGANIZER, START_DATE, TRAIL)
VALUES('1', 'un endroit magnifiiiiique', 'event de bob1', 'bob1', parseDatetime('2022-01-02', 'yyyy-mm-dd'), '1');

INSERT INTO EVENT(ID, DESCRIPTION, NAME, ORGANIZER, START_DATE, TRAIL)
VALUES('2', 'un endroit incroyaaable', 'event de bob2', 'bob2', parseDatetime('2022-01-02', 'yyyy-mm-dd'), '2');

INSERT INTO EVENT(ID, DESCRIPTION, NAME, ORGANIZER, START_DATE, TRAIL)
VALUES('3', 'jsp', 'event de bob2', 'bob2', parseDatetime('2022-01-02', 'yyyy-mm-dd'), '3');