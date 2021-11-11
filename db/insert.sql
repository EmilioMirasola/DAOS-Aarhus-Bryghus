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
       ('Fadøl'),
       ('Beklædning'),
       ('Alkoholfri drikke'),
       ('mad-retter')

insert into Product
values ('Vodka Smirnoff', 30, 15, 1),
       ('Liquor of Aarhus', 100, 50, 1),
       ('Lyng Gin', 50, 25, 1),
       ('Chips', 400, 175, 2),
       ('peanuts', 250, 125, 2),
       ('Kiks', 100, 50, 2),
       ('Klosterbryg', 300, 150, 3),
       ('Classic', 400, 100, 3),
       ('Extra Pilsner', 400, 50, 3),
       ('Trøje', 400, 200, 4),
       ('T-shirt', 200, 100, 4),
       ('cap', 100, 30, 4),
       ('Coca Cola', 250, 150, 5),
       ('Nikoline', 100, 50, 5),
       ('7-UP', 300, 100, 5),
       ('Pasta', 50, 20, 6)


insert into PriceList
values ('Fredagsbar'),
       ('butik')

insert into ProductPrice
values (75, null, 1, 1),
       (100, 10, 1, 2),
       (150, null, 2, 1),
       (200, null, 2, 2),
       (30, null, 1, 4),
       (15, null, 1, 5),
       (20, null, 2, 4),
       (10, null, 2, 5),
       (50, 2, 1, 7),
       (70, null, 1, 8),
       (70, 10, 2, 8),
       (50, null, 1, 9),
       (150, null, 2, 10),
       (75, null, 2, 11),
       (50, null, 2, 12),
       (20, null, 1, 15)

insert into Sale
values (1, 1, '2021-11-09')

insert into SalesLine
values (5, null, 10, 1)

insert into SalesLine
values (3, null, 10, 1)


insert into Sale
values (5, 5, '2021-11-09')

insert into SalesLine
values (5, null, 2, 2)

insert into SalesLine
values (5, null, 1, 2)

insert into SalesLine
values (100, null, 11, 2)










