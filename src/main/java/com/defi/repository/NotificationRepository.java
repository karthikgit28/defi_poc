package com.defi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.defi.entity.Notification;


public interface NotificationRepository extends JpaRepository<Notification, Integer>{
	
	List<Notification> findByNotificationStatusAndTableNameAndCustomerId(String status,String table,int cId);
	List<Notification> findByCustomerId(int cId);

}
