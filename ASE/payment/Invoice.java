package payment;

import advertiser.Advertiser;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Invoice {
    private static int invoiceCounter = 1;
    private String id;
    private Advertiser advertiser;
    private double amount;
    private InvoiceStatus status;
    private LocalDate issueDate;
    private LocalDate dueDate;
    
    private List<String> statusHistory = new ArrayList<>();

    public enum InvoiceStatus {
        UNPAID, PAID, PENDING, CANCELLED
    }

    public Invoice(Advertiser advertiser, double amount) {
        this.id = UUID.randomUUID().toString();
        this.advertiser = advertiser;
        this.amount = amount;
        this.status = InvoiceStatus.UNPAID;
        this.issueDate = LocalDate.now();
        this.dueDate = calculateDueDate(issueDate, 30);
    }

    public void markAsPaid() {
        this.status = InvoiceStatus.PAID;
        statusHistory.add("Marked as paid on " + LocalDate.now());
    }

    public boolean isOverdue() {
        return status == InvoiceStatus.UNPAID && LocalDate.now().isAfter(dueDate);
    }

    public double calculateLateFee() {
        if (!isOverdue()) {
            return 0;
        }
        long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(dueDate, LocalDate.now());
        double lateFeeRate = overdueDays > 30 ? 0.05 : 0.02;
        return overdueDays * amount * lateFeeRate;
    }

    private LocalDate calculateDueDate(LocalDate issueDate, int days) {
        return issueDate.plusDays(days);
    }

    public String getId() {
        return id;
    }

    public Advertiser getAdvertiser() {
        return advertiser;
    }

    public double getAmount() {
        return amount;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public List<String> getStatusHistory() {
        return statusHistory;
    }
}
