package kr.co.company.mobileproject;
/*
    작성자 : 전우진
    액티비티 : 글 등록시 이미지 저장 클래스
*/
public class Model {

    private String imageUrl;

    Model() {

    }

    public Model(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
