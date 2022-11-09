package kr.co.company.mobileproject;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

public class MapParser {

    public final static String URL = " https://openapi.gg.go.kr/OrganicAnimalProtectionFacilit";
    public final static String KEY = "구글API키";

    public MapParser() {
        try{
            apiParserSearch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<MapDTO>  apiParserSearch() throws Exception {
            URL url = new URL(getURLParam(null));

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            BufferedInputStream bis = new BufferedInputStream(url.openStream());
            xpp.setInput(bis, "utf-8");

            String tag = null;
            int event_type = xpp.getEventType();

            ArrayList<MapDTO> list = new ArrayList<MapDTO>();

            String xpos = null, ypos= null,name=null;
            while (event_type != XmlPullParser.END_DOCUMENT) {
                if (event_type == XmlPullParser.START_TAG) {
                    tag = xpp.getName();
                } else if (event_type == XmlPullParser.TEXT) {
                    /**
                     * 약국의 주소만 가져와본다.
                     */
                    if(tag.equals("XPos")){
                        xpos = xpp.getText();
                        System.out.println(xpos);
                    }else if(tag.equals("YPos")){
                        ypos = xpp.getText();
                    }else if(tag.equals("yadmNm")){
                        name = xpp.getText();
                    }
                } else if (event_type == XmlPullParser.END_TAG) {
                    tag = xpp.getName();
                    if (tag.equals("item")) {
//                        MapDTO entity = new MapDTO();
//                        entity.setREFINE_WGS84_LAT(Double.valueOf(xpos));
//                        entity.getREFINE_WGS84_LOGT(Double.valueOf(ypos));
//                        entity.getENTRPS_NM(name);
//
//                        list.add(entity);
                    }
                }
                event_type = xpp.next();
            }
            System.out.println(list.size());

            return list;
        }




        private String getURLParam(String search){
            String url = URL+"?ServiceKey="+KEY;
            if(search != null){
                url = url+"&yadmNm"+search;
            }
            return url;
        }

        public static void main(String[] args) {
            // TODO Auto-generated method stub
            new MapParser();
        }

}
