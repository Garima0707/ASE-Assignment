package magazine;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import advertisement.Advertisement;
import advertiser.Advertiser;
import contributors.Photograph;
import contributors.Story;

public class MagazineIssue {
    private int issueNumber;
    private Date date;
    private List<Advertisement> advertisements;
    private List<Story> stories;
    private List<Photograph> photos;

    public MagazineIssue(int issueNumber, Date date) {
        this.issueNumber = issueNumber;
        this.date = date;
        this.advertisements = new ArrayList<>();
        this.stories = new ArrayList<>();
        this.photos = new ArrayList<>();
    }

    public int getId() {
    return issueNumber;
}

    public void addContent(Object content) {
        if (content instanceof Advertisement) {
            advertisements.add((Advertisement) content);
        } else if (content instanceof Story) {
            stories.add((Story) content);
        } else if (content instanceof Photograph) {
            photos.add((Photograph) content);
        } else {
            System.out.println("Unsupported content type.");
        }
    }

    public void publish() {
        System.out.println("Publishing Magazine Issue " + issueNumber + " on " + date + ".");
        System.out.println("Stories: " + stories.size() + ", Advertisements: " + advertisements.size()
                           + ", Photographs: " + photos.size());
    }

    public void sendToAdvertisers() {
        for (Advertisement ad : advertisements) {
            if (ad.isPaymentConfirmed()) {
                Advertiser advertiser = ad.getAdvertiser();
                if (advertiser != null) {
                    System.out.println("Advertisement ID: " + ad.getId() + " sent to advertiser: " + advertiser.getName());
                } else {
                    System.out.println("Advertisement ID: " + ad.getId() + " has no associated advertiser.");
                }
            }
        }
    }

    public void archiveContent() {
        advertisements.stream()
            .filter(ad -> !ad.isPaymentConfirmed())
            .forEach(ad -> ad.markAsArchived());
        stories.forEach(Story::archive);
        photos.forEach(Photograph::archive);
        System.out.println("Content archived for Magazine Issue " + issueNumber);
    }

    public double calculateTotalAdRevenue() {
        return advertisements.stream()
            .filter(Advertisement::isPaymentConfirmed)
            .mapToDouble(ad -> 100.0) // Example pricing per ad
            .sum();
    }

    // Getters and setters for attributes.
    public int getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public List<Story> getStories() {
        return stories;
    }

    public List<Photograph> getPhotos() {
        return photos;
    }
    
    public boolean isReadyForPublication() {
    return !stories.isEmpty() && !advertisements.isEmpty(); // Example condition
}

    public void review() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
