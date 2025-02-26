package com.vraj.magazinesystem.service;

import com.vraj.magazinesystem.model.Invoice;

/**
 * Manages invoice generation and payment processing.
 */
public class PaymentService {

    /**
     * Generates a new invoice for a given user ID and amount.
     */
    public Invoice generateInvoice(Long userId, double amount) {
        // Create a new invoice with a unique invoiceNumber (here, using currentTimeMillis as a simple example)
        Invoice invoice = new Invoice(System.currentTimeMillis(), amount, userId);
        return invoice;
    }
    /**
     * Process the invoice payment (stub—would integrate PayPal/Stripe in real code).
     */
    public void processPayment(Invoice invoice) {
        // Mark the invoice as paid
        invoice.setStatus("Paid");
        System.out.println("Invoice " + invoice.getInvoiceNumber() + " paid successfully.");

    }
}