package sample.stampLally;

import sample.edukaquiz.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Projection;

public class PinIcon extends SetIcons{

	public PinIcon(Context context) {
		super(context);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	void setIconAndGP(Point point, MapView mapView) {
		// TODO 自動生成されたメソッド・スタブ
		
		Bitmap icon = BitmapFactory.decodeResource(this.activity.getResources(), R.drawable.icon03);
		Projection projection = mapView.getProjection();
		GeoPoint gp = LocationOverlay.getMyGeoPoint();
		gp = StampLallyActivity.testGP;
		
		projection.toPixels(gp, point);
		int halfWidth = icon.getWidth();
		int mapWidth = mapView.getWidth();
		
		//Map外にiconを描画したときの補正
		if(point.x >= mapWidth-halfWidth )
			point.x = mapWidth-halfWidth;
		if(point.y >= mapView.getHeight()-icon.getHeight()/2)
			point.y = mapView.getHeight()-icon.getHeight()/2;
		
	}

	@Override
	Bitmap createIcon() {
		// TODO 自動生成されたメソッド・スタブ
		
		return BitmapFactory.decodeResource(this.activity.getResources(), R.drawable.icon03);
	}

}
