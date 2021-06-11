package com.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.model.GroupBooking;

@Repository
public interface GroupBookingService extends JpaRepository<GroupBooking, Long> {

    @Query(value = "select gb from GroupBooking as gb where orderId=?1")
    GroupBooking getBookingByOrderId(String orderId);

}
