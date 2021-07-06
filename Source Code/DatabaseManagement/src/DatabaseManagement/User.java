package DatabaseManagement;

public class User {
    private String id;
    private int points;
    private String username;

    public User(String id, int points, String username) {
        setId(id);
        setPoints(points);
        setUsername(username);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        try {
            if(id != null && id.length() <= 5) {
                this.id = id;
                return;
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid username input!");
        }
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        try {
            if(points >= 0) {
                this.points = points;
                return;
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid points input!");
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        try {
            if(username != null) {
                this.username = username;
                return;
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid username input!");
        }
    }

    public void updatePoints(int points) {
        this.points += points;
        TriviaDataManager.getInstance().updateUserPoints(id, this.points);
    }
}
