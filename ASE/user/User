package user;

/**
 * Abstract superclass representing a generic user in the system.
 */
public abstract class User {
    private int id;
    private String username;
    private String password;
    private String role;

    /**
     * Constructor to initialize a user.
     *
     * @param id       User ID.
     * @param username Username for login.
     * @param password Password for login.
     * @param role     Role of the user in the system.
     */
    public User(int id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Authenticates the user with the provided credentials.
     *
     * @param username Input username.
     * @param password Input password.
     * @return True if authentication is successful, false otherwise.
     */
    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    // Getters and setters for user attributes

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
