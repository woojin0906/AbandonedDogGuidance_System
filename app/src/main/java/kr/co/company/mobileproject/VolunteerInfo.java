package kr.co.company.mobileproject;
/*
    작성자 : 전우진
    액티비티 : 자원봉사 firebase 저장 클래스
*/
public class VolunteerInfo {
    private String imageUrl;
    private String name;
    private String title;
    private String companyName;
    private String mainName;
    private String place;
    private String phone;
    private String date;
    private String volunteerDate;
    private String httpAddress;
    private String context;

    public VolunteerInfo() {}

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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVolunteerDate() {
        return volunteerDate;
    }

    public void setVolunteerDate(String volunteerDate) {
        this.volunteerDate = volunteerDate;
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

    public VolunteerInfo(String imageUrl, String name, String title, String companyName, String mainName, String place, String phone, String date, String volunteerDate, String httpAddress, String context) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.title = title;
        this.companyName = companyName;
        this.mainName = mainName;
        this.place = place;
        this.phone = phone;
        this.date = date;
        this.volunteerDate = volunteerDate;
        this.httpAddress = httpAddress;
        this.context = context;

    }


}
