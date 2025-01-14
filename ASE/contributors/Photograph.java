package contributors;

import java.util.Objects;

public class Photograph {
    private int photoId;
    private String caption;
    private Photographer photographer; // Reference to the Photographer
    private boolean isArchived;
    private String resolution; // e.g., "1920x1080"

    /**
     * Constructor to create a Photograph object.
     *
     * @param photoId     Unique ID of the photograph.
     * @param caption     Caption or description of the photograph.
     * @param photographer Photographer who took the photograph.
     */
    public Photograph(int photoId, String caption, Photographer photographer, String resolution) {
        this.photoId = photoId;
        this.caption = caption;
        this.photographer = photographer;
        this.isArchived = false;
        this.resolution = resolution;
    }

    // Method to archive the photograph
    public void archive() {
        isArchived = true;
        System.out.println("Photograph captioned \"" + caption + "\" has been archived.");
    }

    // Getters
    public int getPhotoId() {
        return photoId;
    }

    public String getCaption() {
        return caption;
    }

    public Photographer getPhotographer() {
        return photographer;
    }

    public boolean isArchived() {
        return isArchived;
    }

    // Equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photograph that = (Photograph) o;
        return photoId == that.photoId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(photoId);
    }

    // Calculates cost based on resolution
    public double calculateCost() {
        String[] dimensions = resolution.split("x");
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);
        return (width * height) * 0.0001; // Example: $0.0001 per pixel
    }
    
    // toString method for better readability
    @Override
    public String toString() {
        return "Photograph{" +
               "photoId=" + photoId +
               ", caption='" + caption + '\'' +
               ", photographer=" + photographer.getName() +
               ", isArchived=" + isArchived +
               '}';
    }
}
