package sample.edukaquiz;

import java.util.Timer;
import java.util.TimerTask;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources.Theme;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends Activity{
	
	private Handler timerHandler = new Handler();
	private Handler deleteHandler = new Handler();
	public static OAuthAuthorization myOauth;
	public static RequestToken requestToken;
	private final String tweet = "クイズアプリで"+Quiz.getPoint()+"ポイントを獲得！"+" #ictTestQuiz";
	private Timer timeTask;
	private Bitmap graph;
	
	static final String CALLBACK_URL = "http://twitter.com/";
	static final String CONSUMER_ID = "CN1krVYeragTQdJYEm4BA";
	static final String CONSUMER_SECRET = "1DESgiLUiUnvfMnoxO90XZPExIhiJt1cS5IAFbI1w";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result); 
        
        
        //getStringExtra 渡された追加文字列を受け取る　getStringExtra("keyword") 返り値が渡した文字列ぽい
        //staticでも解決できることが判明そっと見送る
        
        //String data = i.getStringExtra("a_count");

       
        TextView tv = (TextView)findViewById(R.id.textView1);
        tv.setText(Quiz.getPoint()+"POINT獲得！\nあなたの正解数は"+Quiz.getAnswerCount()+"問です！");
        //this.timerHandler.postDelayed(CallbackTimer, 10);
        
        
    }
	
	
	@Override
	protected void onPause() {
		// TODO 自動生成されたメソッド・スタブ
		super.onPause();
		if(this.timeTask != null)
			this.timeTask.cancel();
	}


	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO 自動生成されたメソッド・スタブ
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus)
			this.setTimer();
		
	}


	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		
	}
	
	private void setTimer(){
		
		if(this.timeTask != null)
			this.timeTask.cancel();
		
		
		final Handler handler = new Handler();
		this.timeTask = new Timer();
		this.timeTask.schedule(new TimerTask(){
			private double i=0;
			private long total,newTime,oldTime,startTime = System.currentTimeMillis();
			private final double move = (double)60/2000;
			private double moveto;
			private int alpha;
			
			
			@Override
			public void run() {
				// TODO 自動生成されたメソッド・スタブ
				newTime = System.currentTimeMillis();
				if( newTime - startTime >= 1940){
					//count=17-33 理想33?
					Log.d("total",String.valueOf(move));
					this.cancel();
				}

				//FPS係数
				if(oldTime != 0){
					i = ((double)(newTime - oldTime)/60);
					total+= newTime - oldTime;
				}
				else
					i = 1;
				
				if(i < 1){
					Log.d("i<1","i<1を検出！！！");
				}
				
				moveto = moveto + (move * i);

				Log.d("move",String.valueOf(System.currentTimeMillis()));
				oldTime = newTime;

				//final Bitmap bmp = graphDraw(((double)(newTime - startTime))/2000*i);
				final Bitmap bmp = graphDraw(moveto);
				handler.post(new Runnable() {

					@Override
					public void run() {
						// TODO 自動生成されたメソッド・スタブ

						TextView tv = (TextView)findViewById(R.id.textView1);
						ImageView iv = (ImageView)findViewById(R.id.resultGraph);
						
						alpha = (int)(255*moveto) >= 255?255:(int)(255*moveto);
						tv.setTextColor(Color.argb(alpha, 0, 0, 0));
						iv.setImageBitmap(bmp);

					}
				});
			}

		},0,60);
		

	}


	private Runnable CallbackTimer = new Runnable() {
		
		private double i=0;
		private long newTime,oldTime,startTime = System.currentTimeMillis();
		private int arpah=0;
		
		@Override
		public void run() {
			// TODO 自動生成されたメソッド・スタブ
			
			timerHandler.postDelayed(this,60);
			
			newTime = System.currentTimeMillis();
			
			if( newTime - startTime >= 1940){
				deleteHandler.postDelayed(CallbackDelete, 0);
			}
			
			//FPS係数
			if(oldTime != 0)
				i = (newTime - oldTime)/60;
			else
				i = 1;
			
			if(i < 1)
				i=1;
			Log.d("",String.valueOf((newTime - startTime)));
			Log.d("",String.valueOf((int)((double)(newTime - startTime)/2000*i*255)));
			
			
			oldTime = newTime;
			TextView tv = (TextView)findViewById(R.id.textView1);
			
			
			arpah = (int)(arpah + 2*i);
			
			tv.setTextColor(Color.argb((int)((double)(newTime - startTime)/2000*i*255), 0, 0, 0));
			
			ImageView iv = (ImageView)findViewById(R.id.resultGraph);
			iv.setImageBitmap(graphDraw(((double)(newTime - startTime))/2000*i));			
		}
	};
	
	private Runnable CallbackDelete = new Runnable() {
        public void run() {
            /* コールバックを削除して周期処理を停止 */
            timerHandler.removeCallbacks(CallbackTimer);
        }
    };
	
	public void onClickBtn(View view){
		
		//遷移先のアクティビティが稼動済みの場合それより上にあるアクティビティをキルする
		//要するに結果画面＞バックキー＞問題の画面に戻るのを防ぐ　finish()してるのでいらなそう
		//i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		//良くワカラン調べることi.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		
		if(view.getId() == R.id.result_toMenu){
			Intent i=new Intent(this,SelectMenuActivity.class);
			this.startActivity(i);
			this.deleteHandler.postDelayed(CallbackDelete, 0);
			this.finish();
		}else{
			Intent i = new Intent(this,OfflineQuizActivity.class);
			this.startActivity(i);
			this.deleteHandler.postDelayed(CallbackDelete, 0);
			this.finish();
		}
	}

	public void tweet(View v){
		
		SharedPreferences pref = getSharedPreferences("twitter", MODE_PRIVATE);

		String oauthToken  = pref.getString("oauth_token", "");
		String oauthTokenSecret = pref.getString("oauth_token_secret", "");

		//Log.d("oauthTOken",oauthToken);
		if(oauthToken.equals("")){
			this.logIn();

		}else{
			Log.d("token",oauthToken+":"+oauthTokenSecret);

			ConfigurationBuilder builder = new ConfigurationBuilder();
			builder.setOAuthConsumerKey(CONSUMER_ID);
			builder.setOAuthConsumerSecret(CONSUMER_SECRET);
			builder.setOAuthAccessToken(oauthToken);
			builder.setOAuthAccessTokenSecret(oauthTokenSecret);
			Configuration config = builder.build();

			Twitter twitter = new TwitterFactory(config).getInstance();
			Tweet tweet = new Tweet(this);
			tweet.execute(twitter);
			//twitter.updateStatus(this.tweet);
			//Toast.makeText(this, "スコアをつぶやきました", Toast.LENGTH_SHORT).show();
			
				//Toast.makeText(this, "つぶやけませんでした",Toast.LENGTH_SHORT).show();
			
		}
	}
	
	class Tweet extends AsyncTask<Twitter,Integer,Boolean>
	implements OnCancelListener {

		ProgressDialog dialog;
		Context context;
		public Tweet(Context context){
			this.context = context;
		}
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			
			this.dialog = new ProgressDialog(this.context);
			this.dialog.setMessage("しばらくお待ちください");
			this.dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			this.dialog.setCancelable(true);
			this.dialog.setOnCancelListener(this);
			this.dialog.show();
			
		}
		
		@Override
		protected Boolean doInBackground(Twitter... params) {
			// TODO 自動生成されたメソッド・スタブ
			
			try {
				
				params[0].updateStatus(tweet);
				return true;
				
			} catch (TwitterException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO 自動生成されたメソッド・スタブ
			super.onPostExecute(result);
			dialog.dismiss();
			if(result)
				Toast.makeText(this.context, "スコアをつぶやきました", Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(this.context, "つぶやけませんでした",Toast.LENGTH_SHORT).show();
		}
		
		@Override
		public void onCancel(DialogInterface arg0) {
			// TODO 自動生成されたメソッド・スタブ
			
			this.cancel(true);
			
		}
		
	}
	
	public void logIn(){
    	
    	ConfigurationBuilder builder = new ConfigurationBuilder();  
        builder.setOAuthConsumerKey(CONSUMER_ID);
        builder.setOAuthConsumerSecret(CONSUMER_SECRET);  
        Configuration configuration = builder.build();
        
        myOauth = new OAuthAuthorization(configuration);
        myOauth.setOAuthAccessToken(null);
        try {
        	requestToken = myOauth.getOAuthRequestToken(CALLBACK_URL);
        } catch (TwitterException e) {
        	// TODO 自動生成された catch ブロック
        	e.printStackTrace();
        }
        Intent intent = new Intent(this,OAuthActivity.class);
        intent.putExtra("auth_url", requestToken.getAuthorizationURL());
        this.startActivityForResult(intent, 0);
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// TODO 自動生成されたメソッド・スタブ
		super.onActivityResult(requestCode, resultCode, intent);
		
		//intentがnullの場合もあるので注意！
		if(requestCode == 0 && intent !=null){
    		Log.d("main","main");
    		try {
    			AccessToken accessToken = myOauth.getOAuthAccessToken(requestToken,intent.getExtras().getString("oauth_verifier"));
    			SharedPreferences pref = getSharedPreferences("twitter", MODE_PRIVATE);
    			SharedPreferences.Editor editor = pref.edit();
    			editor.putString("oauth_token", accessToken.getToken());
    			editor.putString("oauth_token_secret", accessToken.getTokenSecret());
    			editor.putString("status", "available");
    			editor.commit();

    			Log.d("result",accessToken.getToken()+":"+accessToken.getTokenSecret());

    		} catch (TwitterException e) {
    			// TODO 自動生成された catch ブロック
    			e.printStackTrace();
    		}
    	}
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO 自動生成されたメソッド・スタブ
    	
    	switch (item.getItemId()) {
		case R.id.menu_settings:
			this.clearPref();
			break;
    	}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO 自動生成されたメソッド・スタブ
		
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.activity_main, menu);
		
		return super.onCreateOptionsMenu(menu);
		
	}
	
	public void clearPref(){
    	
    	SharedPreferences pref = getSharedPreferences("twitter", MODE_PRIVATE);
    	Editor editor = pref.edit();
    	editor.clear().commit();
    	
    }
	
	private Bitmap graphDraw(double time){
		
		ImageView iv = (ImageView)this.findViewById(R.id.resultGraph);
		int width = iv.getWidth();
		int height = iv.getHeight();
		
		int centerX = width/2;
		int centerY = height/2;
		
		int shortSpan;
		if(width >= height)
			shortSpan = height/2;
		else
			shortSpan = width/2;
		
		
			
		Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bmp);
		if(graph == null){
			Bitmap graphBase =  BitmapFactory.decodeResource(getResources(),R.drawable.quizresult3);
			graph = Bitmap.createScaledBitmap(graphBase, shortSpan*2, shortSpan*2, false);
		}
		canvas.drawBitmap(graph,(width-graph.getWidth())/2,(height-graph.getHeight())/2,null);
		
		Paint paint = new Paint();
		paint.setColor(Color.argb(96,216,181 ,63));
		paint.setStyle(Paint.Style.FILL);
		
		
		shortSpan = (int) (shortSpan *0.7*time);
		//2*Math.PI(2*π) * 角度/360;ラジアン変換
		//半径 * Math.cos((double)(ラジアン*-90/360)));xの座標 -90するのは、画面上では真上が0度のため
		//半径 * Math.sin((double)(ラジアン*-90/360)));xの座標
		Path path = new Path();
		int x,y;
		int totalQ = OfflineQuizActivity.getToatlQ();
		//正解数
		double multiplier = (double)Quiz.answerCount/totalQ+0.1;
		Log.d("seikai",String.valueOf(multiplier));
		x = centerX+(int)(shortSpan *multiplier*Math.cos((double)2*Math.PI*-90/360));
		y = centerY+(int)(shortSpan *multiplier* Math.sin((double)2*Math.PI*-90/360));		
		path.moveTo(x, y);
		
		//獲得ポイント
		multiplier = (double)Quiz.getPoint()/(totalQ*10000)+0.01;
		Log.d("point",String.valueOf(multiplier));
		x = centerX+(int)(shortSpan * multiplier *Math.cos((double)2*Math.PI*(120-90)/360));
		y = centerY+(int)(shortSpan * multiplier * Math.sin((double)2*Math.PI*(120-90)/360));
		path.lineTo(x, y);
		
		//トータルクリアタイム
		multiplier = (double)((totalQ*10000) - Quiz.getTotalTime()) / (totalQ*10000)+0.1;
		Log.d("total",String.valueOf(multiplier));
		x = centerX+(int)(shortSpan* multiplier *Math.cos((double)2*Math.PI*(240-90)/360));
		y = centerY+(int)(shortSpan* multiplier * Math.sin((double)2*Math.PI*(240-90)/360));
		path.lineTo(x, y);
		
		canvas.drawPath(path, paint);		
		
		return bmp;
	}

}
