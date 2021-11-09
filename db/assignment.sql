-- All assignment tasks here:

-- 3.a, endnu ikke helt færdig
create view productview as

select PG.name as Produktgruppe, P.name as Produktnavn, count(distinct S.saleId) as salg
from productgroup PG
         inner join Product P on PG.productGroupId = P.productGroupId
         inner join ProductPrice PP on P.productId = PP.productId
         inner join SalesLine SL on PP.productPriceId = SL.productPriceId
         inner join Sale S on SL.saleId = S.saleId

group by PG.name, P.name;

-- 3.b
create view saleview as
select S.saleId                            as salgsId,
       C.name                              as kundenavn,
       E.name                              as salgsmedarbejder,
       S.date                              as salgsdato,
       SUM(case
               when customPrice is not null then customPrice
               when discountPercent is not null
                   then SL.amount * price * (1 - (discountPercent / 100))
               else SL.amount * price end) as samlet_pris
from Sale S
         inner join Customer C on S.customerId = C.customerId
         inner join Employee E on S.employeeId = E.employeeId
         inner join SalesLine SL on S.saleId = SL.saleId
         inner join ProductPrice PP on SL.productPriceId = PP.productPriceId
group by S.saleId, C.name, E.name, S.date;

select * from saleview

--3.b query
select salgsmedarbejder, sum(samlet_pris) as salg_i_dkk
from saleview
where salgsmedarbejder = salgsmedarbejder
group by salgsmedarbejder
order by salgsmedarbejder


-- drop view
drop view saleview










