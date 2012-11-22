package sample.stampLally;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;

import com.google.android.maps.MapView;

public abstract class SetIcons {

	public StampLallyActivity activity;
	public SetIcons(Context context){
		
		this.activity = (StampLallyActivity)context;
	}
	abstract void setIconAndGP( Point point,MapView mapView);
	abstract Bitmap createIcon();
}
