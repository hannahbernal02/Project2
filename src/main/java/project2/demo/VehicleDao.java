package project2.demo;

import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class VehicleDao {
        @PersistenceContext
        private EntityManager entityManager;

        public void create(Vehicle v) {
            entityManager.merge(v);
            return;
        }

        public Vehicle getById(int id) {
            return entityManager.find(Vehicle.class, id);
        }

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

        public void update(Vehicle v) {
            entityManager.merge(v);
            return;
        }

        public void delete(Vehicle v) {
            if (entityManager.find(Vehicle.class, v.getId()) != null) {
                entityManager.remove(v);
            }
            return;
        }
}
