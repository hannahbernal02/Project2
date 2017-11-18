package project2.demo;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

@Component
public class MyTasks {

    RestTemplate restTemplate = new RestTemplate();
    private int id = 1;
    private String randModel = "";
    private Random rand;

    //post
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


    //delete
    @Scheduled (cron="*/3 * * * * *") //10
    public void deleteRandomVehicle() {
        //remember to add an id in the url
        rand = new Random();
        //1-100
        int randId = rand.nextInt(100) + 1;
        Vehicle v = restTemplate.getForObject("http://localhost:8080/getVehicle/" + randId, Vehicle.class
        );
        if (v != null) {
            String url = "http://localhost:8080/deleteVehicle/" + randId;
            restTemplate.delete(url);
        }
    }

    //put
    @Scheduled (cron="*/3 * * * * *")
    public void updateVehicle() {
        String url = "http://localhost:8080/updateVehicle";
        rand = new Random();
        int randId = rand.nextInt(100) + 1; //1-100
        //remember same id
        Vehicle updatedVehicle = restTemplate.getForObject("http://localhost:8080/getVehicle/" + randId, Vehicle.class);
        if (updatedVehicle != null) {
            restTemplate.put(url, new Vehicle(randId, "UPDATED Vehicle", 99999, 123456), Vehicle.class);
            System.out.println("Newly updated vehicle: " + updatedVehicle);
        }
    }

    //get
    @Scheduled(cron="0 0 * * * *")
    public void latestVehiclesReport() {
        //remember id
        List<Vehicle> vehicleList = restTemplate.getForObject("http://localhost:8080/getLatestVehicles", List.class);
        System.out.println("latest is: ");
        //loop to print
        for (int i = 0; i < vehicleList.size(); i++) {
            System.out.println(vehicleList.get(i));
        }
    }
}