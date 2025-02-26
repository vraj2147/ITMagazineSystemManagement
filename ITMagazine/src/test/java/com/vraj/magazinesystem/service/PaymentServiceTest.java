package com.vraj.magazinesystem.service;



import com.vraj.magazinesystem.model.Invoice;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {
    @Test
    void testProcessPayment() {
        PaymentService service = new PaymentService();
        Invoice invoice = new Invoice(1L, 100.0, 1L);
        invoice.setStatus("Pending");

        service.processPayment(invoice);

        assertEquals("Paid", invoice.getStatus(), "Invoice should be marked as paid");
    }
}

