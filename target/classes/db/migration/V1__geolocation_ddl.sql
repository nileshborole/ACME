


CREATE TABLE CMT_COMMON_LOV(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR2(255) NOT NULL,
    VALUE VARCHAR2(500) NOT NULL,
    TYPE VARCHAR2(255) NOT NULL
);

CREATE TABLE TXN_LOCATION(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    LATITUDE DECIMAL(9, 6) NOT NULL,
    LONGITUDE DECIMAL(9, 6) NOT NULL,
    POINT_ID BIGINT
);

CREATE TABLE CMT_LOCATION_DETAIL(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR2(255) NOT NULL,
    DESCRIPTION VARCHAR2(255) NOT NULL,
    LOCATION_ID BIGINT NOT NULL,
    CREATED_ON TIMESTAMP NOT NULL,
    CREATED_BY BIGINT NOT NULL,
    UPDATED_ON TIMESTAMP NOT NULL,
    UPDATED_BY BIGINT NOT NULL,
    STATUS BIGINT NOT NULL,
    FOREIGN KEY ( LOCATION_ID ) REFERENCES TXN_LOCATION(ID) ON DELETE RESTRICT,
    FOREIGN KEY (STATUS) REFERENCES CMT_COMMON_LOV(ID)
);


CREATE TABLE TXN_ROUTE(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR2(255) NOT NULL,
    TYPE_ID BIGINT NOT NULL
);

CREATE TABLE TXN_ROUTE_DETAIL(
    ROUTE_ID BIGINT NOT NULL,
    LOCATION_ID BIGINT NOT NULL,
    CREATED_ON TIMESTAMP NOT NULL,
    FOREIGN KEY (ROUTE_ID) REFERENCES TXN_ROUTE(ID),
    FOREIGN KEY (LOCATION_ID) REFERENCES TXN_LOCATION(ID)
);

CREATE TABLE TXN_VEHICLE_ROUTE(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    VEHICLE_ID BIGINT NOT NULL,
    START_TIME TIMESTAMP,
    END_TIME TIMESTAMP,
    ROUTE_ID BIGINT,
    CREATED_ON TIMESTAMP NOT NULL,
    CREATED_BY BIGINT NOT NULL,
    UPDATED_ON TIMESTAMP NOT NULL,
    UPDATED_BY BIGINT NOT NULL,
    STATUS BIGINT NOT NULL,
    FOREIGN KEY (ROUTE_ID) REFERENCES TXN_ROUTE(ID),
    FOREIGN KEY (STATUS) REFERENCES CMT_COMMON_LOV(ID)
);

CREATE TABLE TXN_SHIPMENT(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    START_LOCATION_ID BIGINT NOT NULL,
    END_LOCATION_ID BIGINT NOT NULL,
    ROUTE_ID BIGINT,
    START_TIME TIMESTAMP,
    END_TIME TIMESTAMP,
    VEHICLE_ROUTE_ID BIGINT,
    CREATED_ON TIMESTAMP NOT NULL,
    CREATED_BY BIGINT NOT NULL,
    UPDATED_ON TIMESTAMP NOT NULL,
    UPDATED_BY BIGINT NOT NULL,
    STATUS BIGINT NOT NULL,
    FOREIGN KEY (ROUTE_ID) REFERENCES TXN_ROUTE(ID),
    FOREIGN KEY (START_LOCATION_ID) REFERENCES CMT_LOCATION_DETAIL (ID),
    FOREIGN KEY (END_LOCATION_ID) REFERENCES CMT_LOCATION_DETAIL (ID),
    FOREIGN KEY (STATUS) REFERENCES CMT_COMMON_LOV(ID)
);


CREATE TABLE TXN_SHIPMENT_DEVIATION_DETAIL(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    SHIPMENT_ID BIGINT NOT NULL,
    VEHICLE_ID BIGINT NOT NULL,
    DEVIATION_POINT_ID NOT NULL,
    DEVIATION_FROM_POINT_ID NOT NULL,
    DEVIATION_TIME TIMESTAMP,
    STATUS BIGINT NOT NULL
);
