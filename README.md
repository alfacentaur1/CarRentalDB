# CarRentalDB
SQL table creation
CREATE TABLE car (
    id SERIAL PRIMARY KEY,
    plate_number CHAR(7) UNIQUE NOT NULL,
    type TEXT NOT NULL,
    mileage INT CHECK (mileage > 0),
    is_borrowed BOOLEAN DEFAULT FALSE,
    brand TEXT NOT NULL
);

CREATE TABLE service (
    id SERIAL PRIMARY KEY,
    price INT CHECK (price > 0) NOT NULL,
    service_date DATE CHECK (service_date > CURRENT_DATE) NOT NULL,
    type TEXT NOT NULL
);

CREATE TABLE requires (
    id SERIAL PRIMARY KEY,
    service_id INT REFERENCES service(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    car_id INT REFERENCES car(id) ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE service_record (
    id SERIAL PRIMARY KEY,
    description TEXT,
    car_id INT REFERENCES car(id) ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE employee (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    phone VARCHAR(13) NOT NULL CHECK (phone ~ '^\+420[0-9]{9}$'),
    role TEXT NOT NULL
);

CREATE TABLE birth_number (
    id SERIAL PRIMARY KEY,
    employee_id INT REFERENCES employee(id),
    birth_number VARCHAR(11) NOT NULL CHECK (birth_number ~ '^[0-9]{6}/[0-9]{4}$')
);

CREATE TABLE is_responsible_for (
    employee_id INT REFERENCES employee(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    service_id INT REFERENCES service(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    PRIMARY KEY (employee_id, service_id)
);

CREATE TABLE is_superior_to (
    employee_id INT REFERENCES employee(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    supervisor_id INT REFERENCES employee(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    PRIMARY KEY (employee_id)
);

CREATE TABLE customer (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    street TEXT NOT NULL,
    city TEXT NOT NULL,
    postal_code VARCHAR(5) CHECK (postal_code ~ '^[0-9]{5}$')
);

CREATE TABLE operation (
    id SERIAL PRIMARY KEY,
    operation_date DATE NOT NULL,
    customer_id INT REFERENCES customer(id),
    car_id INT  REFERENCES car(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    employee_id INT REFERENCES employee(id) ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE rental (
    id SERIAL PRIMARY KEY,
    rental_date DATE NOT NULL,
    operation_id INT REFERENCES operation(id),
    fine_per_day INT NOT NULL,
    price_per_day INT NOT NULL
);

CREATE TABLE return (
    id SERIAL PRIMARY KEY,
    rental_id INT REFERENCES rental(id),
    status TEXT NOT NULL,
    fine_per_day INT,
    price_per_day INT NOT NULL
);

CREATE TABLE payment (
    id SERIAL PRIMARY KEY,
    payment_date DATE NOT NULL,
    card DECIMAL(10, 2) NOT NULL,
    cash DECIMAL(10, 2) NOT NULL,
    customer_id INT REFERENCES customer(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    rental_id INT REFERENCES rental(id) ON UPDATE CASCADE ON DELETE RESTRICT
);

INSERT INTO car(
                        
)





