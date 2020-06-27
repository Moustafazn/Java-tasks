package com.notification.task.dao;

import com.notification.task.model.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("riderRepository")
public interface RiderRepository  extends JpaRepository<Rider, Long> {

    Rider findByEmail(String email);
}
