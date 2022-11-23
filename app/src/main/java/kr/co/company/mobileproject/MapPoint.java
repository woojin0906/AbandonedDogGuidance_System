package kr.co.company.mobileproject;
/*
    작성자 : 전우진
    액티비티 : 지도 클래스
*/
public class MapPoint {
    private String Name;
    private double latitude;
    private double longitude;
    private String phone;
    private String addr;

    public MapPoint() {
        super();
    }

    public MapPoint(String Name, double latitude, double longitude, String phone, String addr) {
        this.Name=Name;
        this.latitude=latitude;
        this.longitude=longitude;
        this.phone=phone;
        this.addr=addr;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}