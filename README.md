# EGR327 REST API Project with MySQL

This project is a part of my Software Construction Course. Using REST API, Spring Services, and a SQL database, it is then deployed through the Google Cloud Platform.
Platform services to get, add, update, and delete vehicles from the created vehicle inventory (using the basics functions of CRUD).

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
This class consists of RestControllers which uses HTTP requests to access and return data from the database with VehicleDao.
An example of one of these methods:
```java
@RequestMapping(value = "/getVehicle/{id}", method = RequestMethod.GET)
    public Vehicle getVehicle(@PathVariable("id") int id) throws IOException {
        //query from the database and return vehicle if found
        return vehicleDao.getById(id);
    }
```
This method uses calls and returns vehicleDao's getById method as shown here:
```java
 public Vehicle getById(int id) {
            return entityManager.find(Vehicle.class, id);
        }
```
which uses the entityManager to access and find the Vehicle object in the database by the given id. Since the Vehicle's information is in a JSON format, the entityMangaer makes it into a Vehicle with Vehicle.class.

### MyTasks
This consists of the client-side code which executes HTTP requests at certain times presented as Cron expressions.
Here is an example on how these tasks were implemented:
```java
@Scheduled (cron="*/5 * * * * *")
    public void addNewVehicle() {
        String url = "http://localhost:8080/addVehicle";
        rand = new Random();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        randModel = alphabet.charAt(rand.nextInt(alphabet.length())) + "";
        int randYear = rand.nextInt(31) + 1986;
        int randPrice = rand.nextInt(30001) + 15000;
        restTemplate.postForObject(url, new Vehicle(id++, randModel, randYear, randPrice), Vehicle.class);
    }
```
According to the Cron expression, addNewVehicle will be executed every 5 seconds. Each task uses a HTTP request with the RestTemplate which runs the url of a specified Controller method. Here, addNewVehicle uses the POST request via addVehicle from the Controller class to add a Vehicle object with randomized data. The new object created is translated from Vehicle type to the JSON format.

## MySQL Database
The corresponding MySQL table, named *vehicles*, would look like this if Vehicle of id 0, makeAndModel KIA, year 2014, and retailPrice 123456.0 was entered into the database:

| Id | makeAndModel | year | retailPrice |
| -- | ------------ | ---- | ----------- |
| 0  | KIA          | 2014 | 123456.0    |

The whole database can be viewed through the installation of the MySQL Workbench program, which is run through the local computer's command line. For testing purposes, the SQL table can be modified or accessed through the command line outside the program using typical SQL commands (e.g. select, insert, update, delete).

## Retrieving Data with External Tools
Once the project had been deployed with the Google Cloud Platform, a SQL database was established within the Cloud. There, the user can access or modify the data within the table using REST clients such as Advanced REST Client (ARC), which is avaiable as a Chrome application. There, the user can either use the local host address that is defined within the program or use the url that is given from the Google App Engine. The user can then define the data they want modified through the payload.

**
Overall, this project assignment has shown me how Java, along with the Spring Framework, can be applied to transfer data into a. It has equipped me with the knowledge to collect data from the client and store it into a flexible and easily accessible database system. Utilizing this REST API for any Java program or large-scale services is greatly beneficial as it helps both clients and developers retrieve and update the data needed, also preserving encapsulation of the rest of the data.
