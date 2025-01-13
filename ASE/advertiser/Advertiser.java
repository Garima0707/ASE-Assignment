package advertiser;

import advertisement.Advertisement;
import advertisement.Advertisement.AdStatus;
import payment.Payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
    return contactInfo; // Assuming contactInfo contains the email address
}

    public String getContactInfo() {
        return contactInfo;
    }

    public void placeAdvertisement(Advertisement advertisement) {
        if (advertisement == null) {
            throw new IllegalArgumentException("Advertisement cannot be null.");
        }
        advertisements.add(advertisement);
    }

    public List<Advertisement> getActiveAdvertisements() {
        return advertisements.stream()
                .filter(ad -> ad.getStatus() != AdStatus.ARCHIVED)
                .collect(Collectors.toList());
    }

    public boolean makeBatchPayment(List<Advertisement> ads, double amount) {
        double totalCost = ads.stream()
                .filter(Advertisement::isReadyForPayment)
                .mapToDouble(Advertisement::calculateCost)
                .sum();

        if (totalCost <= amount) {
            ads.stream()
                    .filter(Advertisement::isReadyForPayment)
                    .forEach(Advertisement::confirmPaymentIfReady);
            paymentHistory.add(new Payment(totalCost, new Date(), Payment.PaymentStatus.CONFIRMED));
            return true;
        }
        return false;
    }

    public boolean makeIndependentPayment(Advertisement ad, double amount) {
        if (ad == null) {
            throw new IllegalArgumentException("Advertisement cannot be null.");
        }

        double cost = ad.calculateCost();
        if (ad.isReadyForPayment() && amount >= cost) {
            ad.confirmPaymentIfReady();
            paymentHistory.add(new Payment(cost, new Date(), Payment.PaymentStatus.CONFIRMED));
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
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Notification message cannot be null or blank.");
        }
        notifications.add(message);
        System.out.println("Notification for Advertiser " + name + ": " + message);
    }

    public List<String> getNotifications() {
        return List.copyOf(notifications); // Return immutable list
    }

    public void clearNotifications() {
        notifications.clear();
    }

    public void retryFailedPayments() {
        advertisements.stream()
                .filter(ad -> !ad.isPaymentConfirmed() && ad.isReadyForPayment())
                .forEach(ad -> {
                    double cost = ad.calculateCost();
                    boolean success = makeIndependentPayment(ad, cost);
                    if (success) {
                        System.out.println("Payment retried and confirmed for Advertisement ID: " + ad.getId());
                    } else {
                        System.out.println("Retry failed for Advertisement ID: " + ad.getId());
                    }
                });
    }

    public double calculateTotalAmount() {
        return advertisements.stream()
                .filter(Advertisement::isPaymentConfirmed)
                .mapToDouble(Advertisement::calculateCost)
                .sum();
    }

    public void receiveRejectionNotification(Advertisement ad, List<String> reasons) {
        if (ad == null || reasons == null) {
            throw new IllegalArgumentException("Advertisement and reasons cannot be null.");
        }
        System.out.println("Advertisement ID: " + ad.getId() + " rejected for the following reasons:");
        reasons.forEach(System.out::println);
    }

    public List<Advertisement> getAllAdvertisements() {
        return List.copyOf(advertisements); // Return immutable list
    }
}
