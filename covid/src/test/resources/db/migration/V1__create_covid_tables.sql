CREATE TABLE citizens(
	citizen_id bigint NOT NULL AUTO_INCREMENT,
	citizen_name varchar(200) NOT NULL,
	zip char(4) NOT NULL,
	age bigint NOT NULL,
	email varchar(200),
	taj varchar(10),
	number_of_vaccinations bigint,
	last_vaccination datetime,
	PRIMARY KEY (citizen_id)
);

CREATE TABLE vaccinations(
	vaccination_id bigint NOT NULL AUTO_INCREMENT,
	citizen_id bigint NOT NULL,
	vaccination_date datetime NOT NULL,
	`status` varchar(10),
	note varchar(200),
	vaccination_type varchar(20),
	PRIMARY KEY (vaccination_id),
	FOREIGN KEY (citizen_id) REFERENCES citizens (citizen_id)
);

CREATE TABLE cities(
	city_id bigint NOT NULL AUTO_INCREMENT,
	zip varchar(4) NOT NULL,
	city varchar(40) NOT NULL,
	city_part varchar(40),
	PRIMARY KEY (city_id)
);