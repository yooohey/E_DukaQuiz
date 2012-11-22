package sample.stampLally;

import sample.edukaquiz.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.location.Location;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class BallonIcon extends SetIcons {

	public Bitmap bmp;
	
	public BallonIcon(Context context) {
		super(context);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	void setIconAndGP( Point point, MapView mapView) {
		// TODO 自動生成されたメソッド・スタブ
		Projection projection = mapView.getProjection();
		GeoPoint gp = LocationOverlay.getMyGeoPoint();
		gp = StampLallyActivity.testGP;
		
		this.bmp = BitmapFactory.decodeResource(this.activity.getResources(), R.drawable.balloon);
		

		projection.toPixels(this.orientesion(mapView), point);
		
		Location l = new Location("l");
		l.setLatitude(gp.getLatitudeE6()/1e6);
		l.setLongitude(gp.getLongitudeE6()/1e6);

		GeoPoint destinationGP = mapView.getMapCenter();
		Location l2 = new Location("l2");
		l2.setLatitude(destinationGP.getLatitudeE6()/1e6);
		l2.setLongitude(destinationGP.getLongitudeE6()/1e6);

		//2地点の方位を求め,角度に適応
		float flo = l.bearingTo(l2);
		Matrix matrix = new Matrix();
		matrix.postRotate(flo + 180);
		matrix.postScale(0.5f, 0.5f);
		
		this.bmp = Bitmap.createBitmap(bmp,0,0,bmp.getWidth(),bmp.getHeight(),matrix,true);

		int balloonWidth = this.bmp.getWidth();

		//Map外にiconを描画したときの補正
		if(point.x >= mapView.getWidth()-balloonWidth )
			point.x = mapView.getWidth()-balloonWidth;
		if(point.y >= mapView.getHeight()-bmp.getHeight())
			point.y = mapView.getHeight()-bmp.getHeight();
		
	}

	@Override
	Bitmap createIcon() {
		// TODO 自動生成されたメソッド・スタブ
		return this.bmp;
	}
	
	private GeoPoint orientesion(MapView mapView){
		

		int north,south,west,east;
		//画面の端同士の緯度経度差を取得
		int lat_span = mapView.getLatitudeSpan();
		int lon_span = mapView.getLongitudeSpan();	

		
		//MapViewの東西南北を取得　画面中央の緯度　+　表示緯度/2　＝　MapView上端の緯度
		GeoPoint mapGp = mapView.getMapCenter();//491,010,512,599,63
		
		north = mapGp.getLatitudeE6() + lat_span/2;
		south = mapGp.getLatitudeE6() - lat_span/2;
		east = mapGp.getLongitudeE6() + lon_span/2;
		west = mapGp.getLongitudeE6() - lon_span/2;
		
		GeoPoint myGP = LocationOverlay.getMyGeoPoint();
		myGP = StampLallyActivity.testGP;
		
		if(myGP.getLatitudeE6() > north){
			myGP = new GeoPoint(north,myGP.getLongitudeE6());
			Log.d("","北");
		}
		else if(myGP.getLatitudeE6() < south){
			myGP = new GeoPoint(south,myGP.getLongitudeE6());
			Log.d("","南");
		}
		
		if(myGP.getLongitudeE6() > east){
			myGP = new GeoPoint(myGP.getLatitudeE6(),east);
			Log.d("","東");
			Log.d("",myGP.toString()+":"+mapGp.toString());
		}
		else if(myGP.getLongitudeE6() < west){
			myGP = new GeoPoint(myGP.getLatitudeE6(),west);
			Log.d("","西");
		}
		
		return myGP;
	}

}
