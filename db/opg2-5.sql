--2a
select P.name as produkt, price, discountPercent, PL.name as priceList
from Product P
         join ProductPrice PP on P.productId = PP.productId
         join PriceList PL on PL.priceListId = PP.priceListId

--2b
select SUM(case
               when customPrice is not null then customPrice
               when discountPercent is not null
                   then SL.amount * price * (1 - (discountPercent / 100))
               else SL.amount * price end) as totalPrice
from SalesLine SL
         join ProductPrice PP on SL.productPriceId = PP.productPriceId
where saleId = 1

--2c
select name, SUM(amount) as amount
from Product p
         join ProductPrice PP on p.productId = PP.productId
         join SalesLine SL on PP.productPriceId = SL.productPriceId
         join Sale S on SL.saleId = S.saleId
where MONTH(date) = 11
group by name
having SUM(amount) > 5

--2d
select P.name as product, PG.name as productGroup
from Product P
         join ProductGroup PG on P.productGroupId = PG.productGroupId
except
select P.name as product, PG.name as productGroup
from Product P
         join ProductGroup PG on PG.productGroupId = P.productGroupId
         join ProductPrice PP on P.productId = PP.productId
         join PriceList PL on PL.priceListId = PP.priceListId
where PL.name = 'Fredagsbar'

--2e
select AVG(temp.totalPrice) as average
from (select SUM((case
                      when customPrice is not null then customPrice
                      when discountPercent is not null
                          then SL.amount * price * (1 - (discountPercent / 100))
                      else SL.amount * price end)) as totalPrice
      from Sale S
               join SalesLine SL on S.saleId = SL.saleId
               join ProductPrice PP on SL.productPriceId = PP.productPriceId
      where S.saleId = SL.saleId
      group by S.saleId) as temp

--2f
select PG.name, MAX(price) as maxPrice
from ProductGroup PG
         join Product P on PG.productGroupId = P.productGroupId
         join ProductPrice PP on P.productId = PP.productId
group by PG.name

--2g
select PG.name productGroupName, P.name as productName, MAX(price) as maxPrice
from ProductGroup PG
         join Product P on PG.productGroupId = P.productGroupId
         join ProductPrice PP on P.productId = PP.productId
group by PG.name, P.name

-- 3.a
create view productview as

select PG.name as ProductGroup, P.name as ProductName, count(distinct S.saleId) as sale
from productgroup PG
         inner join Product P on PG.productGroupId = P.productGroupId
         left join ProductPrice PP on P.productId = PP.productId
         left join SalesLine SL on PP.productPriceId = SL.productPriceId
         left join Sale S on SL.saleId = S.saleId

group by PG.name, P.name;

select *
from productview

-- 3.b
create view saleview as
select S.saleId,
       C.name                              as customerName,
       E.name                              as employeeName,
       E.employeeId,
       S.date,
       SUM(case
               when customPrice is not null then customPrice
               when discountPercent is not null
                   then SL.amount * price * (1 - (discountPercent / 100))
               else SL.amount * price end) as totalPrice
from Sale S
         inner join Customer C on S.customerId = C.customerId
         inner join Employee E on S.employeeId = E.employeeId
         inner join SalesLine SL on S.saleId = SL.saleId
         inner join ProductPrice PP on SL.productPriceId = PP.productPriceId
group by S.saleId, C.name, E.name, S.date, E.employeeId;

select *
from saleview

--3.b query
select employeeName, sum(totalPrice) as totalPrice
from saleview S
where employeeId = S.employeeId
group by employeeName
order by employeeName


--4.a

create procedure printPriceList @priceListName as varchar(30)
as
select P.name                       as productName,
       SUM((case

                when discountPercent is not null then PP.price * (1 - (discountPercent / 100))
                else PP.price end)) as price
from PriceList
         inner join ProductPrice PP on PriceList.priceListId = PP.priceListId
         inner join Product P on PP.productId = P.productId
where PriceList.name = @priceListName
group by P.name

    exec printPriceList 'butik'

    --4.b
    create procedure addDiscountToAllProductsInProductGroup @productGroupId as varchar(30),
                                                            @discountPercent as decimal(3, 1)
    as
    begin
        update ProductPrice
        set discountPercent = @discountPercent
        from Product P
                 join ProductPrice PP on P.productId = PP.productId
        where P.productGroupId = @productGroupId
    end
go;

select *
from ProductPrice
execute setDiscountOnProductPricesByProductGroup 10, 3
select *
from ProductPrice

drop proc setDiscountOnProductPricesByProductGroup


-- 4.c

create procedure findEmployeeAndCustomers @name as varchar(30) as
select E.name as name
from Employee E
where E.name like @name + '%'
union
select C.name as name
from Customer C
where C.name like @name + '%'
go;


--5.a
create TRIGGER deleteProductGroup
    on Product
    after delete as
    Declare
        @count as int
    set @count = (select count(P.name)
                  from Product P
                           inner join ProductGroup PG
                                      on P.productGroupId = (select productGroupId from deleted))
    print @count
    if (@count = 0)
        begin

            delete
            from ProductGroup
            where ProductGroup.productGroupId = (select productGroupId from deleted)

        end

    -- Test for triggeren "deleteProductGroup"
    delete product
    where product.name = 'Fuglsang'
go;


--5.b
create trigger reduceStock
    on SalesLine
    after insert as
    Declare
        @amount as         int,
        @productPriceId as int
    set @amount = (select amount
                   from inserted)
    set @productPriceId = (select productPriceId
                           from inserted)
    update Product
    set stock = stock - @amount
    where Product.productId = (select productId
                               from ProductPrice
                               where productPriceId = @productPriceId)
go;


--6.b
create procedure getTotalSaleInDKKForASpeceficProduct @productName as varchar(30),
                                                      @date as date
as

select SUM((case
                when customPrice is not null then customPrice
                when discountPercent is not null
                    then SL.amount * price * (1 - (discountPercent / 100))
                else SL.amount * price end)) as totalPrice
from Sale S
         join SalesLine SL on S.saleId = SL.saleId and S.date = @date
         join ProductPrice PP on SL.productPriceId = PP.productPriceId
         join Product P on PP.productId = P.productId
where P.name = @productName


    exec getTotalSaleInDKKForASpeceficProduct 'Classic', '2021-11-09'












