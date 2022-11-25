package kr.co.company.mobileproject;
/*
    작성자 : 전우진
    액티비티 : 댓글 등록 클래스
*/
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
