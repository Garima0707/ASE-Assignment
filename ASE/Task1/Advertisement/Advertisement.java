package advertisement;

import advertiser.Advertiser;
import java.util.ArrayList;
import java.util.List;

public class Advertisement {

    public enum AdPlacement {
        FRONT_COVER, BACK_COVER, INSIDE_PAGE
    }

    private int id;
    private String size; // "Full Page", "Half Page", "Quarter Page"
    private AdPlacement placement;
    private AdStatus status;
    private boolean paymentConfirmed;
    private Advertiser advertiser;
    private String reviewStatus; // "Unreviewed", "Reviewed"
    private List<String> rejectionReasons;
    private boolean isReadyForPayment;
    private boolean readyForPayment;
    private boolean reviewedByEditing;
    private boolean confirmedByAdvertiser;
    private boolean archived;
    
    // Base prices for ad sizes
    private static final double FULL_PAGE_PRICE = 300.0;
    private static final double HALF_PAGE_PRICE = 200.0;
    private static final double QUARTER_PAGE_PRICE = 100.0;

    // Placement multipliers
    private static final double FRONT_COVER_MULTIPLIER = 1.5;
    private static final double BACK_COVER_MULTIPLIER = 1.2;
    private static final double INSIDE_PAGE_MULTIPLIER = 1.0;

    // Allowed sizes for validation
    private static final List<String> ALLOWED_SIZES = List.of("Full Page", "Half Page", "Quarter Page");

    public Advertisement(int id, String size, AdPlacement placement, Advertiser advertiser) {
        validateInputs(size, placement, advertiser);

        this.id = id;
        this.size = size;
        this.placement = placement;
        this.advertiser = advertiser;
        this.status = AdStatus.PENDING;
        this.paymentConfirmed = false;
        this.reviewStatus = "Unreviewed";
        this.rejectionReasons = new ArrayList<>();
        this.isReadyForPayment = false;
        this.readyForPayment = false;
        this.reviewedByEditing = false;
        this.confirmedByAdvertiser = false;
        this.archived = false;
    }

    // Helper method for validation
    private void validateInputs(String size, AdPlacement placement, Advertiser advertiser) {
        if (!ALLOWED_SIZES.contains(size)) {
            throw new IllegalArgumentException("Invalid size: " + size);
        }
        if (placement == null) {
            throw new IllegalArgumentException("Placement cannot be null.");
        }
        if (advertiser == null) {
            throw new NullPointerException("Advertiser cannot be null.");
        }
    }

    public int getId() {
        return id;
    }

    public boolean isReadyForPayment() {
        return isReadyForPayment;
    }

    public String getSize() {
        return size;
    }

    public AdPlacement getPlacement() {
        return placement;
    }

    public AdStatus getStatus() {
        return status;
    }

    public void confirmPayment() {
    this.paymentConfirmed = true;
}

    public boolean isPaymentConfirmed() {
        return paymentConfirmed;
    }

    public void setReadyForPayment(boolean readyForPayment) {
        this.readyForPayment = readyForPayment;
    }

    public boolean isReviewedByEditing() {
        return reviewedByEditing;
    }
    
    public void setReviewedByEditing(boolean reviewedByEditing) {
        this.reviewedByEditing = reviewedByEditing;
    }

    public boolean isConfirmedByAdvertiser() {
        return confirmedByAdvertiser;
    }

    public void setConfirmedByAdvertiser(boolean confirmedByAdvertiser) {
        this.confirmedByAdvertiser = confirmedByAdvertiser;
    }
    
    public void confirmPaymentIfReady() {
        if (!isReadyForPayment) {
            throw new IllegalStateException("Advertisement is not ready for payment.");
        }
        this.paymentConfirmed = true;
    }

    public Advertiser getAdvertiser() {
        return advertiser;
    }

    public void markAsArchived() {
        this.status = AdStatus.ARCHIVED;
        this.paymentConfirmed = false; // Ensure archived ads are flagged as unpaid
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public List<String> getRejectionReasons() {
        return List.copyOf(rejectionReasons); // Return an immutable list
    }

    public void markAsReviewed(String status, List<String> reasons) {
        this.reviewStatus = status;
        this.rejectionReasons = reasons != null ? reasons : new ArrayList<>();
        this.status = "Reviewed".equals(status) ? AdStatus.APPROVED : AdStatus.PENDING;
    }

    public void logDetails() {
        System.out.println("Advertisement Details:");
        System.out.println("ID: " + id);
        System.out.println("Size: " + size);
        System.out.println("Placement: " + placement);
        System.out.println("Status: " + status);
        System.out.println("Review Status: " + reviewStatus);
        System.out.println("Payment Confirmed: " + paymentConfirmed);
        System.out.println("Advertiser: " + advertiser.getName() + " (" + advertiser.getEmail() + ")");
        System.out.println("Rejection Reasons: " + String.join(", ", rejectionReasons));
    }

    public boolean isArchived() {
        return status == AdStatus.ARCHIVED;
    }

    public double calculateCost() {
        double basePrice;
        switch (size) {
            case "Full Page":
                basePrice = FULL_PAGE_PRICE;
                break;
            case "Half Page":
                basePrice = HALF_PAGE_PRICE;
                break;
            case "Quarter Page":
                basePrice = QUARTER_PAGE_PRICE;
                break;
            default:
                throw new IllegalArgumentException("Unknown advertisement size: " + size);
        }

        double placementMultiplier;
        switch (placement) {
            case FRONT_COVER:
                placementMultiplier = FRONT_COVER_MULTIPLIER;
                break;
            case BACK_COVER:
                placementMultiplier = BACK_COVER_MULTIPLIER;
                break;
            case INSIDE_PAGE:
                placementMultiplier = INSIDE_PAGE_MULTIPLIER;
                break;
            default:
                throw new IllegalArgumentException("Unknown advertisement placement: " + placement);
        }

        return basePrice * placementMultiplier;
    }
    
    
    public enum AdStatus {
    PENDING,
    APPROVED,
    REJECTED,
    ARCHIVED
}

}
