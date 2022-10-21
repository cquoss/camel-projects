create table if not exists customer
(   id            identity primary key,
    surname       varchar(200),
    given_name    varchar(200),
    date_of_birth date,
    postal_code   varchar(20)
);