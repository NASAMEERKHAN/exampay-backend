package com.sameer.exampay.service;

import com.sameer.exampay.entity.Payment;
import java.util.List;

public interface PaymentService {

    Payment savePayment(Payment payment);

    List<Payment> getAllPayments();
}