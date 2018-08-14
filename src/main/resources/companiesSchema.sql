drop table COMPANIES if exists;

CREATE TABLE COMPANIES(
	COMPANY_ID BIGINT(20) NOT NULL AUTO_INCREMENT,
	SECTOR_ID BIGINT(20),
	NAME VARCHAR NOT NULL,
	TICKER_SYMBOL VARCHAR NOT NULL,
	PRIMARY KEY (COMPANY_ID),
	FOREIGN KEY (SECTOR_ID) REFERENCES SECTORS(SECTOR_ID)
);