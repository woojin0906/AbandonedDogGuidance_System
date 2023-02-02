package kr.co.company.mobileproject;
/*
    작성자 : 전우진
    액티비티 : 지도 화면 API
*/
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.net.URL;
import java.util.ArrayList;

public class ShelterApi {
    private static String ServiceKey ="03caf098fbce4f5abdce2addf54d3c88";
    public ShelterApi() {
        try {
            apiParserSearch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList<MapPoint> apiParserSearch() throws Exception {
        URL url = new URL(getURLParam(null));

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp= factory.newPullParser();
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        xpp.setInput(bis, "utf-8");

        String tag = null;
        int event_type = xpp.getEventType();

        ArrayList<MapPoint> mapPoint = new ArrayList<MapPoint>();

        String facility_name = null, longitude=null, latitude=null, phone=null, addr=null;
        boolean bfacility_name=false, blatitude=false, blongitude=false, bphone=false, baddr=false;

        while (event_type != XmlPullParser.END_DOCUMENT) {
            if(event_type == XmlPullParser.START_TAG) {
                tag = xpp.getName();
                if(tag.equals("ENTRPS_NM")) {
                    bfacility_name=true;
                }
                if(tag.equals("REFINE_WGS84_LAT")) {
                    blatitude=true;
                }
                if(tag.equals("REFINE_WGS84_LOGT")) {
                    blongitude=true;
                }
                if(tag.equals("ENTRPS_TELNO")){
                    bphone=true;
                }
                if(tag.equals("REFINE_ROADNM_ADDR")) {
                    baddr=true;
                }
            } else if (event_type == XmlPullParser.TEXT) {
                if(bfacility_name == true) {
                    facility_name = xpp.getText();
                    bfacility_name = false;
                } else if(blatitude == true) {
                    latitude = xpp.getText();
                    blatitude = false;
                } else if(blongitude == true) {
                    longitude = xpp.getText();
                    blongitude = false;
                }
                else if(bphone == true) {
                    phone = xpp.getText();
                    bphone = false;
                }
                else if(baddr == true) {
                    addr = xpp.getText();
                    baddr = false;
                }
            } else if (event_type == XmlPullParser.END_TAG) {
                tag = xpp.getName();
                if(tag.equals("row")) {
                    MapPoint entity = new MapPoint();
                    entity.setName(facility_name);
                    entity.setLatitude(Double.valueOf(latitude));
                    entity.setLongitude(Double.valueOf(longitude));
                    entity.setPhone(phone);
                    entity.setAddr(addr);
                    mapPoint.add(entity);
                    System.out.println(mapPoint.size());
                }
            } event_type = xpp.next();
        } System.out.println(mapPoint.size());
        return  mapPoint;
    }

    private  String getURLParam(String search) {
        // 공공데이터 API 주소, 키
        String url = "https://openapi.gg.go.kr/OrganicAnimalProtectionFacilit?Key=" + ServiceKey + "&Type=xml&plndex=1&pSize=300";
        return  url;
    }

    public static void main(String[] args) {
        new ShelterApi();
    }
}
