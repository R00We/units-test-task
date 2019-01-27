package units.r00we.test_task.data.entity;

import java.util.Date;

public class Issue {
    private String url;
    private String repository_url;
    private String labels_url;
    private String comments_url;
    private String events_url;
    private String html_url;
    private int id;
    private String node_id;
    private int number;
    private String title;
    private String state;
    private boolean locked;
    private String assignee = null;
    private User user;
    private int comments;
    private Date created_at;
    private Date updated_at;
    private Date closed_at;
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

    public int getId() {
        return id;
    }

    public String getNodeId() {
        return node_id;
    }

    public int getNumber() {
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

    public int getComments() {
        return comments;
    }

    public Date getCreatedAt() {
        return created_at;
    }

    public Date getUpdatedAt() {
        return updated_at;
    }

    public Date getClosedAt() {
        return closed_at;
    }

    public String getAuthorAssociation() {
        return author_association;
    }

    public String getBody() {
        return body;
    }
}
