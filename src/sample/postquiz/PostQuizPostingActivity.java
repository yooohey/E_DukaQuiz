package sample.postquiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import sample.edukaquiz.R;
import sample.edukaquiz.R.layout;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PostQuizPostingActivity extends Activity{

	private String strUrl =  "http://wacha2.herokuapp.com/api/";;
	private Map<DBColumn, String> quizMap = new HashMap<DBColumn, String>();
	private static List<Integer> editTextList = new ArrayList<Integer>();
	
	static{
		editTextList.add(R.id.post_Q_editText);
		editTextList.add(R.id.post_A_editText);
		editTextList.add(R.id.post_D1_editText);
		editTextList.add(R.id.post_D2_editText);
		editTextList.add(R.id.post_D3_editText);
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.posting);
		
		//デバッグ用
		this.debugSetET();
		
		
			
	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO 自動生成されたメソッド・スタブ
		super.onStop();
	}

	public void debugSetET(){
		EditText et = (EditText)findViewById(editTextList.get(0));
		et.setText("testQuestion");
		et = (EditText)findViewById(editTextList.get(1));
		et.setText("testAnswer");
		et = (EditText)findViewById(editTextList.get(2));
		et.setText("testDammy1");
		et = (EditText)findViewById(editTextList.get(3));
		et.setText("testDammy2");
		et = (EditText)findViewById(editTextList.get(4));
		et.setText("testDammy3");
		
	}
	
	public void quizPost(View view){
		
		//PostTask post = new PostTask();
		//post.execute("test");
		if(!this.checkEditText())
			Log.d("","false");
		else{
			Log.d("","true");
			
			EditText et;
			this.quizMap.put(DBColumn.mode, "post");
			this.quizMap.put(DBColumn.statusCode, "01");
			this.quizMap.put(DBColumn.userName, PostQuizMenuActivity.getUserName());
			this.quizMap.put(DBColumn.userKey, PostQuizMenuActivity.getUUID().toString());
			
			et=(EditText)this.findViewById(editTextList.get(0));
			this.quizMap.put(DBColumn.quesiton,et.getText().toString());
			
			et=(EditText)this.findViewById(editTextList.get(1));
			this.quizMap.put(DBColumn.answer,et.getText().toString() );
			et=(EditText)this.findViewById(editTextList.get(2));
			this.quizMap.put(DBColumn.dammy1, et.getText().toString());
			et=(EditText)this.findViewById(editTextList.get(3));
			this.quizMap.put(DBColumn.dammy2, et.getText().toString());
			et=(EditText)this.findViewById(editTextList.get(4));
			this.quizMap.put(DBColumn.dammy3, et.getText().toString());
			
			this.quizMap.put(DBColumn.quizCode, "1");
			
			
			this.quizMap.put(DBColumn.image, null);
			//Date date = new Date();
			//this.quizMap.put(DBColumn.created_at,date.toString());
			//Log.d("aaa",date.toString());
			PostTask post = new PostTask();
			post.execute("");
		}
		
		
	}
	
	private Boolean checkEditText(){

		EditText et;
		String text;
		
		for(int id:editTextList){
			
			
			et = (EditText)this.findViewById(id);
			text = et.getText().toString();
			if(text.equals(""))
				return false;
		}
		
		return true;
	}

	private class PostTask extends AsyncTask<String, Integer, String>{

		
		//メインとなる処理　まずはここがバックで実行される
		@Override
		protected String doInBackground(String... params2) {
			// TODO 自動生成されたメソッド・スタブ
			//String[] contents = {"post","1+1は？","1","2","3","4","tanaka"};

			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(strUrl);

			
			//POSTをMAPから送信できるように改良
			
			
			ArrayList <NameValuePair> params = new ArrayList <NameValuePair>();
			/*params.add( new BasicNameValuePair(DBColumn._id.getColumnName(), contents[0]));
			//quizMap.k
			params.add( new BasicNameValuePair(DBColumn.quesiton.getColumnName(), quizMap.get(DBColumn.quesiton.getColumnName())));
			params.add( new BasicNameValuePair("answer", contents[1]));
			params.add( new BasicNameValuePair("dummy1", contents[2]));
			params.add( new BasicNameValuePair("dummy2", contents[3]));
			params.add( new BasicNameValuePair("dummy3", contents[4]));
			params.add( new BasicNameValuePair("username", contents[6]));
			*/
			for(Map.Entry<DBColumn, String> e:quizMap.entrySet()){
				Log.d(e.getKey().getColumnName(),e.getValue()+"");
				params.add(new BasicNameValuePair(e.getKey().getColumnName(), e.getValue()));
			}
			
			HttpResponse res = null;

			try{
				post.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
				res = client.execute(post);
				Log.d("aaa",res.toString());
			} catch (ClientProtocolException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			return null;
		}
	}
}
