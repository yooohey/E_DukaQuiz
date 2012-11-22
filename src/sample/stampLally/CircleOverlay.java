package sample.stampLally;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;
/*
 * 円を描画するクラス
 * 画面外だろうと描画するのでパフォーマンスわるし
 */
public class CircleOverlay extends Overlay {

	private StampLallyActivity stampActivity;
	private MapView mapView;
	private final float RADISU = 50;
	
	public CircleOverlay(StampLallyActivity stampActivity,MapView mapView){
		this.stampActivity = stampActivity;
		this.mapView = mapView;
	}
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		// TODO 自動生成されたメソッド・スタブ
		super.draw(canvas, mapView, shadow);
		
		if(!shadow){
			
			for(GeoPoint gp:stampActivity.getDestinationGP()){
				
				Projection projection = this.mapView.getProjection();
				Point point = projection.toPixels(gp, null);
				//Location#distanceBetween(A.Latitude,A.Longitude,B.Latitude,B.Longitude,float[])で二点の距離をfloatに代入してくれる

				//緯度経度でラジアンを求め　cos　何やってるか不明
				double radius = Math.cos(Math.toRadians(gp.getLatitudeE6()/this.stampActivity.destinationGP.get(0).getLongitudeE6()));
				//距離をピクセルに換算？ 50がメートル部分　結果として半径？50mの距離をピクセルで返してくれる？
				float pixel = projection.metersToEquatorPixels(Math.round(this.RADISU/radius));

				Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
				paint.setStyle(Paint.Style.FILL);
				paint.setColor(Color.argb(0x22, 0x33, 0x99, 0xFF));
				canvas.drawCircle(point.x, point.y,pixel, paint);
				
			}
			
		}
	}

}
