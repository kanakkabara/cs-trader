drop table TRANSACTIONS if exists;

create table TRANSACTIONS(
	TRANSACTION_ID BIGINT(20) NOT NULL AUTO_INCREMENT,
	ORDER_ID BIGINT(20),
	TRADER_ID BIGINT(20),

	ORDER_SIDE VARCHAR(4) NOT NULL,
    ORDER_TYPE VARCHAR(10) NOT NULL,
    PRICE DOUBLE(20) DEFAULT NULL,
    VOLUME INT(20) NOT NULL,
    TRANSACTION_TIMESTAMP TIMESTAMP NOT NULL,
    ORDER_STATUS VARCHAR(20) DEFAULT 'OPEN',

    TRANSACTION_TYPE VARCHAR(20),

	PRIMARY KEY (TRANSACTION_ID),
    FOREIGN KEY (ORDER_ID) REFERENCES ORDERS(ORDER_ID),
    FOREIGN KEY (TRADER_ID) REFERENCES TRADERS(TRADER_ID)
);