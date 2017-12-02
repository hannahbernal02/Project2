# Project2
EGR327 REST API Project with MySQL
***
This project is a part of my Software Construction Course. Using REST API, Spring Services, and a SQL database, it is then deployed through the Google Cloud Platform.
Platform services to get, add, update, and delete vehicles from the created vehicle inventory.

The project relies on the DAO (Data Access Object) which allows the Java code of the project interact with the SQL database with persist and merge methods for corresponding HTTP requests.

***

## Java Classes

### DemoApplication

### Controller

### Vehicle

### VehicleDao

### MyTasks
This consists of the client-side code which executes HTTP requests at certain times presented as Cron expressions.
Here is an example on how I implemented these tasks:

Using the RestTemplate, 

## MySQL Database
The corresponding MySQL table, named *vehicles*, would look like this:

| Id | makeAndModel | year | retailPrice |
| -- | ------------ | ---- | ----------- |
| 0  | KIA          | 2014 | 123456.0    |
