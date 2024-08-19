package ra.models;

import java.io.Serializable;

public class Feedback implements Serializable {
    private String userId;
    private String feedback;

    public Feedback(String userId, String feedback) {
        this.userId = userId;
        this.feedback = feedback;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "userId='" + userId + '\'' +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
