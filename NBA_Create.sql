DROP DATABASE IF EXISTS CelticsDB;
CREATE DATABASE CelticsDB;
USE CelticsDB;

DROP TABLE IF EXISTS team;

CREATE TABLE team (
    franchise VARCHAR(50) PRIMARY KEY,
    fromYear INT,
    toYear INT,
    wins INT,
    losses INT,
    numPlyfs INT,
    numDiv INT,
    numConf INT,
    numChamp INT
);

DROP TABLE IF EXISTS roster;
CREATE TABLE roster (
    playerNum INT,
    lName VARCHAR(50) PRIMARY KEY,
    fName VARCHAR(50),
    pos VARCHAR(2),
    height INT,
    weight INT,
    birthday DATE,
    yearsExp INT,
    college VARCHAR(144),
    team VARCHAR(50),
    CONSTRAINT team_fk FOREIGN KEY (team)
        REFERENCES team (franchise)
);
 
 DROP TABLE IF EXISTS seasonSched;
CREATE TABLE seasonSched (
    gameDate DATE PRIMARY KEY,
    celtics VARCHAR(50),
    opponent VARCHAR(50),
    celtPoints INT,
	oppPoints INT,
    isHome TINYINT(1),
    isWin TINYINT(1),
    CONSTRAINT home_fk FOREIGN KEY (celtics)
        REFERENCES team (franchise),
    CONSTRAINT away_fk FOREIGN KEY (opponent)
        REFERENCES team (franchise)
);

DROP TABLE IF EXISTS playerGameStats;
CREATE TABLE playerGameStats (
	gameDate DATE,
    lName VARCHAR(50),
    fName VARCHAR(50),
    mp DOUBLE,
    fg INT,
    fga INT,
    threeP INT,
    threePA INT,
    ft INT,
    fta INT,
    orb INT,
    drb INT,
    trb INT,
    ast INT,
    stl INT,
    blk INT,
    tov INT,
    pf INT,
    pts INT,
    gmScore DOUBLE,
    plusMinus INT,
    CONSTRAINT game_fk FOREIGN KEY (gameDate)
        REFERENCES seasonSched (gameDate),
    CONSTRAINT player_fk FOREIGN KEY (lName)
        REFERENCES roster (lName) ON DELETE CASCADE
);
