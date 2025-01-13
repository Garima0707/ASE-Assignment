/* GarimaJain_21554093*/
package system;

import advertisement.Advertisement;
import advertiser.Advertiser;
import contributors.Contributor;
import magazine.MagazineIssue;
import payment.Accounts;
import payment.Invoice;

import java.util.*;
import user.MarketingStaff;

public class SystemWorkFlow {
    private List<Advertisement> advertisements = new ArrayList<>();
    private List<MagazineIssue> magazineIssues = new ArrayList<>();
    private List<Contributor> contributors = new ArrayList<>();
    private Accounts accounts = new Accounts();
    private Queue<Advertisement> processingQueue = new LinkedList<>();

    /**
     * Adds a new advertisement to the system.
     *
     * @param advertisement Advertisement to be added.
     */
    public void addAdvertisement(Advertisement advertisement) {
        if (advertisement == null) {
            throw new IllegalArgumentException("Advertisement cannot be null.");
        }
        advertisements.add(advertisement);
        processingQueue.add(advertisement);
        System.out.println("Advertisement ID: " + advertisement.getId() + " added to the system.");
    }

    /**
     * Retrieves the details of an advertisement by its ID.
     *
     * @param id Advertisement ID.
     * @return Advertisement object if found, null otherwise.
     */
    public Advertisement getAdvertisementDetails(int id) {
        return advertisements.stream()
                .filter(ad -> ad.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Processes advertisements in the queue.
     */
    public void processAdvertisements() {
        while (!processingQueue.isEmpty()) {
            Advertisement ad = processingQueue.poll();
            if (ad != null) {
                System.out.println("Processing advertisement ID: " + ad.getId());
                if ("Approved".equals(ad.getStatus())) {
                    System.out.println("Advertisement is approved and ready for payment.");
                    if (ad.isPaymentConfirmed()) {
                        System.out.println("Payment already confirmed for Advertisement ID: " + ad.getId());
                    } else {
                        System.out.println("Payment is pending for Advertisement ID: " + ad.getId());
                    }
                } else if ("Unapproved".equals(ad.getStatus())) {
                    System.out.println("Advertisement ID: " + ad.getId() + " is unapproved and will be archived.");
                    ad.markAsArchived();
                } else {
                    System.out.println("Advertisement ID: " + ad.getId() + " is pending review.");
                }
            } else {
                System.out.println("No advertisement to process.");
            }
        }
    }

    /**
     * Archives unused submissions such as unapproved advertisements.
     */
    public void archiveUnusedSubmissions() {
        advertisements.stream()
            .filter(ad -> "Unapproved".equals(ad.getStatus()) || "Expired".equals(ad.getStatus()))
            .forEach(ad -> {
                ad.markAsArchived();
                System.out.println("Advertisement ID: " + ad.getId() + " has been archived.");
            });
    }

    /**
     * Publishes a magazine issue.
     *
     * @param issue Magazine issue to be published.
     */
    public void publishIssue(MagazineIssue issue) {
        if (issue == null) {
            throw new IllegalArgumentException("Magazine issue cannot be null.");
        }
        if (!issue.isReadyForPublication()) {
            System.out.println("Issue cannot be published as it is not ready.");
            return;
        }
        magazineIssues.add(issue);
        issue.publish();
        System.out.println("Magazine issue published: " + issue.getId());
    }

    /**
     * Confirms payment for an advertiser.
     *
     * @param advertiser Advertiser whose payment is being confirmed.
     */
    public void confirmPayment(Advertiser advertiser) {
        double totalAmount = advertiser.calculateTotalAmount();
        Invoice invoice = accounts.issueInvoice(advertiser, totalAmount);
        System.out.println("Invoice issued: " + invoice.getId());
    }

    /**
     * Makes payments to contributors.
     */
    public void makePaymentsToContributors() {
        contributors.forEach(contributor -> {
            double paymentDue = contributor.getPaymentDue();
            if (paymentDue > 0) {
                accounts.processBatchPayment(Collections.emptyList(), paymentDue);
                System.out.println("Payment made to contributor: " + contributor.getName());
            }
        });
    }

    /**
     * Validates a batch of advertisements.
     *
     * @param ads List of advertisements to validate.
     * @return True if all ads are valid, false otherwise.
     */
    public boolean validateBatch(List<Advertisement> ads) {
        return ads.stream().allMatch(ad -> "Approved".equals(ad.getStatus()) && !"Archived".equals(ad.getStatus()));
    }

    /**
     * Notifies an advertiser with a specific message.
     *
     * @param advertiser Advertiser to notify.
     * @param message    Notification message.
     */
    public void notifyAdvertiser(Advertiser advertiser, String message) {
        advertiser.receiveNotification(message);
    }

    /**
     * Forwards advertisements to the editing department for review.
     *
     * @param ads List of advertisements to forward.
     */
    public void forwardToEditingDepartment(List<Advertisement> ads) {
        System.out.println("Advertisements forwarded to editing department.");
    }

    /**
     * Authenticates a user based on username and password.
     *
     * @param username Username.
     * @param password Password.
     * @return True if authentication is successful, false otherwise.
     */
    public boolean authenticateUser(String username, String password) {
        if (username == null || password == null) {
            throw new IllegalArgumentException("Username and password cannot be null.");
        }
        return "admin".equals(username) && "password".equals(password);
    }

    /**
     * Notifies staff with a specific message.
     *
     * @param staff   Staff to notify.
     * @param message Notification message.
     */
    public void notifyStaff(MarketingStaff staff, String message) {
        System.out.println("Notification to marketing staff: " + message);
    }

    /**
     * Generates a summary report of all advertisements.
     */
    public void generateAdvertisementReport() {
        System.out.println("Advertisement Report:");
        advertisements.forEach(ad -> {
            System.out.println("Advertisement ID: " + ad.getId() + ", Status: " + ad.getStatus() + ", Payment Confirmed: " + ad.isPaymentConfirmed());
        });
    }

    public List<Advertisement> getAdvertisements() {
        return new ArrayList<>(advertisements);
    }
} 
