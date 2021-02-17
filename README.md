# microservice

https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation

# Springboot application Run command
mvn clean install -Dskiptests
mvn spring-boot:run

# Error logging Priority
debug
info
error

# Auto increment issue in mysql - use the below query
SELECT @@SQL_MODE, @@GLOBAL.SQL_MODE;
SET @@SQL_MODE = REPLACE(@@SQL_MODE, 'STRICT_TRANS_TABLES', '');

# SQL
select * from customer;
select * from account;  
select * from account_history;

drop table customer;
drop table account;  
drop table account_history;

ALTER TABLE customer MODIFY customer_id bigint NOT NULL AUTO_INCREMENT;
ALTER TABLE account MODIFY account_number bigint NOT NULL AUTO_INCREMENT;
ALTER TABLE account_history MODIFY account_history_id bigint NOT NULL AUTO_INCREMENT;

alter table account drop column account_indicator;
desc account;
desc customer;
desc account_history;


insert into customer
(first_name,last_name,gender,mobile_number,email,door_number,street,district,state,country,pincode,created_by,createdts,updated_by,updatedts)
values
("Manikandan","Kandasamy","male",9940780689,"manikarthi25@gmail.com","2/152","Nandhagopal Road","Coimbatore","Tamilnadu","India",641044,
"admin",current_timestamp(),"admin",current_timestamp());

insert into customer
(first_name,last_name,gender,mobile_number,email,door_number,street,district,state,country,pincode,created_by,createdts,updated_by,updatedts)
values
("Kiruthika","Velmurugan","male",9500402587,"drkiruthimani@gmail.com","2/152","Nandhagopal Road","Coimbatore","Tamilnadu","India",641044,
"admin",current_timestamp(),"admin",current_timestamp());

insert into account_history 
(balance_amount,
credit_amount,
debit_amount,
tranaction_date,
account_number)
values(100,100, 100, current_timestamp(), 1);

insert into account
(customer_id,active_account,account_type,amount)
values(1,"Y","Saving",10000);

insert into account
(customer_id,active_account,account_type,amount)
values(2,"Y","Current()",5000);
