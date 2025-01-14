package contributors;

public class Story {
    private int storyId;
    private String title;
    private String content;
    private Contributor author; // Reference to the author (Journalist or Contributor)
    private boolean isArchived;

    /**
     * Constructor to create a Story object.
     *
     * @param storyId Unique ID of the story.
     * @param title   Title of the story.
     * @param content Content of the story.
     * @param author  Author (Contributor) who wrote the story.
     */
    public Story(int storyId, String title, String content, Contributor author) {
        this.storyId = storyId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.isArchived = false;
    }

    // Method to archive the story
    public void archive() {
        isArchived = true;
        System.out.println("Story titled \"" + title + "\" has been archived.");
    }

    // Getters
    public int getStoryId() {
        return storyId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Contributor getAuthor() {
        return author;
    }

    public boolean isArchived() {
        return isArchived;
    }
    
    // Calculates cost based on word count
    public double calculateCost() {
        int wordCount = content.split("\\s+").length; // Calculate word count
        return wordCount * 0.5; // Example: $0.50 per word
    }

    // toString method for better readability
    @Override
    public String toString() {
        return "Story{" +
               "storyId=" + storyId +
               ", title='" + title + '\'' +
               ", content='" + content + '\'' +
               ", author=" + author.getName() +
               ", isArchived=" + isArchived +
               '}';
    }
}
