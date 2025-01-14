package payment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Payment {
    private double amount;
    private Date date;
    private PaymentStatus status;

    /**
     * Constructor to create a Payment object.
     *
     * @param amount Amount of the payment.
     * @param date   Date of the payment.
     * @param status
     */
    public Payment(double amount, Date date, PaymentStatus status) {
        this.amount = amount;
        this.date = date;
        this.status = status;
    }

    /**
     * Confirms the payment by changing its status to "Confirmed."
     */
    public void confirm() {
        this.status = PaymentStatus.CONFIRMED;
    }

    // Getters for controlled access to attributes

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    // Utility method to display payment details
    @Override
public String toString() {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return "Payment{" +
           "amount=" + amount +
           ", date=" + formatter.format(date) +
           ", status=" + status +
           '}';
}

    public enum PaymentStatus {
    PENDING,
    CONFIRMED,
    FAILED
}
}
