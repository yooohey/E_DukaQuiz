package sample.stampLally;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sample.edukaquiz.R;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class StampLallyActivity extends MapActivity {
private static final float RADISU = 50;
/*
 * android:clickable="false"でMapViewのイベントのONOFF
 * ZoomLV22が最大
 * ZoomLV19-22辺りで使用？
 */
	
	public MapController ctrl;
	public MapView map;
	public int weight,height;
	//private Map<Boolean, MyGeoPointOverlay> iconDraws = new HashMap<Boolean, MyGeoPointOverlay>();
	
	
	private boolean[] getStamp = {false,false,false,false,false};
	public ArrayList<GeoPoint> destinationGP = new ArrayList<GeoPoint>();
	public int touchIndex;
	
	public MyLocationOverlay myLocationOverlay;
	//public IconOverlay myOverlay;
	public LocationOverlay locationOverlay;
	public CircleOverlay circleOverlay;
	private MyGeoPointOverlay myOverlay;
	
	//カーソルで移動する距離
	private final int MOVE = 200;
	public static GeoPoint testGP;
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stamp);
        
        Display disp = ((WindowManager)this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        
        this.weight = disp.getWidth();
        this.height = disp.getHeight();
        
        this.map = (MapView)this.findViewById(R.id.mapview);
        this.ctrl = map.getController();
        
        this.destinationGP.add(new GeoPoint((int)(33.641491*1e6),(int)(130.689182*1e6)));
        this.destinationGP.add(new GeoPoint((int)(33.644912*1e6),(int)(130.688476*1e6)));
        this.destinationGP.add(new GeoPoint((int)(33.647413*1e6),(int)(130.689903*1e6)));
        this.destinationGP.add(new GeoPoint((int)(33.644501*1e6),(int)(130.694001*1e6)));
        this.destinationGP.add(new GeoPoint((int)(33.643536*1e6),(int)(130.691459*1e6)));
        
        testGP = new GeoPoint((int)(33.643536*1e6),(int)(130.691459*1e6));
        ctrl.setCenter(this.destinationGP.get(0));
        ctrl.setZoom(18);
        /*
        this.iconDraws.put(true, new Icon(this,this.map));
		this.iconDraws.put(false, new BallonIcon(this, this.map));
		this.myOverlay = this.iconDraws.get(false);
        */
        /*
        //最初に位置情報が確定したときに走る
        this.myOverlay.runOnFirstFix(new Runnable() {

        	@Override
        	public void run() {
        		// TODO 自動生成されたメソッド・スタブ
        		//GPS情報を元に現在地へ移動今回使わない
        		//ctrl.animateTo(myOverlay.getMyLocation());

        		//自位置追跡の無効化　マーカも消える！　
                //myOverlay.disableMyLocation();
        		Log.d("main","main");
        	}
        });
        */
        //コンパスの有効化　凄い勢いで描画が繰り返されるので電池消耗が激しそう
        //this.myOverlay.enableCompass();
        this.locationOverlay = new LocationOverlay(this, this.map);
        //使用する位置情報プロバイダを指定
        this.locationOverlay.onProviderEnabled(LocationManager.GPS_PROVIDER);
        this.circleOverlay = new CircleOverlay(this, map);
        this.myOverlay = new MyGeoPointOverlay(this, map);
        //Overlayの追加と再描画
        this.map.getOverlays().add(this.myOverlay);
        this.map.getOverlays().add(this.circleOverlay);
        this.map.invalidate();
        
        SharedPreferences prefs = getSharedPreferences("stamp", MODE_PRIVATE);
		for(int i=0;i<this.getStamp.length;i++){
			this.getStamp[i] = prefs.getBoolean(String.valueOf(i), false);
		}
		
		ImageView iv = (ImageView)this.findViewById(R.id.stampView1);
		
		
		iv.setOnTouchListener(new ImageViewOnClick());
		
		this.drawStamp();
		
		/*	onCreではViewが描画されていないので取得できない（0になる）
		ImageView iv = (ImageView)this.findViewById(R.id.stampView1);
		int x=iv.getLeft();int y=iv.getTop();
		
		//MapView m = (MapView)findViewById(R.id.mapview);
		//x=m.getLeft(); y=m.getTop();
		
		for(int i=0;i<this.getStamp.length;i++){
			//x += iv.getWidth()/3;
			
			if(i == 3)
				y += iv.getHeight()/2;
			
			this.rectList.add(new Rect(x+iv.getWidth()*i,y,x+iv.getWidth()*(i+1),y+iv.getHeight()));
		}
		*/

    }
    
    public ArrayList<GeoPoint> getDestinationGP(){
    	return this.destinationGP;
    }
    
    @Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO 自動生成されたメソッド・スタブ
		super.onWindowFocusChanged(hasFocus);
		
		
		
		
	
	}
    
    public class ImageViewOnClick implements OnTouchListener{

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO 自動生成されたメソッド・スタブ
			Float x=event.getX();
			Float y=event.getY();
			Log.d("aaa",x+":"+y);
			Rect r;
			ImageView iv = (ImageView)findViewById(R.id.stampView1);
			
			int pointX=0,pointY=0;
			for(int i=0;i<getStamp.length;i++){
				
				if(i==3){
					pointY+=iv.getHeight()/2;
					pointX = 0;
				}
				
				r = new Rect(pointX, pointY, pointX+iv.getWidth()/3 , pointY+iv.getHeight()/2);
				pointX+=iv.getWidth()/3;
				if(r.contains(x.intValue(), y.intValue())){
					ctrl.setCenter(destinationGP.get(i));
					touchIndex = i;
				}
				
			}
			
			/*
			
			for(Rect r:rectList){
				//保持したRectとタッチした点との判定
				if(r.contains(x.intValue(), y.intValue())){
					
					ctrl.setCenter(destinationGP.get(i));
					touchIndex = i;
				}
				i++;
				Log.d("touch",r.toString()+event.getX()+":"+event.getY());
			}

			map.invalidate();
			*/
			return false;
		}
    	
    }

    @Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		
		//OverlayPlus plus = new OverlayPlus(this);
		//this.map.getOverlays().add(plus);
		//自位置の追跡を有効に　表示させるとマーカー付き！
        //this.myOverlay.enableMyLocation();　GPSは別クラスにて実装することに
        this.locationOverlay.enableMyLocation();
        
	}

	public void mapZoom(View v){
    	int zoomLevel =	this.map.getZoomLevel();
    	zoomLevel =	this.map.getZoomLevel();
    	if(v.getId() == R.id.zoomin){
    		if(zoomLevel < 22){
    			zoomLevel++;
    			this.ctrl.setZoom(zoomLevel);
    		}
    	}else{
    		if(zoomLevel > 19){
    			zoomLevel--;
    			this.ctrl.setZoom(zoomLevel);   
    		}
    	}
    	
    	TextView tv = (TextView)this.findViewById(R.id.textView1);
    	tv.setText(""+zoomLevel);
    }
   

	@Override
	protected void onStop() {
		// TODO 自動生成されたメソッド・スタブ
		super.onStop();
		
		//サービス停止を忘れずに 位置情報取得の停止　コンパスの停止
		//this.myOverlay.disableCompass();
		this.locationOverlay.disableMyLocation();
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	public void drawStamp(){		
		
		//MapView m = (MapView)findViewById(R.id.mapview);
		//x=m.getLeft(); y=m.getTop();
				
		Bitmap bmp = Bitmap.createBitmap(this.weight,this.height/3,Bitmap.Config.ARGB_8888);
		ImageView iv = (ImageView)this.findViewById(R.id.stampView1);
				
		Canvas canvas = new Canvas(bmp);
		
        //スタンプのフレームを描画
        Bitmap bmp2 = BitmapFactory.decodeResource(getResources(),R.drawable.stampfream);
        bmp2 = Bitmap.createScaledBitmap(bmp2,this.weight,this.height/3,true);
        canvas.drawBitmap(bmp2, 0, 0, null);
		
        int x=35,y=15;
        //スタンプを5つ描画
        for(int i = 0;i<this.getStamp.length;i++){
        	
        	//状態により描画するスタンプを取得
        	if(this.getStamp[i])
        		bmp2 = BitmapFactory.decodeResource(getResources(),R.drawable.stampon);
        	else
        		bmp2 = BitmapFactory.decodeResource(getResources(),R.drawable.stampoff);
        
        	//描画する範囲　引き延ばされる
        	Rect rect = new Rect(x, y, x+this.height/3/2-30, y+this.height/3/2-30);
        	//Rect rect = new Rect(x, y, x+bmp2.getWidth(), y+bmp2.getHeight());
        	
        	canvas.drawBitmap(bmp2, new Rect(0,0,bmp2.getWidth(),bmp2.getHeight()), rect,null);
        	//Log.d("draw",rect.toString());
        	
        	//iv.getTop()　ImageViewの画面上における座標を返す
        	x+=this.weight/3;
        	if(i==2){
        		x=40;
        		y+=this.height/3/2;
        	}
        		
        }
        iv.setImageBitmap(bmp);
	}

/*
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO 自動生成されたメソッド・スタブ
		
		Float x=event.getX();
		Float y=event.getY();
		Integer i=0;
		
		for(Rect r:rectList){
			//保持したRectとタッチした点との判定
			if(r.contains(x.intValue(), y.intValue())){
				
				this.ctrl.setCenter(this.destinationGP.get(i));
				this.touchIndex = i;
			}
			i++;
			Log.d("touch",r.toString()+event.getX()+":"+event.getY());
		}

		this.drawStamp();
		this.map.invalidate();
		
		return super.onTouchEvent(event);
	}
	*/

	//デバッグ用
	public void arrow(View v){
		int lan = testGP.getLatitudeE6();
		int lon = testGP.getLongitudeE6();
		
		if(v.getId() == R.id.btntop)
			lan += MOVE;
		if(v.getId() == R.id.btnbottom)
			lan -= MOVE;
		if(v.getId() == R.id.btnrigth)
			lon += MOVE;
		if(v.getId() == R.id.btnleft)
			lon -= MOVE;
		testGP =new GeoPoint(lan,lon);
		this.map.invalidate();
		this.collsion(new GeoPoint(lan,lon));
		
		
	}

	public void  setStamp(int i){
		this.getStamp[i] = true;
		this.drawStamp();
		SharedPreferences prefs = getSharedPreferences("stamp", MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(String.valueOf(i), true);
		editor.commit();
	}
	
	public boolean getStamp(int i){
		return this.getStamp[i];
	}
	
	public boolean getStampNow(){
		return this.getStamp[this.touchIndex];
	}
	
	public boolean collsion(GeoPoint gp){
		//スタンプが押されていなかったら現在位置との距離を比較し、結果によりスタンプを押す
		for(int i=0;i<this.getStamp.length;i++){
			if(!this.getStamp[i]){
				
				GeoPoint centerGP = this.destinationGP.get(i);
				float[] result = new float[1];			
				Location.distanceBetween((double)centerGP.getLatitudeE6()/1e6, (double)centerGP.getLongitudeE6()/1e6, (double)gp.getLatitudeE6()/1e6, (double)gp.getLongitudeE6()/1e6,result);
			
				//距離が50m以下ならスタンプをOnにする
				if(result[0] <= RADISU && !this.getStamp(i)){
					Log.d("MyOver","円の中にいます");
					Toast.makeText(this, "スタンプを取得しました", Toast.LENGTH_SHORT).show();
					this.setStamp(i);
					return true;
				}				
			}
		}
		return false;
	}
	
	public Boolean inMap(){
		
		int north,south,west,east;
		//画面の端同士の緯度経度差を取得
		int lat_span = this.map.getLatitudeSpan();
		int lon_span = this.map.getLongitudeSpan();	

		
		//MapViewの東西南北を取得　画面中央の緯度　+　表示緯度/2　＝　MapView上端の緯度
		GeoPoint mapGp = this.map.getMapCenter();//491,010,512,599,63
		
		north = mapGp.getLatitudeE6() + lat_span/2;
		south = mapGp.getLatitudeE6() - lat_span/2;
		west = mapGp.getLongitudeE6() + lon_span/2;
		east = mapGp.getLongitudeE6() - lon_span/2;
		
		
		GeoPoint myGp = LocationOverlay.getMyGeoPoint();
		myGp = testGP;
		
		if(myGp != null){
			if(myGp.getLatitudeE6() <= north)
				if(myGp.getLatitudeE6() >= south)
					if(myGp.getLongitudeE6() <= west)
						if(myGp.getLongitudeE6() >= east){
							Log.d("inMap","true");

							return true;
						}
		}
		
		return false;
	}
	
}
