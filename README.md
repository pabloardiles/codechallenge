## Campsite API

### Requirements

- JDK 8
- Maven
- MongoDB
- MongoDB Compass
- JMeter

### Get the code

git clone https://github.com/pabloardiles/codechallenge.git

### Assumptions

The campsite has a maximum of 10 slots (parcels) available per day.

### Setup database

Start MongoDB server

```
./mongod --dbpath /PATH/TO/YOUR/DB/DATA --config /PATH/TO/codechallenge/db/mongod.conf
```

Create a Replica Set (this is required to allow mongo clients use transactions)

```
./mongo
```

And then run

```
> rs.initiate()
```
```
> exit
```

Restore database 

```
./mongorestore /PATH/TO/codechallenge/db/dbdump/campsitedb -d campsitedb
```
You can now use MongoDB Compass to check the database content.

### Campsite APIs

I've splitted the API in 2 separate services: campsite-availability and campsite-reservations.

campsite-availability implements the resource /api/availability which let the user search for slots availability for certain date.

campsite-reservations implements reservation management, /api/reserve, /api/cancel and /api/update resources.

Splitted this way makes campsite-availability scale independently for service availability reasons. For instance, it can be included within Container orchestration (ECS, Kubernetes, etc) or can be placed into an auto scaling group to scale out accordingly. Also it might use a read replica database to improve performance.

In order to build the services run:

```
mvn clean install
```

Deploy and run the services:

```
mvn spring-boot:run
```

Notice that campsite-availability is registered in port 8080 and campsite-reservations in port 8081.

You can take a look at their Swagger API docs at http://localhost:8080/swagger-ui.html and http://localhost:8081/swagger-ui.html


### Concurrent reservations

The easiest way to see how overlapping reservations can be handled is with a JMeter test plan.
Open the file /codechallenge/jmeter-test-plan/campsite-test-plan.jmx in JMeter and run the test plan.
You will notice in "View Results Tree" that there will be several requests, some of them succeeded and some of them failed.
Assuming that the date trying to perform the reservation is fully available at the moment (i.e. slots = 10), there should be 10 request with status code 200, and the rest should be 2 types of 409 errors:  
- "No availability for the selected dates": means that the date has run out of slots.
- "One or more dates are not available at the time. Try again later.": means that 2 o more request tried to reserve in the same date. The transaction is rolled back for requests that couldn't be served.

