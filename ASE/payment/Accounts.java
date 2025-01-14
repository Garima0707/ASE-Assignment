package payment;

import advertisement.Advertisement;
import advertiser.Advertiser;
import contributors.Journalist;
import contributors.Photographer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Accounts {
    private static final double DEFAULT_AD_PRICE = 100.0; // Default price per advertisement
    private List<Payment> payments;
    private List<Invoice> outstandingInvoices;

    public Accounts() {
        this.payments = new ArrayList<>();
        this.outstandingInvoices = new ArrayList<>();
    }

    public boolean processBatchPayment(List<Advertisement> ads,double expectedTotal) {
        List<Advertisement> validAds = new ArrayList<>();
        List<Advertisement> archivedAds = new ArrayList<>();
        double calculatedTotal = 0.0;

        for (Advertisement ad : ads) {
            if (ad.isArchived()) {
                archivedAds.add(ad);
            } else if (ad.isReadyForPayment()) {
                validAds.add(ad);
                calculatedTotal += ad.calculateCost(); // Example price calculation
            }
        }

        // Detailed logging
    System.out.println("Batch Payment Details:");
    System.out.println("Total Ads in Batch: " + ads.size());
    System.out.println("Archived Ads Excluded: " + archivedAds.size());
    System.out.println("Valid Ads for Payment: " + validAds.size());
    System.out.println("Calculated Total: $" + calculatedTotal);
    System.out.println("Expected Total: $" + expectedTotal);
    
        System.out.println("Batch Payment Details:");
        System.out.println("Total Ads in Batch: " + ads.size());
        System.out.println("Archived Ads Excluded: " + archivedAds.size());
        archivedAds.forEach(ad -> 
            System.out.println("Excluded Ad ID: " + ad.getId() + " (Archived)")
        );

        if (!validAds.isEmpty() && Math.abs(calculatedTotal - expectedTotal) < 0.01) {
        payments.add(new Payment(calculatedTotal, new Date(), Payment.PaymentStatus.CONFIRMED));
        for (Advertisement ad : validAds) {
            ad.confirmPayment(); // Ensure this method exists in Advertisement
        }
        System.out.println("Batch payment successful for " + validAds.size() + " ads.");
        return true;
    }

        System.out.println("Batch payment failed: Valid ads do not match the expected total.");
        return false;
    }

    public boolean processIndependentPayment(Advertisement ad) {
    System.out.println("Processing payment for Ad ID: " + ad.getId());

    if (ad.isReadyForPayment()) {
        double cost = ad.calculateCost();
        payments.add(new Payment(cost, new Date(), Payment.PaymentStatus.CONFIRMED));
        ad.confirmPayment();
        System.out.println("Payment successful for Ad ID: " + ad.getId() +
                           " | Amount: $" + cost);
        return true;
    }

    System.out.println("Payment failed for Ad ID: " + ad.getId() +
                       " | Reason: Not ready for payment.");
    return false;
}

    public Invoice issueInvoice(Advertiser advertiser, double amount) {
    Invoice invoice = new Invoice(advertiser, amount);
    outstandingInvoices.add(invoice);
    System.out.println("Invoice issued to " + advertiser.getName() + 
                       " | Amount: $" + amount + 
                       " | Invoice ID: " + invoice.getId());
    return invoice;
}

    public boolean handleLatePayments() {
    // Flag to track whether any late payments are found
    boolean latePaymentsHandled = false;

    // Process late payments and check if any were found    
    for (Invoice invoice : outstandingInvoices) {
        if (invoice.isOverdue()) {
            latePaymentsHandled = true;
            double lateFee = invoice.calculateLateFee();
            System.out.println("Late payment for Invoice ID: " + invoice.getId() +
                               " | Advertiser: " + invoice.getAdvertiser().getName() +
                               " | Late Fee: $" + lateFee);
        }
    }

    // Return whether any late payments were handled
    return latePaymentsHandled;
}


    public List<Payment> getPayments() {
        return payments;
    }

    public List<Invoice> getOutstandingInvoices() {
        return outstandingInvoices;
    }

    public boolean processContributorPayment(Object contributor, double amount) {
    if (contributor instanceof Journalist) {
        Journalist journalist = (Journalist) contributor;
        System.out.println("Processing payment for Journalist: " + journalist.getName());
        // Payment logic for journalist
        payments.add(new Payment(amount, new Date(), Payment.PaymentStatus.CONFIRMED));
        System.out.println("Payment successful for Journalist: " + journalist.getName() + " | Amount: $" + amount);
        return true;
    } else if (contributor instanceof Photographer) {
        Photographer photographer = (Photographer) contributor;
        System.out.println("Processing payment for Photographer: " + photographer.getName());
        // Payment logic for photographer
        payments.add(new Payment(amount, new Date(), Payment.PaymentStatus.CONFIRMED));
        System.out.println("Payment successful for Photographer: " + photographer.getName() + " | Amount: $" + amount);
        return true;
    } else {
        System.out.println("Contributor type not recognized.");
        return false;
    }
}

}
