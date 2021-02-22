package fooddelivery;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

//public interface 주문Repository extends PagingAndSortingRepository<Order, Long>{
//public interface 주문Repository extends JpaRepository<Order, String>{
public interface 주문Repository extends MongoRepository<Order, String>{


}