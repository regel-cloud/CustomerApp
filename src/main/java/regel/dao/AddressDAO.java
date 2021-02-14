package regel.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import regel.models.Address;


public interface AddressDAO extends JpaRepository<Address, Long> {
}
