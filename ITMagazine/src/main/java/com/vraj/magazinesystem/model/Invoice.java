package com.vraj.magazinesystem.model;



import java.time.LocalDateTime;

/**
 * Represents an invoice for paying a contributor (e.g., a journalist).
 */
public class Invoice {

    private Long invoiceNumber;   // A unique invoice identifier
    private double amount;        // The amount to be paid
    private Long userId;          // The ID of the user to be paid
    private String status;        // e.g., "Pending", "Paid", "Failed"
    private LocalDateTime issueDate; // When this invoice was generated

    public Invoice() {
        // No-arg constructor if frameworks need it
    }

    /**
     * Creates a new Invoice with a given invoice number, amount, and user ID.
     * By default, the status is set to "Pending" and the issue date is "now".
     *
     * @param invoiceNumber A unique number or ID for this invoice
     * @param amount        The payment amount
     * @param userId        The user receiving payment
     */
    public Invoice(Long invoiceNumber, double amount, Long userId) {
        this.invoiceNumber = invoiceNumber;
        this.amount = amount;
        this.userId = userId;
        this.status = "Pending";
        this.issueDate = LocalDateTime.now();
    }

    // ---------- Getters & Setters ----------

    public Long getInvoiceNumber() {
        return invoiceNumber;
    }
    public void setInvoiceNumber(Long invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }
    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }
}
