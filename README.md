# ACME

* How to deploy: The ACME application is implemented using SpringBoot framework and in memory H2 database,
To run project please follow following steps:

1. Go to project directory (cd PROJECT_DIRECTORY)
2. Run command mvn spring-boot:run

* Prerequisites:
1. Java 8
2. Apache Maven 3+

REST APIs:

1. To Push Truck Geo Location:
    POST Request : HOST:PORT/logistic/gpsListener/vehicle
    BODY : { vehicleId : ID, latitude : LATITUDE, longitude : LONGITUDE }
    
2. To generate shipment route from sorce and destination:
    GET Request : HOST:PORT/logistic/shipment/new?source=&destination=
