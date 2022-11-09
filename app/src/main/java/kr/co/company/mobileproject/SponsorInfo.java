package kr.co.company.mobileproject;
/*
    작성자 : 전우진
    액티비티 : 후원요청 firebase 저장 클래스
*/
public class SponsorInfo {
    private String imageUrl;
    private String name;
    private String title;
    private String companyName;
    private String mainName;
    private String phone;
    private String httpAddress;
    private String context;
    private String place;

    public SponsorInfo() {}

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHttpAddress() {
        return httpAddress;
    }

    public void setHttpAddress(String httpAddress) {
        this.httpAddress = httpAddress;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public SponsorInfo(String imageUrl, String name, String title, String companyName, String mainName, String phone, String httpAddress, String context, String place) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.title = title;
        this.companyName = companyName;
        this.mainName = mainName;
        this.phone = phone;
        this.httpAddress = httpAddress;
        this.context = context;
        this.place = place;
    }
}
