package sample.postquiz;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import sample.edukaquiz.R;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class PostQuizPlayActivity extends Activity {
	
	private String strUrl = "http://wacha2.herokuapp.com/api/";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.postquizplay);
		DownloadQuiz downquiz = new DownloadQuiz(this);
		downquiz.execute("");
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
	
	private class DownloadQuiz extends AsyncTask<String, Integer, String>
	 implements OnCancelListener{

		ProgressDialog dialog;
		Context context;
		
		public DownloadQuiz(Context context){
			this.context = context;
		}
		
		//最初にUIスレッド上で呼び出される ProgressBarを表示
		@Override
		protected void onPreExecute() {
			// TODO 自動生成されたメソッド・スタブ
			super.onPreExecute();		
			
			dialog = new ProgressDialog(this.context);
			dialog.setTitle("QuizDownload");
			dialog.setMessage("クイズをダウンロード中です");
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.setCancelable(true);
			dialog.setOnCancelListener(this);
			dialog.setMax(100);
			dialog.setProgress(0);
			dialog.show();
		}

		//メイン処理　ワーカースレッドで呼ばれるバックグラウンドで走る
		@Override
		protected String doInBackground(String... params2) {
			// TODO 自動生成されたメソッド・スタブ
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(strUrl);
			HttpResponse res = null;
			
			ArrayList <NameValuePair> params = new ArrayList <NameValuePair>();
			params.add(new BasicNameValuePair(DBColumn.mode.getColumnName(),"get"));
			//params.add(new BasicNameValuePair(DBColumn._id.getColumnName(),));
			
			
			try {
				post.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));			
				res = client.execute(post);
				/*ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				res.getEntity().writeTo(byteArrayOutputStream);
				res.toString();
				*/
				InputStream objStream = res.getEntity().getContent();
				InputStreamReader objReader= new InputStreamReader(objStream);
				BufferedReader objBuf = new BufferedReader(objReader);
				
				StringBuilder objJson = new StringBuilder();
				String sLine;
				while((sLine = objBuf.readLine())!=null){
					objJson.append(sLine);
				}
				Log.d("json",objJson.toString());
				
				res.toString();
				
				/*for(int i=0;i<10;i++){
					if(this.isCancelled()){
						break;
					}
					Thread.sleep(1000);
					//2番目の引数に渡す値
					this.publishProgress((i+1)*10);
					
				}*/
			} catch (ClientProtocolException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			
			return res.toString();
		}

		@Override
		protected void onCancelled() {
			// TODO 自動生成されたメソッド・スタブ
			super.onCancelled();
			dialog.dismiss();
		}

		
		//doInBackgroundが終わると呼ばれる 引数はdoInBackgroundのパラメータ
		@Override
		protected void onPostExecute(String result) {
			// TODO 自動生成されたメソッド・スタブ
			super.onPostExecute(result);
			dialog.dismiss();
			Log.d("back",result);
			
		}

		//publishProgressが呼ばれるとUIスレッド上でonProgressUpdateが呼ばれる
		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO 自動生成されたメソッド・スタブ
			super.onProgressUpdate(values);
			
			dialog.setProgress(values[0]);
		}

		//キャンセル処理
		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO 自動生成されたメソッド・スタブ
			this.cancel(true);
		}
		
		
		
	}

}
