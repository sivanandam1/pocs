
cassandra faculty: mukesh.shukla@gmail.com 9880061584

#########################################################################################################


nodetool status

nodetool flush siva_test

nodetool tablestats siva_test.employees

nodetool compact

nodetool describering siva_test


#########################################################################################################


cqlsh 100.97.79.52

create keyspace siva_test with replication = {'class':'NetworkTopologyStrategy','BLR':2, 'Chennai':2};

describe keyspaces;

use siva_test;

create table employees (id int, name varchar, dept int, city varchar, primary key(dept,city,name));


insert into employees (dept,city,name) values(100,'Chennai','Mukesh');
insert into employees (dept,city,name,id) values(100,'Chennai','Siva',10000);
insert into employees (dept,city,name,id) values(100,'BLR','Jai',11000);
insert into employees (dept,city,name,id) values(200,'BLR','Dinesh',12000);


select name from employees where city='BLR';
select name from employees where city='BLR' ALLOW FILTERING;
create index cityindex on employees(city);
select * from employees where dept=100 order by city,name;


cqlsh:mukesh_playlist> create materialized view emp_by_id AS select id,name,dept,city from employees where id is not null AND dept is not null an
d city is not null and name is not null primary key (id,dept,city,name);



#########################################################################################################


CREATE [OR REPLACE] AGGREGATE [IF NOT EXISTS]
aggregateName(type1, type2, …)
SFUNC accumulatorFunction
STYPE stateType
[FINALFUNC finalFunction]
INITCOND initCond;

CREATE TABLE sales_items(shop_id text, day int, category text, count counter, PRIMARY KEY((shop_id),day,category));
 
UPDATE sales_items SET count=count+1300 WHERE shop_id='BestDeals' AND day=20151221 AND category='Books';
UPDATE sales_items SET count=count+5000 WHERE shop_id='BestDeals' AND day=20151221 AND category='Movies';
UPDATE sales_items SET count=count+3493 WHERE shop_id='BestDeals' AND day=20151221 AND category='Games';
 
UPDATE sales_items SET count=count+1500 WHERE shop_id='BestDeals' AND day=20151222 AND category='Books';
UPDATE sales_items SET count=count+7000 WHERE shop_id='BestDeals' AND day=20151222 AND category='Movies';
UPDATE sales_items SET count=count+9000 WHERE shop_id='BestDeals' AND day=20151222 AND category='Games';
 
UPDATE sales_items SET count=count+1200 WHERE shop_id='BestDeals' AND day=20151223 AND category='Books';
UPDATE sales_items SET count=count+11000 WHERE shop_id='BestDeals' AND day=20151223 AND category='Movies';
UPDATE sales_items SET count=count+13000 WHERE shop_id='BestDeals' AND day=20151223 AND category='Games';
 
UPDATE sales_items SET count=count+800 WHERE shop_id='BestDeals' AND day=20151224 AND category='Books';
UPDATE sales_items SET count=count+3000 WHERE shop_id='BestDeals' AND day=20151224 AND category='Movies';
UPDATE sales_items SET count=count+1000 WHERE shop_id='BestDeals' AND day=20151224 AND category='Games';

SELECT * FROM sales_items;

Now we want to have an aggregate view of sales count over a period of time for each category of item. So let’s create an UDA for this!

CREATE OR REPLACE FUNCTION cumulateCounter(state map<text,bigint>, category text, count counter)
RETURNS NULL ON NULL INPUT
RETURNS map<text,bigint>
LANGUAGE java
AS '
if(state.containsKey(category)) {
  state.put(category, (long)state.get(category) + count); 
} else {
  state.put(category, count);
}
return state;
'; 	

CREATE OR REPLACE AGGREGATE groupCountByCategory(text, counter)
SFUNC cumulateCounter
STYPE map<text,bigint>
INITCOND {};

SELECT groupCountByCategory(category,count) 
FROM sales_items 
WHERE shop_id='BestDeals'
AND day>=20151221 AND day<=20151224;

#######################################################################################################################