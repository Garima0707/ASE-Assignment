package user;

import advertisement.Advertisement;
import system.SystemWorkFlow;
import java.util.ArrayList;
import java.util.List;

public class MarketingStaff extends User {

    private SystemWorkFlow workflow;
    private List<String> notifications = new ArrayList<>();

    /**
     * Constructor for the MarketingStaff class.
     *
     * @param id       User ID.
     * @param username Username for login.
     * @param password Password for login.
     * @param role     Role of the user.
     * @param workflow The system workflow to interact with advertisements.
     */
    public MarketingStaff(int id, String username, String password, String role, SystemWorkFlow workflow) {
        super(id, username, password, role);
        if (workflow == null) {
            throw new IllegalArgumentException("Workflow cannot be null");
        }
        this.workflow = workflow;
    }

    /**
     * Requests the list of advertisements from the system.
     *
     * @param filterStatus The status to filter advertisements by (e.g., "Pending", "Confirmed").
     * @return List of filtered advertisements.
     */
    public List<Advertisement> requestAdDetails(String filterStatus) {
        System.out.println("Fetching advertisement details...");
        return workflow.getAdvertisements().stream()
                .filter(ad -> filterStatus == null || ad.getStatus().equals(filterStatus))
                .toList();
    }
    
    /**
     * Notify the user of updates or messages.
     *
     * @param message The notification message.
     */
    public void notifyUpdate(String message) {
        if (message == null || message.isEmpty()) {
            System.out.println("Empty notification received.");
            return;
        }
        notifications.add(message);
        System.out.println("Notification: " + message);
    }

    /**
     * Get the list of notifications received by the marketing staff.
     *
     * @return List of notification messages.
     */
    public List<String> getNotifications() {
        return notifications;
    }

    /**
     * Initiates advertisement processing in the system.
     */
    public void initiateAdProcessing() {
        System.out.println("Initiating advertisement processing...");
        workflow.processAdvertisements();
    }

    /**
     * Receives confirmation of processed advertisements.
     *
     * @param ads List of confirmed advertisements.
     */
    public void receiveConfirmation(List<Advertisement> ads) {
        if (ads == null || ads.isEmpty()) {
            System.out.println("No advertisements were confirmed.");
            return;
        }
        System.out.println("Processing confirmation received for the following advertisements:");
        ads.forEach(ad -> System.out.println("Advertisement ID: " + ad.getId()));
    }
}
