# EGR327 REST API Project with MySQL

This project is a part of my Software Construction Course. Using REST API, Spring Services, and a SQL database, it is then deployed through the Google Cloud Platform.
Platform services to get, add, update, and delete vehicles from the created vehicle inventory.

The project relies on the DAO (Data Access Object) which allows the Java code of the project interact with the SQL database with persist and merge methods for corresponding HTTP requests.

***

## Java Classes
Using IntelliJ and Spring, the program connects with Web Services, JPA, JDBC, and MySQL. The classes within the program interact with these elements. The following classes include the client- and server- side code that serve to use the HTTP requests to utilize Vehicle data and execute it.

### DemoApplication
This class simply starts the application, enabling the scheduling component with ```@EnableScheduling```.

### Vehicle
This is the class where the object (called Vehicle) is defined with its information.
Its information is defined in four fields:
```java
    private int id;
    private String makeModel;
    private int year;
    private double retailPrice;
```
Id will be used in the MySQL database to label each created Vehicle object. It is defined to be auto incremented with this code:
```java
@Entity
@Table(name = "vehicles")
public class Vehicle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
```
which is used in the *VehicleDao* class.
The table name *vehicles* is also defined with @Table to ensure the program is connected with the same MySQL database table that will be created within the MySQL program.

### VehicleDao
The Vehicle Dao is an object that allows access for the request methods described in the Controller class to the MySQL database.
The class used the ORM (Object Relational-Mapping) to represent the data from the database with Vehicle objects.
Here's an example in how these methods work:
```java
public List<Vehicle> getVehicleList() {

//            List<Vehicle> retrievedList = entityManager.createQuery( "from Vehicle", Vehicle.class).getResultList();
//            List<Vehicle> latestVehicles = new ArrayList<>();
//            int limit = 9;
//            if(retrievedList.size() - 1 < limit) {
//               limit = retrievedList.size() - 1;
//            }
//            for (int i = 0; i <= limit; i++) {
//                latestVehicles.add(retrievedList.get(retrievedList.size()-1-i));
//            }
            List <Vehicle> retrievedList = entityManager.createNativeQuery("Select * from vehicles ORDER BY id DESC LIMIT 10",
                    Vehicle.class).getResultList();
            return retrievedList;
        }
```
There are two alternative ways to access the database:
1. Create a list that retrieves every Vehicle object in the database and then loop to retrieve it to another list that will be returned until the defined limit.
2. Create a native query that uses the SQL command *select * from vehicles order desc limit 10* to get at most ten Vehicles from data

### Controller
This class consists of RestControllers which uses HTTP requests to access and return data from the database
An example of one of these methods:
```java
@RequestMapping(value = "/getVehicle/{id}", method = RequestMethod.GET)
    public Vehicle getVehicle(@PathVariable("id") int id) throws IOException {
        //query from the database and return vehicle if found
        return vehicleDao.getById(id);
    }
```


### MyTasks
This consists of the client-side code which executes HTTP requests at certain times presented as Cron expressions.
Here is an example on how I implemented these tasks:

Using the RestTemplate, 

## MySQL Database
The corresponding MySQL table, named *vehicles*, would look like this:

| Id | makeAndModel | year | retailPrice |
| -- | ------------ | ---- | ----------- |
| 0  | KIA          | 2014 | 123456.0    |
