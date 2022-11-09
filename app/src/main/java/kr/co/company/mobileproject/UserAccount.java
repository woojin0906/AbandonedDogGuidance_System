package kr.co.company.mobileproject;
/*
    작성자 : 전우진
    액티비티 : 사용자 계정 정보 모델 클래스
*/
public class UserAccount {

    private String idToken;      // Firebase Uid (고유 토큰정보)
    private String id;           // 아이디
    private String password;     // 비밀번호
    private String passwordConfirm;
    private String name;
    private String phone;
    private String imgUrl;

    // 클래스가 생성될 때 가자 먼저 호출 되는 곳
    // 빈 생성자를 안만들어주면 데이터베이스 조회할 때 오류 남
    public UserAccount() {}

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public UserAccount(String id, String idToken, String password, String passwordConfirm, String name, String phone, String imgUrl) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.phone = phone;
        this.id = id;
        this.idToken = idToken;
    }
}
