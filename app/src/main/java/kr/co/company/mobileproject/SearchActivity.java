package kr.co.company.mobileproject;
/*
    작성자 : 전우진
    액티비티 : 지도 화면
*/

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import com.google.firebase.database.FirebaseDatabase;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;
import com.skt.Tmap.poi_item.TMapPOIItem;

public class SearchActivity extends AppCompatActivity implements TMapGpsManager.onLocationChangedCallback {

    private TMapView tMapView = null;
    private TMapGpsManager tmapgps = null;
    private static String TMapAPIKey = "l7xx72602e7783854a2fa17f0707466b1b45";  // TMap API 키
    private Context mContext = null;
    private TMapPoint tpoint = null;

    private TextView btnSetTrackingMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mContext = this;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // T Map View
        tMapView = new TMapView(this);

        // API Key
        tMapView.setSKTMapApiKey(TMapAPIKey);
        setUpMap();

        tMapView.setCompassMode(true);

        tMapView.setZoomLevel(15);
        tMapView.setIconVisibility(true);
        tMapView.setMapType(TMapView.MAPTYPE_STANDARD);
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN);

        // Linear Layout
        LinearLayout linearLayoutTmap = (LinearLayout) findViewById(R.id.linearLayoutRmap);
        linearLayoutTmap.addView(tMapView);

        tmapgps = new TMapGpsManager(this);
        tmapgps.setMinTime(1000);              // 일정 시간마다 리셋
        tmapgps.setMinDistance(1);             // 일정 거리마다 리셋
        tmapgps.setProvider(tmapgps.NETWORK_PROVIDER); // 네트워크에 맞춰 현재 위치 표시  -> 디바이스로 실행할 때 사용
        //tmapgps.setProvider(tmapgps.GPS_PROVIDER);       //GPS  ->  애뮬레이터로 실행할 때 사용

        // 화면중심을 단말의 현재위치로 이동
        tMapView.setTrackingMode(true);
        tMapView.setSightVisible(true);

        tmapgps.OpenGps();

        // 현재위치로 돌아가는 버튼(TextView)
        btnSetTrackingMode = (TextView) findViewById(R.id.BtnSetTrackingMode);
        btnSetTrackingMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.BtnSetTrackingMode:
                        Toast.makeText(SearchActivity.this, "현위치로 돌아갑니다", Toast.LENGTH_SHORT).show();

                        // 화면중심을 단말의 현재위치로 이동
                        tMapView.setTrackingMode(true);
                        tMapView.setSightVisible(true);
                        break;
                }
            }
        });
    }

    private void setUpMap() {
        ShelterApi parser = new ShelterApi();
        ArrayList<MapPoint> mapPoint = new ArrayList<MapPoint>();
        try {
            mapPoint = parser.apiParserSearch();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < mapPoint.size(); i++) {
            for (MapPoint entity : mapPoint) {
                TMapPoint point = new TMapPoint(mapPoint.get(i).getLatitude(), mapPoint.get(i).getLongitude());
                TMapMarkerItem markerItem1 = new TMapMarkerItem();
                Bitmap bitmap = null;
                bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.poi_dot); // 마커 아이콘 변경

                markerItem1.setPosition(0.5f, 1.0f);
                markerItem1.setIcon(bitmap);

                // 풍선뷰 안의 항목에 글을 지정
                markerItem1.setCalloutTitle(mapPoint.get(i).getName()); // 보호센터 이름
                markerItem1.setCalloutSubTitle(mapPoint.get(i).getPhone());  // 보호센터 전화번호
                markerItem1.setCanShowCallout(true);
                markerItem1.setAutoCalloutVisible(true);

                markerItem1.setTMapPoint(point);
                markerItem1.setName(entity.getName());
                tMapView.setCenterPoint(mapPoint.get(i).getLongitude(), mapPoint.get(i).getLatitude()); // 위도 경도
                tMapView.addMarkerItem("markerItem1" + i, markerItem1);

            }
        }

    }

    @Override
    public void onLocationChange(Location location) {
        tMapView.setLocationPoint(location.getLongitude(), location.getLatitude());
        tMapView.setCenterPoint(location.getLongitude(), location.getLatitude());
    }
}