-- All assignment tasks here:
create VIEW productview as
select P.name as productname, PG.name as productgroupname, s.saleId
from product P,
     ProductGroup PG,
     Sale S
where p.productGroupId = pg.productGroupId

create view test (saleid, customername, employeename, amount) as
select s.saleId, c.name as customername, e.name as employeename, sum(sl.amount * pp.price) totalprice
from sale s

         inner join SalesLine sl
                    on s.saleid = sl.saleid
        inner join PriceList pl
    on pl.priceListId = pp.pricelistid
        inner join ProductPrice PP on pl.priceListId = PP.priceListId
         inner join employee e
                    on e.employeeid = s.employeeid
         inner join Customer c
                    on c.customerid = s.customerid


group by c.name, e.name, saleid ,amount;


drop view productview
drop view test