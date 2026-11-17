package comrizsentosa.app.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import comrizsentosa.app.models.shopping.Customer;

public interface CustomerRepo1 extends JpaRepository<Customer, String> {

}
