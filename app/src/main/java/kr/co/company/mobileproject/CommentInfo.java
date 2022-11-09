package kr.co.company.mobileproject;

public class CommentInfo {
    private String id;
    private String comment;

    public CommentInfo() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CommentInfo(String comment, String id) {
        this.comment = comment;
        this.id = id;
    }
}
