package units.r00we.test_task.network;

public class Issue {
    private String url;
    private String repository_url;
    private String labels_url;
    private String comments_url;
    private String events_url;
    private String html_url;
    private float id;
    private String node_id;
    private float number;
    private String title;
    private String state;
    private boolean locked;
    private String assignee = null;
    private User user;
    private float comments;
    private String created_at;
    private String updated_at;
    private String closed_at;
    private String author_association;
    private String body;

    public String getUrl() {
        return url;
    }

    public String getRepositoryUrl() {
        return repository_url;
    }

    public String getLabelsUrl() {
        return labels_url;
    }

    public String getCommentsUrl() {
        return comments_url;
    }

    public String getEventsUrl() {
        return events_url;
    }

    public String getHtmlUrl() {
        return html_url;
    }

    public float getId() {
        return id;
    }

    public String getNodeId() {
        return node_id;
    }

    public float getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public String getState() {
        return state;
    }

    public boolean getLocked() {
        return locked;
    }

    public String getAssignee() {
        return assignee;
    }

    public User getUser() {
        return user;
    }

    public float getComments() {
        return comments;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public String getUpdatedAt() {
        return updated_at;
    }

    public String getClosedAt() {
        return closed_at;
    }

    public String getAuthorAssociation() {
        return author_association;
    }

    public String getBody() {
        return body;
    }
}
