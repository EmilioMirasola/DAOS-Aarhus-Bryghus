--Drop tables -- create tables er længere nede i denne fil:
drop table SalesLine
go

drop table ProductPrice
go

drop table PriceList
go

drop table Product
go

drop table ProductGroup
go

drop table Sale
go

drop table Customer
go

drop table Employee
go


create table ProductGroup
(
    name           varchar(30) not null unique,
    productGroupId int identity (1,1) primary key,
)

create table Product
(
    name           varchar(30) not null,
    stock          int         not null,
    minimumStock   int         not null,
    productGroupId int         not null foreign key references ProductGroup (productGroupId),
    productId      int identity (1,1) primary key,
    constraint checkStock
        check (stock >= 0),
    constraint checkMinimumStock
        check (minimumStock >= 0)
)

create table PriceList
(
    name        varchar(30) not null unique,
    priceListId int identity (1,1) primary key,
)

create table ProductPrice
(
    price           float not null,
    discountPercent float,
    priceListId     int   not null foreign key references PriceList (priceListId),
    productId       int   not null foreign key references Product (productId),
    productPriceId  int identity (1,1) primary key,
    constraint checkDiscount
        check (discountPercent <= 100 and discountPercent > 0),
)

create table Customer
(
    name       varchar(30) not null,
    customerId int identity (1,1) primary key,
)

create table Employee
(
    name       varchar(30) not null,
    employeeId int identity (1,1) primary key,
)

create table Sale
(
    customerId int foreign key references Customer (customerId),
    employeeId int  not null foreign key references Employee (employeeId),
    date       date not null,
    saleId     int identity (1,1) primary key,
)

create table SalesLine
(
    amount         int not null,
    customPrice    float,
    productPriceId int not null foreign key references ProductPrice (productPriceId),
    saleId         int not null foreign key references Sale (saleId),
    salesLineId    int identity (1,1) primary key,
)


