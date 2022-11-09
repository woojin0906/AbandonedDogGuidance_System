package kr.co.company.mobileproject;
/*
    작성자 : 전우진
    액티비티 : 실종동물찾기 firebase 저장 클래스
*/
public class MissingAnimalInfo {

    private String imageUrl;
    private String name;
    private String title;
    private String place;
    private String money;
    private String phone;
    private String date;
    private String context;
    private String pet;

    public MissingAnimalInfo() {} // 생성자 메서드

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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
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

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    public MissingAnimalInfo(String name, String title, String place, String money, String phone, String date, String context, String imageUrl, String pet) {
        this.name = name;
        this.title = title;
        this.place = place;
        this.money = money;
        this.phone = phone;
        this.date = date;
        this.context = context;
        this.imageUrl = imageUrl;
        this.pet = pet;
    }
}
