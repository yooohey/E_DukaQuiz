package sample.stampLally;

import android.content.Context;
import android.location.Location;
import android.os.Vibrator;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

/*
 * 位置情報を取得するクラス
 * geterでGPを返す
 * MyLocationOverlayのonLocationChangedを使った方が精度が良いらしい？
 */
public class LocationOverlay extends MyLocationOverlay {

	private static GeoPoint myGP=null;
	private StampLallyActivity activity;
	
	public LocationOverlay(Context context, MapView mapView) {
		super(context, mapView);
		// TODO 自動生成されたコンストラクター・スタブ
		this.activity = (StampLallyActivity)context;
	}

	@Override
	public synchronized void onLocationChanged(Location location) {
		// TODO 自動生成されたメソッド・スタブ
		super.onLocationChanged(location);

		myGP = new GeoPoint((int)(location.getLatitude()*1e6), (int)(location.getLongitude()*1e6));
		this.activity.collsion(myGP);
		/*
		String gpStr = new GeoPoint((int)(location.getLatitude()*1e6), (int)(location.getLongitude()*1e6)).toString();
		SdcardToWrite sd = new SdcardToWrite(gpStr);
		sd.outFile();
		*/

	}
	
	public static GeoPoint getMyGeoPoint(){
		return myGP;
	}

}
