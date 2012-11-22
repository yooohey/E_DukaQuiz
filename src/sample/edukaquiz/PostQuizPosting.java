package sample.edukaquiz;

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

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class PostQuizPosting extends Activity{

	private String strUrl;
	private Map<String, String> quizMap = new HashMap<String, String>();
	private final String Q="questhin",A="answer",D1="dummy1",D2="dummy2",D3="dummy3";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.posting);
			
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

	public void quizPost(View view){
		
		//PostTask post = new PostTask();
		//post.execute("test");
		this.postData();
		
	}
	
	private void checkQuestions(){

		if(true){
			
		}else{
			Toast.makeText(this, "エラーデス", Toast.LENGTH_SHORT).show();
		}
	}

	private class PostTask extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params2) {
			// TODO 自動生成されたメソッド・スタブ
			String url = "http://wacha2.herokuapp.com/api/";
			String q = null;
			String test = "hello";
			String[] contents = {"post","1+1は？","1","2","3","4","tanaka"};

			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);

			ArrayList <NameValuePair> params = new ArrayList <NameValuePair>();
			params.add( new BasicNameValuePair("post", contents[0]));
			params.add( new BasicNameValuePair("question", contents[5]));
			params.add( new BasicNameValuePair("answer", contents[1]));
			params.add( new BasicNameValuePair("dummy1", contents[2]));
			params.add( new BasicNameValuePair("dummy2", contents[3]));
			params.add( new BasicNameValuePair("dummy3", contents[4]));
			params.add( new BasicNameValuePair("username", contents[6]));
			
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
	
	
	private void postData(){
		
		String url = "http://wacha2.herokuapp.com/api";
		String q = null;
		String test = "hello";
		String[] contents = {"1+1は？","1","2","3","4"};
		
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		
		ArrayList <NameValuePair> params = new ArrayList <NameValuePair>();
		params.add( new BasicNameValuePair("question", contents[0]));
		params.add( new BasicNameValuePair("answer", contents[1]));
		params.add( new BasicNameValuePair("dummy1", contents[2]));
		params.add( new BasicNameValuePair("dummy2", contents[3]));
		params.add( new BasicNameValuePair("dummy3", contents[4]));
		params.add( new BasicNameValuePair("quizcode ", "1"));
		params.add( new BasicNameValuePair("genre", "1"));
				 
		HttpResponse res = null;
				
		try{
			post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
			res = client.execute(post);
			Log.d("",res.toString());
			Log.d("",res.toString());
		} catch (ClientProtocolException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
		} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
		}
		
	}

}
