package com.zoroark.orderproducer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zoroark.orderproducer.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}