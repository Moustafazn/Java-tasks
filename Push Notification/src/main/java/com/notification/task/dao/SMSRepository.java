package com.notification.task.dao;

import com.notification.task.model.SMS;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("smsRepository")
public interface SMSRepository extends PagingAndSortingRepository<SMS, Long> {

    List<SMS> findAllByIsSentOrderByCreatedAtAsc(boolean isSent, Pageable pageable);

}
