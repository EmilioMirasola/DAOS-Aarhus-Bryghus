create table ProductGroup
(
    name           varchar(30) not null unique,
    productGroupId int identity (1,1) primary key,
)

create table Product
(
    name         varchar(30) not null,
    stock        int         not null,
    minimumStock int         not null,
    productId    int identity (1,1) primary key,
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
    priceListId     int foreign key references PriceList (priceListId),
    productId       int foreign key references Product (productId),
    productPriceId  int identity (1,1) primary key,
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
    employeeId int foreign key references Employee (employeeId),
    saleId     int identity (1,1) primary key,
)

create table SalesLine
(
    amount         int not null,
    customPrice    float,
    productPriceId int foreign key references ProductPrice (productPriceId),
    saleId         int foreign key references Sale (saleId),
    salesLineId    int identity (1,1),
)


