INSERT INTO ORDERS(SYMBOL, INSTRUCTION, ORDER_TYPE,PRICE, VOLUME, PLACEMENT_TIMESTAMP, TRADER_ID) 
VALUES('AAPL', 'BUY', 'LIMIT', 110.03, 415,  '2015-09-25 23:04:59.078', 1);

INSERT INTO ORDERS(SYMBOL, INSTRUCTION, ORDER_TYPE,PRICE, VOLUME, PLACEMENT_TIMESTAMP, TRADER_ID) 
VALUES('GOOGL', 'BUY', 'LIMIT', 760.76, 30,  '2015-09-25 23:05:03.048', 1);



INSERT INTO ORDERS(SYMBOL, INSTRUCTION, ORDER_TYPE,PRICE, VOLUME, PLACEMENT_TIMESTAMP, TRADER_ID) 
VALUES('GOOGL', 'BUY', 'LIMIT', 760.76, 30,  '2015-10-25 23:05:03.048', 1);

INSERT INTO ORDERS(SYMBOL, INSTRUCTION, ORDER_TYPE,PRICE, VOLUME, PLACEMENT_TIMESTAMP, TRADER_ID) 
VALUES('GOOGL', 'BUY', 'LIMIT', 760.76, 30,  '2015-11-10 23:05:03.048', 1);

INSERT INTO ORDERS(SYMBOL, INSTRUCTION, ORDER_TYPE,PRICE, VOLUME, PLACEMENT_TIMESTAMP, TRADER_ID) 
VALUES('GOOGL', 'BUY', 'LIMIT', 760.76, 30,  '2015-11-11 23:05:03.048', 1);

INSERT INTO ORDERS(SYMBOL, INSTRUCTION, ORDER_TYPE,PRICE, VOLUME, PLACEMENT_TIMESTAMP, TRADER_ID) 
VALUES('GOOGL', 'BUY', 'LIMIT', 760.76, 30,  '2015-11-15 23:05:03.048', 1);

INSERT INTO ORDERS(SYMBOL, INSTRUCTION, ORDER_TYPE,PRICE, VOLUME, PLACEMENT_TIMESTAMP, TRADER_ID, STATUS) 
VALUES('GOOGL', 'BUY', 'LIMIT', 760.76, 30,  '2015-11-28 20:0:03.048', 1, 'FULFILLED');

INSERT INTO ORDERS(SYMBOL, INSTRUCTION, ORDER_TYPE,PRICE, VOLUME, PLACEMENT_TIMESTAMP, TRADER_ID, STATUS) 
VALUES('GOOGL', 'BUY', 'LIMIT', 760.76, 30,  '2016-10-10 10:0:03.048', 1, 'FULFILLED');
VALUES('GOOGL', 'BUY', 'LIMIT', 760.76, 30,  '2015-09-25 23:05:03.048', 1);


SELECT * FROM ORDERS;
