package com.notification.task.dao;

import com.notification.task.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository  extends JpaRepository<User, Long> {

   User findByEmailAndPassword(String email, String password);
}
