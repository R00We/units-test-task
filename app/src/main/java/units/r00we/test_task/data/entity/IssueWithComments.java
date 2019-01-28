package units.r00we.test_task.data.entity;

import java.util.List;

import units.r00we.test_task.data.entity.Comment;
import units.r00we.test_task.data.entity.Issue;

public class IssueWithComments {
    private final Issue issue;
    private final List<Comment> comment;

    public IssueWithComments(Issue issue, List<Comment> comment) {
        this.issue = issue;
        this.comment = comment;
    }

    public Issue getIssue() {
        return issue;
    }

    public List<Comment> getComment() {
        return comment;
    }
}
