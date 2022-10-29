package com.example.demo.cash;


import com.example.demo.cash.model.CashLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashLogRepository extends JpaRepository<CashLog, Long> {
}