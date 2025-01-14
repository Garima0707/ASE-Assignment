package contributors;

public class Journalist extends Contributor {

    /**
     * Constructor to initialize a Journalist.
     *
     * @param id         Unique ID of the journalist.
     * @param name       Name of the journalist.
     * @param paymentDue Outstanding payment due to the journalist.
     */
    public Journalist(int id, String name, double paymentDue) {
        super(id, name, "Journalist", paymentDue);
    }

    /**
     * Allows the journalist to write a story.
     *
     * @param storyId Unique ID of the story.
     * @param title   Title of the story.
     * @param content Content of the story.
     * @return A new Story object written by the journalist.
     */
    public Story writeStory(int storyId, String title, String content) {
        System.out.println("Journalist " + getName() + " is writing a story titled: " + title);
        return new Story(storyId, title, content, this);
    }

    /**
     * Updates the payment due for the journalist.
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
        System.out.println("Payment of " + amount + " processed for journalist " + getName() + ".");
    }
    
    @Override
    public void submitStory() {
        System.out.println("Journalist " + getName() + " submits a written story.");
    }
}
