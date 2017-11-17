package project2.demo;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.io.IOException;
import java.util.List;


@RestController
public class Controller {

    @Autowired
    private VehicleDao vehicleDao;

    @RequestMapping(value = "/addVehicle", method = RequestMethod.POST)
    public Vehicle addVehicle(@RequestBody Vehicle newVehicle) throws IOException {
        //insert new record into database table
        vehicleDao.create(newVehicle);
        return newVehicle;
    }

    @RequestMapping(value = "/getVehicle/{id}", method = RequestMethod.GET)
    public Vehicle getVehicle(@PathVariable("id") int id) throws IOException {
        //query from the database and return vehicle if found
        return vehicleDao.getById(id);
    }

    @RequestMapping(value = "/updateVehicle", method = RequestMethod.PUT)
    public Vehicle updateVehicle(@RequestBody Vehicle newVehicle) throws IOException {
        //update existing vehicle in the database
        vehicleDao.update(newVehicle);
        return newVehicle;
    }

    @RequestMapping(value = "/deleteVehicle/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteVehicle(@PathVariable("id") int id)  throws IOException {
        //delete the record from the database if found
        Vehicle vehicle = vehicleDao.getById(id);
        if (vehicle == null) {
            return new ResponseEntity<String>("Could not find/delete", HttpStatus.BAD_REQUEST);
        }
        vehicleDao.delete(vehicle);
        return new ResponseEntity<String>("Deleted", HttpStatus.OK);
    }

    @RequestMapping(value = "/getLatestVehicles", method = RequestMethod.GET)
    public List<Vehicle> getLatestVehicles() throws IOException {
        //query from the database and return the most recent 10 vehicles
        return vehicleDao.getVehicleList();
    }
}