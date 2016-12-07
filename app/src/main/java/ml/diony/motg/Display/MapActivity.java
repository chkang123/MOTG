package ml.diony.motg.Display;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import ml.diony.motg.R;

//식당의 정보를 출력하는 화면에서 지도보기 버튼을 사용자가 누ㄹ면 식당의 죄표정보를 받아
//해당 식당의 위치를 Daum지도 상에 marker와 함께 보여준다.
//이는 Daum지도 api를 이용하였다.
public class MapActivity extends AppCompatActivity {

    float X, Y;
    //지도가 출력될때 아래의 코드들이 실행된다.
    MapView.MapViewEventListener mapViewEventListener = new MapView.MapViewEventListener() {
        @Override
        public void onMapViewInitialized(MapView mapView) {
            //지도의 확대정도를 설정한다.
            mapView.setZoomLevel(0, true);
            //식당이 화면의 중심에 오도록 설정한다.
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(X, Y), true);

            //marker를 생성하여 식당의 위치를 가리키도록 한다.
            MapPOIItem marker = new MapPOIItem();
            marker.setItemName("Default Marker");
            marker.setTag(0);
            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(X, Y));
            marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

            mapView.addPOIItem(marker);
        }

        @Override
        public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

        }

        @Override
        public void onMapViewZoomLevelChanged(MapView mapView, int i) {

        }

        @Override
        public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

        }

        @Override
        public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

        }

        @Override
        public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

        }

        @Override
        public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

        }

        @Override
        public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

        }

        @Override
        public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Intent I = getIntent();
        //위도와 경도를 받아온다.
        X = I.getExtras().getFloat("COORDX");
        Y = I.getExtras().getFloat("COORDY");
        Log.i("MAPMAPMAP", "COORDX = " + X + ", COORDY = " + Y);

        //Daum지도를 띄운다.
        MapView mapView = new MapView(this);
        mapView.setDaumMapApiKey("95196575995654fba45336d25f83249d");
        mapView.setMapViewEventListener(mapViewEventListener);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

    }

}
