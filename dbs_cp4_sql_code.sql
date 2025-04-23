DROP TRIGGER IF EXISTS insert_into_borrowed_table ON currently_rented_cars;
DROP FUNCTION IF EXISTS insert_into_borrowed();
DROP VIEW IF EXISTS currently_rented_cars;

CREATE VIEW currently_rented_cars AS
SELECT
    c.plate_number,
    c.brand,
    c.type,
    o.operation_date,
    cu.name    AS customer_name,
    r.price_per_day
FROM car c
         JOIN operation o  ON c.id = o.car_id
         JOIN rental r     ON o.id = r.operation_id
         JOIN customer cu  ON o.customer_id = cu.id
WHERE c.is_borrowed = TRUE;

CREATE OR REPLACE FUNCTION insert_into_borrowed()
    RETURNS TRIGGER AS
$$
DECLARE
    v_car_id      INT;
    v_customer_id INT;
    v_op_id       INT;
BEGIN
    -- 1) car
    SELECT id INTO v_car_id
    FROM car
    WHERE plate_number = NEW.plate_number;
    IF NOT FOUND THEN
        INSERT INTO car (plate_number, brand, type, mileage, is_borrowed)
        VALUES (NEW.plate_number, NEW.brand, NEW.type, 0, TRUE)
        RETURNING id INTO v_car_id;

        --save id into v_car_id

    ELSE
        UPDATE car
        SET is_borrowed = TRUE
        WHERE id = v_car_id;
    END IF;

    -- 2) customer
    SELECT id INTO v_customer_id
    FROM customer
    WHERE name = NEW.customer_name;
    IF NOT FOUND THEN
        INSERT INTO customer (name, street, city, postal_code)
        VALUES (NEW.customer_name, 'Neznámá', 'Neznámo', '00000')
        RETURNING id INTO v_customer_id;
    END IF;

    -- 3) Operation
    INSERT INTO operation (operation_date, customer_id, car_id)
    VALUES (NEW.operation_date, v_customer_id, v_car_id)
    RETURNING id INTO v_op_id;

    -- 4) Rental
    INSERT INTO rental (operation_id, price_per_day, fine_per_day)
    VALUES (v_op_id, NEW.price_per_day, 500);

    RETURN NULL;
END;
$$
    LANGUAGE plpgsql;

CREATE TRIGGER insert_into_borrowed_table
    INSTEAD OF INSERT ON currently_rented_cars
    FOR EACH ROW
EXECUTE FUNCTION insert_into_borrowed();

INSERT INTO currently_rented_cars
(plate_number, brand, type, operation_date, customer_name, price_per_day)
VALUES
    ('AAA2221', 'Mazda',   'electric', '2025-05-10', 'Matěj Nový', 2000);

SELECT * FROM currently_rented_cars;


--employee table has 32k data, it makes sense to try it here
CREATE INDEX search_by_phone ON employee(phone);

EXPLAIN ANALYZE
SELECT * FROM employee WHERE phone='+420888999000';

DROP INDEX search_by_phone;
