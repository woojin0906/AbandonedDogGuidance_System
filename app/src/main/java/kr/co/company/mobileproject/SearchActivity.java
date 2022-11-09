package kr.co.company.mobileproject;
/*
    작성자 : 전우진
    액티비티 : 지도 화면
*/
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;


import android.app.FragmentManager;
import android.os.Bundle;
import android.os.StrictMode;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Map;

public class SearchActivity extends FragmentActivity {

    private GoogleMap mMap;



//    //implements OnMapReadyCallback
////    private FragmentManager fragmentManager;
////    private MapFragment mapFragment;
////
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//        setContentView(R.layout.activity_search);
//        setUpMapIfNeeded();
////        fragmentManager = getFragmentManager();
////        mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.googleMap);
////        mapFragment.getMapAsync(this);
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        setUpMapIfNeeded();
//    }
//
//    private void setUpMapIfNeeded() {
//        // Do a null check to confirm that we have not already instantiated the map.
//        if (mMap == null) {
//            // Try to obtain the map from the SupportMapFragment.
//            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap).getMap();
//            // Check if we were successful in obtaining the map.
//            if (mMap != null) {
//                setUpMap();
//            }
//        }
//    }
//
//    private void setUpMap() {
//
//        MapParser parser = new MapParser();
//        ArrayList<MapDTO> list = null;
//        try {
//            list =parser.apiParserSearch();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        for(MapDTO entity : list) {
//            MarkerOptions options = new MarkerOptions();
//            options.position(new LatLng(entity.getREFINE_WGS84_LOGT(), entity.getREFINE_WGS84_LAT()));
//            options.title(entity.getENTRPS_NM());
//           // options.icon(BitmapDescriptorFactory.fromResource(R.drawable.pill_small));
//
//            mMap.addMarker(options);
//        }
//
//    }
//}
//////
//////    @Override
//////    public void onMapReady(@NonNull GoogleMap googleMap) {  // 구글 맵 준비되면 호출하는 곳
//////        LatLng location = new LatLng(37.4483,126.6573); // 위도 경도 값(인하공전 위치)
//////        MarkerOptions markerOptions = new MarkerOptions();
//////        markerOptions.title("인하공업전문대학교");
//////        markerOptions.snippet("학교");
//////        markerOptions.position(location);
//////        googleMap.addMarker(markerOptions);
//////
//////       // googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16)); // 16 : 줌 크기, map 생성시 켜지는 화면이 딱딱하게 이동함
//////        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 16)); // map 생성시 켜지는 화면이 부드럽게 이동함
    }


