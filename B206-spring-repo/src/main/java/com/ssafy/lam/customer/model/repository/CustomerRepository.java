<<<<<<< PATCH SET (700382 feat: user_rolesì와 user_type에 customer들어감. CustomerRepositor)
package com.ssafy.lam.customer.model.repository;

import com.ssafy.lam.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
    Optional<Customer> findByUserId(String customerId);

<<<<<<< PATCH SET (d7d4e8 feat: tokenInfo)
//    String findUserType(Customer customer);
=======
=======
package com.ssafy.lam.customer.model.repository;public class CustomerRepository {
>>>>>>> BASE      (9bdca5 Merge "feat: user_roles에 customer가 들어감" into dev-spring-hees)
>>>>>>> BASE      (dac024 feat: user_rolesì와 user_type에 customer들어감. CustomerRepositor)
}
