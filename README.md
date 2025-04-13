# CarRentalDB
SQL table creation
CREATE TABLE car(
    id_plate_number char(7),
    type text NOT NULL,
    mileage int CHECK ( mileage > 0 ),
    is_borrowed boolean DEFAULT TRUE,
    brand text NOT NULL,
    CONSTRAINT carPK PRIMARY KEY (id_plate_number)
);

CREATE TABLE service_record(
    service_record text,
    plate_number char(7) NOT NULL REFERENCES car ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT service_recordPK PRIMARY KEY (service_record)
);

CREATE TABLE requires(
    id_requires SERIAL PRIMARY KEY,
    requires_service_number int REFERENCES service ON UPDATE CASCADE ON DELETE RESTRICT,
    plate_number char(7) NOT NULL REFERENCES car ON UPDATE CASCADE ON DELETE RESTRICT

);

CREATE TABLE service(
    id_service_number int ,
    price int CHECK ( price > 0 ) NOT NULL,
    date date check ( date > CURRENT_DATE) NOT NULL,
    type text NOT NULL,
    CONSTRAINT servicePK PRIMARY KEY (id_service_number)
);

CREATE TABLE employee(
    employee_id int NOT NULL,
    name text NOT NULL,
    phone VARCHAR(13) NOT NULL CHECK (phone ~ '^\+420[0-9]{9}$'),
    role text NOT NULL,
    CONSTRAINT employeePK PRIMARY KEY (employee_id)
);

CREATE TABLE birth_number(
    id_birth_number int,
    birth_number VARCHAR(11) NOT NULL CHECK (birth_number ~ '^[0-9]{6}/[0-9]{4}$'),
    CONSTRAINT birth_numberPK PRIMARY KEY (id_birth_number)
);

CREATE TABLE is_responsible_for(
    employee int REFERENCES employee,
    service_number int REFERENCES service,
    CONSTRAINT is_responsible_forPK PRIMARY KEY(employee,service_number)
);

CREATE TABLE is_superior_to(
    employee int REFERENCES employee ON UPDATE CASCADE ON DELETE RESTRICT ,
    supervisor int REFERENCES employee ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT is_superior_toPK PRIMARY KEY (employee)
);

CREATE TABLE operation(
    operation_date date NOT NULL,
    customer_name text NOT NULL,
    customer_street text NOT NULL,
    customer_city text NOT NULL,
    customer_postal_code varchar(5) CHECK (customer_postal_code ~ '^[0-9]{5}$'),
    car char(7) NOT NULL UNIQUE REFERENCES car,
    employee int NOT NULL UNIQUE REFERENCES employee,
    CONSTRAINT operationFK FOREIGN KEY (operation_date,customer_name,customer_street,customer_city,customer_postal_code)
        REFERENCES customer,
    CONSTRAINT operationPK PRIMARY KEY (operation_date,customer_name,customer_street,customer_city,customer_postal_code)

);

CREATE TABLE rental(
    rental_date date NOT NULL,
    customer_name text NOT NULL,
    customer_street text NOT NULL,
    customer_city text NOT NULL,
    customer_postal_code varchar(5) CHECK (customer_postal_code ~ '^[0-9]{5}$'),
    fine_per_day int NOT NULL,
    price_per_day int NOT NULL,
    CONSTRAINT rentalFK FOREIGN KEY (rental_date,customer_name,customer_street,customer_city,customer_postal_code)
                   REFERENCES operation,
    CONSTRAINT rentalPK PRIMARY KEY (rental_date,customer_name,customer_street,customer_city,customer_postal_code)
);

CREATE TABLE return (
    status text NOT NULL,
    rental_date date NOT NULL,
    customer_name text NOT NULL,
    customer_street text NOT NULL,
    customer_city text NOT NULL,
    customer_postal_code varchar(5) CHECK (customer_postal_code ~ '^[0-9]{5}$'),
    fine_per_day int,
    price_per_day int NOT NULL,
    CONSTRAINT rentalFK FOREIGN KEY (rental_date,customer_name,customer_street,customer_city,customer_postal_code)
        REFERENCES operation,
    CONSTRAINT rentalPK PRIMARY KEY (rental_date,customer_name,customer_street,customer_city,customer_postal_code)

);

CREATE TABLE customer(
    name text NOT NULL,
    street text NOT NULL,
    city text NOT NULL,
    postal_code varchar(5) CHECK (postal_code ~ '^[0-9]{5}$'),
    CONSTRAINT customerPK PRIMARY KEY (name,street,city, postal_code)
);

CREATE TABLE payment (
    payment_id serial PRIMARY KEY,
    payment_date date NOT NULL,
    card decimal(10, 2) NOT NULL,
    cash decimal(10, 2) NOT NULL,
    customer_name text NOT NULL,
    customer_street text NOT NULL,
    customer_city text NOT NULL,
    customer_postal_code VARCHAR(5) CHECK (customer_postal_code ~ '^[0-9]{5}$'),
    rental_date date NOT NULL,
    CONSTRAINT payment_customerFK FOREIGN KEY (customer_name, customer_street, customer_city, customer_postal_code)
        REFERENCES customer(name, street, city, postal_code)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT payment_rentalFK FOREIGN KEY (rental_date, customer_name, customer_street, customer_city, customer_postal_code)
        REFERENCES rental(rental_date, customer_name, customer_street, customer_city, customer_postal_code)
        ON UPDATE CASCADE ON DELETE RESTRICT
);



