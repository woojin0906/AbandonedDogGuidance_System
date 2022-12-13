package kr.co.company.mobileproject;
/*
    작성자 : 전우진
    액티비티 : 지도 화면에 polyline 표시하는 클래스
*/
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

    public class FindPetPathTask extends AsyncTask<TMapPoint, Void, Double>
    {
        Context context;
        TMapPolyLine tMapPolyLine;
        TMapView tMapView;

        public FindPetPathTask(Context context, TMapView tMapView)
        {
            super();
            this.context = context;
            this.tMapView = tMapView;
        }

        @Override
        protected void onPostExecute(Double distance)
        {
            super.onPreExecute();
        }

        @Override
        protected Double doInBackground(TMapPoint... tMapPoints)
        {
            TMapData tMapData = new TMapData();

            try
            {
                tMapPolyLine = tMapData.findPathData(tMapPoints[0], tMapPoints[1]);
                tMapPolyLine.setLineColor(Color.BLUE);
                tMapPolyLine.setLineWidth(2);

                tMapView.addTMapPolyLine("Line123", tMapPolyLine);
            }
            catch( Exception e )
            {
                e.printStackTrace();
            }

            return tMapPolyLine.getDistance();
        }
    }

