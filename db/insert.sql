-- noinspection SqlUnicodeStringLiteralForFile

-- noinspection SpellCheckingInspectionForFile

insert into Customer
values ('Anders Milken'),
       ('Mikkel Jensen'),
       ('Bohne Petersen'),
       ('Signe jensen'),
       ('Pauline Jensen'),
       ('Bruce Banner'),
       ('Joe Andersen'),
       ('Zacarias Sirens'),
       ('Morten Hansen'),
       ('Kasper Jensen'),
       ('Line Andersen'),
       ('Soren Petersen')

insert into Employee
values ('Lars Binder Jørgensen'),
       ('Lars Larsen'),
       ('Søren Petersen'),
       ('Mihaela Hudson'),
       ('Suzana Kerstin'),
       ('Júlia Petar'),
       ('Webster Natali'),
       ('Diana Torsten'),
       ('Kamilla Herman'),
       ('Herman Bang'),
       ('Lars Krøyer'),
       ('Allan Jørgensen')


insert into ProductGroup
values ('Spiritus'),
       ('Snacks'),
       ('Øl'),
       ('Fadøl'),
       ('Beklædning'),
       ('Alkoholfri drikke')

insert into Product
values ('Vodka Smirnoff', 30, 15, 1),
       ('Classic', 400, 100, 3),
       ('Pilsner', 400, 100, 3),
       ('Coca Cola', 250, 150, 6),
       ('Trøje', 400, 200, 5),
       ('Chips', 400, 175, 2),
       ('Fuglsang', 300, 100, 4)


insert into PriceList
values ('Fredagsbar'),
       ('butik')

insert into ProductPrice
values (70, null, 1, 2)

insert into ProductPrice
values (70, 10, 2, 2)

insert into Sale
values (1, 1, '2021-11-09')

insert into SalesLine
values (5, null, 2, 1)

insert into SalesLine
values (3, null, 2, 1)

insert into Sale
values (5, 5, '2021-11-09')

insert into SalesLine
values (5, null, 2, 2)

insert into SalesLine
values (5, null, 1, 2)

insert into SalesLine
values (300, null, 2, 2)
