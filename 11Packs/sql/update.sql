
drop table users;
CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(120) NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  `unique_number` varchar(45) NOT NULL,
  `email` varchar(80) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `middlename` varchar(45) DEFAULT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `referral_code` varchar(45) NOT NULL,
  PRIMARY KEY (`unique_number`,`email`,`username`,`referral_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE `referred_user` (
  `referral_code` varchar(45) NOT NULL,
  `unique_number` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  UNIQUE KEY `UC_REFERRED_USER` (`referral_code`,`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


create table player_score(
	player_id varchar(45) NOT NULL,
	match_id varchar(45) NOT NULL,
    run int(4) DEFAULT 0,
    ball int(4) DEFAULT 0,
    sixes int(2) DEFAULT 0,
    fours int(2) DEFAULT 0,
	sRate DECIMAL DEFAULT 0,
	overs DECIMAL DEFAULT 0,
    runsgiven int(4) DEFAULT 0,
    wicket int(2) DEFAULT 0,
    maiden int(2) DEFAULT 0,
    economy DECIMAL DEFAULT 0,
    catches int(2) DEFAULT 0,
    runout int(2) DEFAULT 0,
    stumped int(2) DEFAULT 0,
    points DECIMAL DEFAULT 0,
    UNIQUE KEY `UC_PLAYER_SCORE` (`player_id`,`match_id`)
);


drop table fielding;
CREATE TABLE `fielding` (
  `player_id` varchar(45) NOT NULL,
  `match_id` varchar(45) NOT NULL,
  `catch` int(3) DEFAULT 0,
  `stumped` int(3) DEFAULT 0,
  `run_out` int(3) DEFAULT 0,
  UNIQUE KEY `UC_FIELDING` (`player_id`,`match_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


drop table bowling;
CREATE TABLE `bowling` (
  `player_id` varchar(45) NOT NULL,
  `match_id` varchar(45) NOT NULL,
  `over` int(3) DEFAULT 0,
  `maiden` int(3) DEFAULT 0,
  `run` int(3) DEFAULT 0,
  `wicket` int(3) DEFAULT 0,
  `economy` double DEFAULT 0,
  UNIQUE KEY `UC_BOWLING` (`player_id`,`match_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


drop table batting;
CREATE TABLE `batting` (
  `player_id` varchar(45) NOT NULL,
  `match_id` varchar(45) NOT NULL,
  `run` int(3) DEFAULT 0,
  `ball` int(3) DEFAULT 0,
  `fours` int(3) DEFAULT 0,
  `sixes` int(3) DEFAULT 0,
  `sRate` decimal(10,0) DEFAULT 0,
  UNIQUE KEY `UC_BATTING` (`player_id`,`match_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


create table point_system(
	id varchar(45) NOT NULL,
    value DECIMAL DEFAULT 0,
    caption varchar(45) NOT NULL,
    type VARCHAR(20) NOT NULL,
    point_type VARCHAR(20) 
);

insert into point_system(id, value, caption, type, point_type) values ('RUN', 1, 'For every run', 'T20', 'BATTING');
insert into point_system(id, value, caption, type, point_type) values ('SIX', 3, 'Six Bonus', 'T20', 'BATTING');
insert into point_system(id, value, caption, type, point_type) values ('FOUR', 2, 'Boundary Bonus', 'T20', 'BATTING');
insert into point_system(id, value, caption, type, point_type) values ('HALFCENTURY', 10, 'Half-century Bonus', 'T20', 'BATTING');
insert into point_system(id, value, caption, type, point_type) values ('CENTURY', 20, 'Century Bonus', 'T20', 'BATTING');
insert into point_system(id, value, caption, type, point_type) values ('DUCK', -3, 'Dismissal for a duck', 'T20', 'BATTING');


insert into point_system(id, value, caption, type, point_type) values ('WICKET', 20, 'Wicket', 'T20', 'BOWLING');
insert into point_system(id, value, caption, type, point_type) values ('FOURWICKETHAUL', 5, '4 wicket haul Bonus', 'T20', 'BOWLING');
insert into point_system(id, value, caption, type, point_type) values ('FIVEWICKETHAUL', 10, '5 wicket haul Bonus', 'T20', 'BOWLING');
insert into point_system(id, value, caption, type, point_type) values ('MAIDEN', 10, 'Maiden over', 'T20', 'BOWLING');


insert into point_system(id, value, caption, type, point_type) values ('CATCH', 10, 'Catch', 'T20', 'FIELDING');
insert into point_system(id, value, caption, type, point_type) values ('RUNOUT', 5, 'Run-out', 'T20', 'FIELDING');
insert into point_system(id, value, caption, type, point_type) values ('STUMP', 10, 'Half-century Bonus', 'T20', 'FIELDING');


insert into point_system(id, value, caption, type, point_type) values ('CAPTAIN', 2, 'Captain', 'T20', 'OTHERS');
insert into point_system(id, value, caption, type, point_type) values ('VICECAPTAIN', 1.5, 'Vice-Captain', 'T20', 'OTHERS');
insert into point_system(id, value, caption, type, point_type) values ('INELEVEN', 4, 'In playing 11', 'T20', 'OTHERS');


insert into point_system(id, value, caption, type, point_type) values ('SEVENTYTONINTY', -3, 'Strike rate between 70-89.9', 'T20', 'STRIKE RATE');
insert into point_system(id, value, caption, type, point_type) values ('FIFTYTOSEVENTY', -5, 'Strike rate between 50-69.9', 'T20', 'STRIKE RATE');
insert into point_system(id, value, caption, type, point_type) values ('BELOWFIFTY', -10, 'Strike rate below 50', 'T20', 'STRIKE RATE');
insert into point_system(id, value, caption, type, point_type) values ('ONETWENTYTOFIFTY', 5, 'Strike rate between 120-150', 'T20', 'STRIKE RATE');
insert into point_system(id, value, caption, type, point_type) values ('ABOVEONEFIFTY', 10, 'Strike rate above 150', 'T20', 'STRIKE RATE');


insert into point_system(id, value, caption, type, point_type) values ('BELOWFOURRUN', 10, 'Below 4 runs per over', 'T20', 'ECONOMY RATE');
insert into point_system(id, value, caption, type, point_type) values ('FOURTOFIVE', 5, 'Between 4-4.99 runs per over', 'T20', 'ECONOMY RATE');
insert into point_system(id, value, caption, type, point_type) values ('FIVETOSIX', 2, 'Between 5-6 runs per over', 'T20', 'ECONOMY RATE');
insert into point_system(id, value, caption, type, point_type) values ('NINETOTEN', -2, 'Between 9-10 runs per over', 'T20', 'ECONOMY RATE');
insert into point_system(id, value, caption, type, point_type) values ('TENTOELEVEN', -5, 'Between 10.1-11 runs per over', 'T20', 'ECONOMY RATE');
insert into point_system(id, value, caption, type, point_type) values ('ABOVEELEVEN', -10, 'Above 11 runs per over', 'T20', 'ECONOMY RATE');



create table player_points(
	player_id varchar(45) NOT NULL,
    Match_id varchar(45) NOT NULL,
    points DECIMAL DEFAULT 0,
    UNIQUE KEY `UC_player_points` (`player_id`,`match_id`)
);
 
