create proc setDiscountOnProductPricesByProductGroup @discount as float,
                                                     @productGroupId as int
as
begin
    update ProductPrice
    set discountPercent = @discount
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