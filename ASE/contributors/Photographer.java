package contributors;

public class Photographer extends Contributor {

    /**
     * Constructor to initialize a Photographer.
     *
     * @param id         Unique ID of the photographer.
     * @param name       Name of the photographer.
     * @param paymentDue Outstanding payment due to the photographer.
     */
    public Photographer(int id, String name, double paymentDue) {
        super(id, name, "Photographer", paymentDue);
    }

    /**
     * Allows the photographer to take a photo.
     *
     * @param photoId     Unique ID of the photograph.
     * @param description Description or caption of the photograph.
     * @param resolution
     * @return A new Photograph object taken by the photographer.
     */
    public Photograph takePhoto(int photoId, String description, String resolution) {
        if (photoId <= 0) {
            throw new IllegalArgumentException("Photo ID must be positive.");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty.");
        }
        if (resolution == null || !resolution.matches("\\d+x\\d+")) {
            throw new IllegalArgumentException("Resolution must be in 'WidthxHeight' format.");
        }

        System.out.println("Photographer " + getName() + " is taking a photograph: " + description);
        return new Photograph(photoId, description, this, resolution);
    }

    /**
     * Updates the payment due for the photographer.
     *
     * @param amount Payment amount to deduct from outstanding dues.
     */
    public void updatePayment(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid payment amount.");
            return;
        }
        if (amount > getPaymentDue()) {
            System.out.println("Payment exceeds outstanding dues. Adjusting to full payment.");
            amount = getPaymentDue();
        }
        setPaymentDue(getPaymentDue() - amount);
        System.out.println("Payment of " + amount + " processed for photographer " + getName() + ".");
    }
    
    @Override
    public void submitStory() {
        System.out.println("Photographer " + getName() + " submits a set of photographs.");
    }
}
