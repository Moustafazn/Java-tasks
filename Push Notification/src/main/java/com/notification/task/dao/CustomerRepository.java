package com.notification.task.dao;

import com.notification.task.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("customerRepository")
public interface CustomerRepository  extends JpaRepository<Customer, Long> {

    Customer findByEmail(String email);
}
