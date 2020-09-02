CREATE TABLE customer
(
    id bigint GENERATED ALWAYS AS IDENTITY,
    name varchar(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE operating_system
(
    id bigint GENERATED ALWAYS AS IDENTITY,
    system_name varchar(100) NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE device
(
    id bigint GENERATED ALWAYS AS IDENTITY,
    type varchar(100) NOT NULL,
    customer_id bigint NOT NULL,
    operating_system_id bigint NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES customer (id),
    CONSTRAINT fk_operating_system_id FOREIGN KEY (operating_system_id) REFERENCES operating_system (id)
);

CREATE TABLE service
(
    id bigint GENERATED ALWAYS AS IDENTITY,
    service_name varchar(100) NOT NULL,
    cost numeric NOT NULL DEFAULT 0.00,
    selectable boolean NOT NULL,
    operating_system_id bigint,
    parent_id bigint,
    PRIMARY KEY (id),
    CONSTRAINT fk_operating_system_id FOREIGN KEY (operating_system_id) REFERENCES operating_system (id),
    CONSTRAINT fk_parent_id FOREIGN KEY (parent_id) REFERENCES service (id)
);

CREATE TABLE customer_service
(
    id bigint GENERATED ALWAYS AS IDENTITY,
    customer_id bigint NOT NULL,
    service_id bigint NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES customer (id),
    CONSTRAINT fk_service_id FOREIGN KEY (service_id) REFERENCES service (id)
);
