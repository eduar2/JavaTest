drop table if exists PERSON;
drop table if exists CUSTOMER;
drop table if exists ACCOUNT;
drop table if exists MOVEMENT;

create table PERSON(
  ID                int not null AUTO_INCREMENT primary key,
  NAME              varchar(100) not null,
  GENDER            varchar(100) not null,
  AGE               int,
  IDENTIFICATION    varchar(100) not null,
  ADDRESS           varchar(100) not null,
  PHONE             varchar(100) not null,
  PRIMARY KEY ( ID )
);

CREATE TABLE CLIENT (
  ID               int not null AUTO_INCREMENT primary key,
  PASSWORD         varchar(100) not null,
  STATUS BOOL       NOT null,
  PRIMARY KEY (id)
);

create table ACCOUNT(
  ID                int not null AUTO_INCREMENT primary key,
  ACCOUNT_NUMBER    INT not null,
  TYPE              varchar(100) not null,
  INITIAL_BALANCE   decimal(8,2) NOT NULL,
  STATUS BOOL       NOT null,
  CLIENT_ID         INT not null,
  foreign key (CLIENT_ID) references CLIENT(ID)
);

create table TRANSACTIONS(
  ID                int not null AUTO_INCREMENT primary key,
  TRANSACTION_DATE  DATE NOT NULL,
  ACCOUNT_NUMBER    INT not null,
  TYPE              varchar(100) not null,
  INITIAL_BALANCE   INT NOT NULL,
  AMOUNT            INT not null,
  FINAL_BALANCE INT NOT NULL,
  STATUS BOOL       NOT null,
  ACCOUNT_ID        INT NOT NULL,
  foreign key (ACCOUNT_ID) references ACCOUNT(ID)
);
