package contributors;

/**
 * The Contributor class serves as a base class for all contributors such as journalists and photographers.
 */
public abstract class Contributor {
    private int id;            // Unique ID of the contributor
    private String name;       // Name of the contributor
    private String role;       // Role (e.g., "Journalist", "Photographer")
    private double paymentDue; // Outstanding payment due to the contributor

    /**
     * Constructor to initialize a Contributor.
     *
     * @param id         Unique ID of the contributor.
     * @param name       Name of the contributor.
     * @param role       Role of the contributor.
     * @param paymentDue Outstanding payment due to the contributor.
     */
    public Contributor(int id, String name, String role, double paymentDue) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.paymentDue = paymentDue;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public double getPaymentDue() {
        return paymentDue;
    }

    public void setPaymentDue(double paymentDue) {
        this.paymentDue = paymentDue;
    }

    public abstract void submitStory();
    
    /**
     * Pay the contributor a specific amount, reducing their outstanding payment.
     *
     * @param amount Amount to pay.
     */
    public void receivePayment(double amount) {
        if (amount <= 0) {
            System.out.println("Payment amount must be positive.");
            return;
        }
        if (amount > paymentDue) {
            System.out.println("Payment exceeds the due amount. Adjusting to pay only the outstanding balance.");
            amount = paymentDue;
        }
        paymentDue -= amount;
        System.out.println("Payment of $" + amount + " received by " + name + ". Remaining payment due: $" + paymentDue);
    }

    @Override
    public String toString() {
        return "Contributor{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", role='" + role + '\'' +
               ", paymentDue=" + paymentDue +
               '}';
    }
}
