
package advertiser;

import advertisement.Advertisement;
import payment.Payment;
import java.util.*;

public class Advertiser {
    private int id;
    private String name;
    private String contactInfo;
    private List<Payment> paymentHistory;
    private List<Advertisement> advertisements;
    private List<String> notifications;

    public Advertiser(int id, String name, String contactInfo) {
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
        this.paymentHistory = new ArrayList<>();
        this.advertisements = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    public String getName() {
    return name;
    }
     
    public void placeAdvertisement(Advertisement advertisement) {
        advertisements.add(advertisement);
    }

    public List<Advertisement> getActiveAdvertisements() {
        return advertisements.stream()
            .filter(ad -> !ad.getStatus().equalsIgnoreCase("Archived"))
            .toList();
    }

    public boolean makeBatchPayment(List<Advertisement> ads, double amount) {
        double total = ads.stream().filter(Advertisement::isReadyForPayment).count() * 100; // Example pricing logic
        if (total <= amount) {
            ads.forEach(Advertisement::confirmPayment);
            paymentHistory.add(new Payment(amount, new Date(), Payment.PaymentStatus.CONFIRMED));
            return true;
        }
        return false;
    }

    public boolean makeIndependentPayment(Advertisement ad, double amount) {
        if (ad.isReadyForPayment() && amount >= 100) { // Example price per ad
            ad.confirmPayment();
            paymentHistory.add(new Payment(amount, new Date(), Payment.PaymentStatus.CONFIRMED));
            return true;
        }
        return false;
    }

    public double getTotalPaymentsMade() {
        return paymentHistory.stream()
            .mapToDouble(Payment::getAmount)
            .sum();
    }

    public void receiveNotification(String message) {
        notifications.add(message);
        System.out.println("Notification for Advertiser " + name + ": " + message);
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void retryFailedPayments() {
    advertisements.stream()
        .filter(ad -> !ad.isPaymentConfirmed() && ad.isReadyForPayment())
        .forEach(ad -> {
            boolean success = makeIndependentPayment(ad, 100.0); // Example price
            if (success) {
                System.out.println("Payment retried and confirmed for Advertisement ID: " + ad.getId());
            } else {
                System.out.println("Retry failed for Advertisement ID: " + ad.getId());
            }
        });
}


    public double calculateTotalAmount() {
        return advertisements.stream()
            .filter(ad -> ad.isPaymentConfirmed())
            .mapToDouble(ad -> 100.0) // Example price
            .sum();
    }

    public void receiveRejectionNotification(Advertisement ad, List<String> reasons) {
        System.out.println("Advertisement ID: " + ad.getId() + " rejected for the following reasons:");
        reasons.forEach(System.out::println);
    }

    public Integer getId() {
        return id;
    }
}
