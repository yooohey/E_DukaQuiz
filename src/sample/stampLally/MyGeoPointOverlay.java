package sample.stampLally;

import java.util.HashMap;
import java.util.Map;

import sample.edukaquiz.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class MyGeoPointOverlay extends Overlay{
	
	public StampLallyActivity stampActivity;
	public MapView map;
	private Bitmap icon;
	
	private SetIcons icontest;
	private Map<Boolean, SetIcons> iconDraws = new HashMap<Boolean, SetIcons>();
	
	public MyGeoPointOverlay(Context context, MapView mapView) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.stampActivity = (StampLallyActivity)context;
		this.map = mapView;
		
		 this.iconDraws.put(true, new PinIcon(this.stampActivity));
		 this.iconDraws.put(false, new BallonIcon(this.stampActivity));
		
	}
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		// TODO 自動生成されたメソッド・スタブ
		super.draw(canvas, mapView, shadow);

		if(!shadow){
			if(!stampActivity.getStampNow()){
				GeoPoint gp = LocationOverlay.getMyGeoPoint();
				gp = StampLallyActivity.testGP;
				if(gp != null){

					Point point = new Point();

					this.icontest = this.iconDraws.get(this.inMap(mapView));
					this.icontest.setIconAndGP(point, mapView);
					this.icon = this.icontest.createIcon();


					canvas.drawBitmap(icon, point.x, point.y, null);
				}

			}else{
				Bitmap guideBmp = BitmapFactory.decodeResource(this.stampActivity.getResources(), R.drawable.kaho);
				Rect src = new Rect(0,0,guideBmp.getWidth(),guideBmp.getHeight());
				Rect dst = new Rect(20,20,canvas.getWidth()-20,mapView.getHeight()-20);

				//Log.d("aaa",String.valueOf(mapView.getHeight()));
				//src=読み込み画像の範囲？ dst= 出力先の範囲
				canvas.drawBitmap(guideBmp, src,dst, null);
			}


			/*
				if(this.inMap(mapView)){

					icon = BitmapFactory.decodeResource(this.stampActivity.getResources(), R.drawable.icon03);
					projection.toPixels(gp, point);
					int halfWidth = icon.getWidth();
					int mapWidth = mapView.getWidth();

					//Map外にiconを描画したときの補正
					if(point.x >= mapWidth-halfWidth )
						point.x = mapWidth-halfWidth;
					if(point.y >= mapView.getHeight()-icon.getHeight()/2)
						point.y = mapView.getHeight()-icon.getHeight()/2;

					canvas.drawBitmap(icon, point.x, point.y, null);

				}
			 */


		}
	}


	public Boolean inMap(MapView map){

		int north,south,west,east;
		//画面の端同士の緯度経度差を取得
		int lat_span = map.getLatitudeSpan();
		int lon_span = map.getLongitudeSpan();	


		//MapViewの東西南北を取得　画面中央の緯度　+　表示緯度/2　＝　MapView上端の緯度
		GeoPoint mapGp = map.getMapCenter();//491,010,512,599,63

		north = mapGp.getLatitudeE6() + lat_span/2;
		south = mapGp.getLatitudeE6() - lat_span/2;
		west = mapGp.getLongitudeE6() + lon_span/2;
		east = mapGp.getLongitudeE6() - lon_span/2;


		GeoPoint myGp = LocationOverlay.getMyGeoPoint();
		myGp = StampLallyActivity.testGP;

		if(myGp != null){
			if(myGp.getLatitudeE6() <= north)
				if(myGp.getLatitudeE6() >= south)
					if(myGp.getLongitudeE6() <= west)
						if(myGp.getLongitudeE6() >= east){
							Log.d("inMap","true");

							return true;
						}
		}
		Log.d("inMap","false");
		return false;
	}

}
