SELECT datname
FROM pg_database;
-- CREATE DATABASE dboj;
DROP TABLE IF EXISTS QUESTION_BUILD;
DROP TABLE IF EXISTS QUESTION_DETAIL;
DROP TABLE IF EXISTS COMMIT_RESULT;
DROP TABLE IF EXISTS QUESTION;
DROP TABLE IF EXISTS COMMIT_LOG;
DROP TABLE IF EXISTS USER_TABLE;
CREATE TABLE USER_TABLE
(
    USER_ID    SERIAL PRIMARY KEY,
    USERNAME   TEXT UNIQUE NOT NULL,
    PASSWORD   TEXT        NOT NULL,
    USER_LEVEL INT         NOT NULL,
    EMALL      TEXT UNIQUE NOT NULL
);
insert into USER_TABLE (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES ('12345', '67890', 0, 'test@case.com');
insert into USER_TABLE (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES ('1234567', '67890', 0, 'test@case2.com');
insert into USER_TABLE (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES ('12345678', '67890', 0, 'test@case3.com');
insert into USER_TABLE (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES ('123456789', '67890', 1, 'test@case4.com');
CREATE TABLE COMMIT_LOG
(
    COMMIT_LOG_ID  SERIAL PRIMARY KEY,
    USER_ID        INT  NOT NULL REFERENCES USER_TABLE (USER_ID),
    QUESTION_ORDER INT  NOT NULL,
    COMMIT_CODE    TEXT NOT NULL,
    LANGUAGE       INT  NOT NULL,
    STATE          INT  NOT NULL
);
insert into COMMIT_LOG (USER_ID, QUESTION_ORDER, COMMIT_CODE, LANGUAGE, STATE)
VALUES (1,1,'select * from usertable1;',0,4);
insert into COMMIT_LOG (USER_ID, QUESTION_ORDER, COMMIT_CODE, LANGUAGE, STATE)
VALUES (1,1,'select * from usertable2;',1,4);
insert into COMMIT_LOG (USER_ID, QUESTION_ORDER, COMMIT_CODE, LANGUAGE, STATE)
VALUES (1,2,'select * from usertable3;',2,4);
insert into COMMIT_LOG (USER_ID, QUESTION_ORDER, COMMIT_CODE, LANGUAGE, STATE)
VALUES (1,3,'select * from usertable4;',0,4);
CREATE TABLE COMMIT_RESULT
(
    COMMIT_LOG_ID INT  NOT NULL REFERENCES COMMIT_LOG (COMMIT_LOG_ID),
    table_ORDER   INT  NOT NULL,
    COMMIT_RESULT_ TEXT NOT NULL,
    CPUTIME       INT  NOT NULL,
    RAMSIZE       INT  NOT NULL, --INKB
    PRIMARY KEY (COMMIT_LOG_ID, table_ORDER, COMMIT_RESULT_)
);
insert into COMMIT_RESULT (COMMIT_LOG_ID, table_ORDER, COMMIT_RESULT_, CPUTIME, RAMSIZE)
VALUES (1,0,'error',0,0);
insert into COMMIT_RESULT (COMMIT_LOG_ID, table_ORDER, COMMIT_RESULT_, CPUTIME, RAMSIZE)
VALUES (2,0,'error',0,0);
insert into COMMIT_RESULT (COMMIT_LOG_ID, table_ORDER, COMMIT_RESULT_, CPUTIME, RAMSIZE)
VALUES (3,0,'error',0,0);
CREATE TABLE QUESTION
(
    PROGRAM_ORDER SERIAL PRIMARY KEY,
    NAME          TEXT    NOT NULL,
    DESCRIPTION   TEXT    NOT NULL,
    DEADLINE      INTEGER NOT NULL
);
INSERT INTO QUESTION (NAME, DESCRIPTION, DEADLINE)
VALUES ('homework1', 'this is the 1st homework', 100);
INSERT INTO QUESTION (NAME, DESCRIPTION, DEADLINE)
VALUES ('homework2', 'this is the 2nd homework', 200);
INSERT INTO QUESTION (NAME, DESCRIPTION, DEADLINE)
VALUES ('homework3', 'this is the 3rd homework', 300);
select *
from question;
CREATE TABLE QUESTION_DETAIL
(
    PROGRAM_ORDER  INT  NOT NULL REFERENCES QUESTION (PROGRAM_ORDER),
    CORRECT_SCRIPT TEXT NOT NULL,
    LANGUAGE       INT  NOT NULL,
    PRIMARY KEY (PROGRAM_ORDER, LANGUAGE)
);
Insert into question_detail (PROGRAM_ORDER, CORRECT_SCRIPT, LANGUAGE)
VALUES (1, 'select * from usertable', 0);
Insert into question_detail (PROGRAM_ORDER, CORRECT_SCRIPT, LANGUAGE)
VALUES (1, 'select * from usertable', 1);
Insert into question_detail (PROGRAM_ORDER, CORRECT_SCRIPT, LANGUAGE)
VALUES (1, 'select * from usertable', 2);
Insert into question_detail (PROGRAM_ORDER, CORRECT_SCRIPT, LANGUAGE)
VALUES (2, 'select * from usertable', 0);
Insert into question_detail (PROGRAM_ORDER, CORRECT_SCRIPT, LANGUAGE)
VALUES (2, 'select * from usertable', 1);
Insert into question_detail (PROGRAM_ORDER, CORRECT_SCRIPT, LANGUAGE)
VALUES (2, 'select * from usertable', 2);
Insert into question_detail (PROGRAM_ORDER, CORRECT_SCRIPT, LANGUAGE)
VALUES (3, 'select * from usertable', 0);
Insert into question_detail (PROGRAM_ORDER, CORRECT_SCRIPT, LANGUAGE)
VALUES (3, 'select * from usertable', 1);
Insert into question_detail (PROGRAM_ORDER, CORRECT_SCRIPT, LANGUAGE)
VALUES (3, 'select * from usertable', 2);
CREATE TABLE QUESTION_BUILD
(
    QUESTION_TABLE_ORDER SERIAL PRIMARY KEY,
    PROGRAM_ORDER        INT  NOT NULL REFERENCES QUESTION (PROGRAM_ORDER),
    BUILD_SCRIPT         TEXT NOT NULl,
    LANGUAGE             INT  NOT NULL
);
Insert into QUESTION_BUILD (PROGRAM_ORDER, BUILD_SCRIPT, LANGUAGE)
VALUES (1, '
CREATE TABLE usertable
(
    USER_ID    SERIAL PRIMARY KEY,
    USERNAME   TEXT UNIQUE NOT NULL,
    PASSWORD   TEXT        NOT NULL,
    USER_LEVEL INT         NOT NULL,
    EMALL      TEXT UNIQUE NOT NULL
);
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''12345'', ''67890'', 0, ''test@case.com'');
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''1234567'', ''67890'', 0, ''test@case2.com'');
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''12345678'', ''67890'', 0, ''test@case3.com'');
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''123456789'', ''67890'', 1, ''test@case4.com'');
', 0);
Insert into QUESTION_BUILD (PROGRAM_ORDER, BUILD_SCRIPT, LANGUAGE)
VALUES (1, '
CREATE TABLE usertable
(
    USER_ID    SERIAL PRIMARY KEY,
    USERNAME   TEXT UNIQUE NOT NULL,
    PASSWORD   TEXT        NOT NULL,
    USER_LEVEL INT         NOT NULL,
    EMALL      TEXT UNIQUE NOT NULL
);
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''12345'', ''67890'', 0, ''test@case.com'');
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''1234567'', ''67890'', 0, ''test@case2.com'');
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''12345678'', ''67890'', 0, ''test@case3.com'');
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''123456789'', ''67890'', 1, ''test@case4.com'');
', 1);
Insert into QUESTION_BUILD (PROGRAM_ORDER, BUILD_SCRIPT, LANGUAGE)
VALUES (1, '
CREATE TABLE usertable
(
    USER_ID    SERIAL PRIMARY KEY,
    USERNAME   TEXT UNIQUE NOT NULL,
    PASSWORD   TEXT        NOT NULL,
    USER_LEVEL INT         NOT NULL,
    EMALL      TEXT UNIQUE NOT NULL
);
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''12345'', ''67890'', 0, ''test@case.com'');
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''1234567'', ''67890'', 0, ''test@case2.com'');
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''12345678'', ''67890'', 0, ''test@case3.com'');
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''123456789'', ''67890'', 1, ''test@case4.com'');
', 2);
Insert into QUESTION_BUILD (PROGRAM_ORDER, BUILD_SCRIPT, LANGUAGE)
VALUES (2, '
CREATE TABLE usertable
(
    USER_ID    SERIAL PRIMARY KEY,
    USERNAME   TEXT UNIQUE NOT NULL,
    PASSWORD   TEXT        NOT NULL,
    USER_LEVEL INT         NOT NULL,
    EMALL      TEXT UNIQUE NOT NULL
);
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''12345'', ''67890'', 0, ''test@case.com'');
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''1234567'', ''67890'', 0, ''test@case2.com'');
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''12345678'', ''67890'', 0, ''test@case3.com'');
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''123456789'', ''67890'', 1, ''test@case4.com'');
', 0);
Insert into QUESTION_BUILD (PROGRAM_ORDER, BUILD_SCRIPT, LANGUAGE)
VALUES (2, '
CREATE TABLE usertable
(
    USER_ID    SERIAL PRIMARY KEY,
    USERNAME   TEXT UNIQUE NOT NULL,
    PASSWORD   TEXT        NOT NULL,
    USER_LEVEL INT         NOT NULL,
    EMALL      TEXT UNIQUE NOT NULL
);
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''12345'', ''67890'', 0, ''test@case.com'');
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''1234567'', ''67890'', 0, ''test@case2.com'');
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''12345678'', ''67890'', 0, ''test@case3.com'');
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''123456789'', ''67890'', 1, ''test@case4.com'');
', 1);
Insert into QUESTION_BUILD (PROGRAM_ORDER, BUILD_SCRIPT, LANGUAGE)
VALUES (2, '
CREATE TABLE usertable
(
    USER_ID    SERIAL PRIMARY KEY,
    USERNAME   TEXT UNIQUE NOT NULL,
    PASSWORD   TEXT        NOT NULL,
    USER_LEVEL INT         NOT NULL,
    EMALL      TEXT UNIQUE NOT NULL
);
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''12345'', ''67890'', 0, ''test@case.com'');
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''1234567'', ''67890'', 0, ''test@case2.com'');
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''12345678'', ''67890'', 0, ''test@case3.com'');
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''123456789'', ''67890'', 1, ''test@case4.com'');
', 2);
Insert into QUESTION_BUILD (PROGRAM_ORDER, BUILD_SCRIPT, LANGUAGE)
VALUES (3, '
CREATE TABLE usertable
(
    USER_ID    SERIAL PRIMARY KEY,
    USERNAME   TEXT UNIQUE NOT NULL,
    PASSWORD   TEXT        NOT NULL,
    USER_LEVEL INT         NOT NULL,
    EMALL      TEXT UNIQUE NOT NULL
);
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''12345'', ''67890'', 0, ''test@case.com'');
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''1234567'', ''67890'', 0, ''test@case2.com'');
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''12345678'', ''67890'', 0, ''test@case3.com'');
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''123456789'', ''67890'', 1, ''test@case4.com'');
', 0);
Insert into QUESTION_BUILD (PROGRAM_ORDER, BUILD_SCRIPT, LANGUAGE)
VALUES (3, '
CREATE TABLE usertable
(
    USER_ID    SERIAL PRIMARY KEY,
    USERNAME   TEXT UNIQUE NOT NULL,
    PASSWORD   TEXT        NOT NULL,
    USER_LEVEL INT         NOT NULL,
    EMALL      TEXT UNIQUE NOT NULL
);
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''12345'', ''67890'', 0, ''test@case.com'');
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''1234567'', ''67890'', 0, ''test@case2.com'');
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''12345678'', ''67890'', 0, ''test@case3.com'');
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''123456789'', ''67890'', 1, ''test@case4.com'');
', 1);
Insert into QUESTION_BUILD (PROGRAM_ORDER, BUILD_SCRIPT, LANGUAGE)
VALUES (3, '
CREATE TABLE usertable
(
    USER_ID    SERIAL PRIMARY KEY,
    USERNAME   TEXT UNIQUE NOT NULL,
    PASSWORD   TEXT        NOT NULL,
    USER_LEVEL INT         NOT NULL,
    EMALL      TEXT UNIQUE NOT NULL
);
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''12345'', ''67890'', 0, ''test@case.com'');
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''1234567'', ''67890'', 0, ''test@case2.com'');
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''12345678'', ''67890'', 0, ''test@case3.com'');
insert into usertable (USERNAME, PASSWORD, USER_LEVEL, EMALL)
VALUES (''123456789'', ''67890'', 1, ''test@case4.com'');
', 2);
select *
from question_build;
select *
from user_table;