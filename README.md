# CarRentalDB
SQL table creation
CREATE TABLE car(
    id_plate_number char(7),
    type text,
    mileage int DEFAULT 0,
    is_borrowed boolean DEFAULT TRUE,
    brand text,
    PRIMARY KEY (id_plate_number)
)
