package user;


import advertisement.Advertisement;
import advertisement.Advertisement.AdStatus;

import java.util.List;

public class EditingDepartment extends User {

    /**
     * Constructor for the EditingDepartment class.
     *
     * @param id       User ID.
     * @param username Username for login.
     * @param password Password for login.
     * @param role     Role of the user.
     */
    public EditingDepartment(int id, String username, String password, String role) {
        super(id, username, password, role);
    }

    /**
     * Reviews an advertisement.
     *
     * @param ad Advertisement to review.
     * @return True if the advertisement is approved, false otherwise.
     */
    public boolean reviewAd(Advertisement ad) {
        if (ad == null) {
            System.out.println("Advertisement is null. Cannot review.");
            return false;
        }

        AdStatus currentStatus = ad.getStatus(); // Assuming getStatus() returns an AdStatus enum
        if (currentStatus == AdStatus.PENDING) { // Use the enum directly
            ad.markAsReviewed(AdStatus.APPROVED.toString(), null);
            ad.setReadyForPayment(true); // Mark as ready for payment
            System.out.println("Advertisement ID: " + ad.getId() + " approved and marked as ready for payment.");
            return true;
        } else {
            System.out.println("Advertisement ID: " + ad.getId() + " cannot be reviewed. Current status: " + currentStatus);
            return false;
        }
    }

    /**
     * Notifies the rejection of an advertisement.
     *
     * @param ad      Advertisement that is rejected.
     * @param reasons Reasons for rejection.
     */
    public void notifyRejection(Advertisement ad, List<String> reasons) {
        if (ad == null) {
            System.out.println("Advertisement is null. Cannot reject.");
            return;
        }

        if (reasons == null || reasons.isEmpty()) {
            System.out.println("Rejection reasons cannot be null or empty for Advertisement ID: " + ad.getId());
            return;
        }

        // Check if advertiser exists before notifying
        if (ad.getAdvertiser() == null) {
            System.out.println("No advertiser associated with Advertisement ID: " + ad.getId());
            return;
        }

        ad.markAsReviewed(AdStatus.REJECTED.toString(), reasons);
        ad.getAdvertiser().receiveRejectionNotification(ad, reasons);
        System.out.println("Advertisement ID: " + ad.getId() + " rejected for the following reasons:");
        reasons.forEach(reason -> System.out.println("- " + reason));
    }
}
