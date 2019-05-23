USE CelticsDB;

-- INSERT INTO PLAYER

select * from team;
select * from roster;
select * from seasonSched;
select * from playerGameStats;

delete from roster where lName = 'Irving';