package sample.postquiz;

import java.util.UUID;

import sample.edukaquiz.R;
import sample.edukaquiz.R.id;
import sample.edukaquiz.R.layout;
import sample.edukaquiz.R.string;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PostQuizMenuActivity extends Activity {

	private static UUID uuid;
	private static String userName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.postselect);
		
		this.setup();
		Toast.makeText(this, uuid.toString(), Toast.LENGTH_SHORT).show();
		Toast.makeText(this, userName, Toast.LENGTH_SHORT).show();
	}
	public static String getUserName(){
		return userName;
	}
	
	public static UUID getUUID(){
		return uuid;
	}
	
	public void pushBtn(View view){
				
		if(view.getId() == R.id.post_play){
			Intent i = new Intent(this,PostQuizPlayActivity.class);
			this.startActivity(i);
		}
		if(view.getId() == R.id.post_post){
			Intent i = new Intent(this,PostQuizPostingActivity.class);
			this.startActivity(i);
		}
		if(view.getId() == R.id.post_myquiz){
			Intent i = new Intent(this,PostQuizHistoryActivity.class);
			this.startActivity(i);
		}
		
	}
	
	private void setup(){
		
		this.readPrefs();
		if(uuid==null){
			uuid = UUID.randomUUID();
			this.writeUuidPrefs();
		}
		
		if(userName == null){
			
			this.setUserNameDialog();
			
		}
		
	}	
	
	private void setUserNameDialog(){
		
		final EditText edit = new EditText(this);
		//改行不可
		edit.setInputType(InputType.TYPE_CLASS_TEXT);
		InputFilter[] _inputFileter = new InputFilter[1];
		_inputFileter[0] = new InputFilter.LengthFilter(6);
		edit.setFilters(_inputFileter);
		
		
		new AlertDialog.Builder(this).setTitle(R.string.label_postDialogTitle).setMessage(R.string.label_postDialogMessage)
		.setView(edit).setPositiveButton("OK",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 自動生成されたメソッド・スタブ
				Toast.makeText(PostQuizMenuActivity.this, edit.getText().toString(), Toast.LENGTH_SHORT).show();
				
				if(edit.getText().toString().equals("")){
					setUserNameDialog();
				}else{
					userName = edit.getText().toString();
					writeUserNamePrefs();
				}
			}
		})
		.show();
	}
	
	private void writeUuidPrefs(){
		
		SharedPreferences prefs = getSharedPreferences("post", MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("uuid", this.uuid.toString());
		editor.commit();
	}
	
	private void writeUserNamePrefs(){
		
		SharedPreferences prefs = getSharedPreferences("post", MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("name", userName);
		editor.commit();
	}
	
	private void readPrefs(){
		
		SharedPreferences prefs = getSharedPreferences("post", MODE_PRIVATE);
		String id = prefs.getString("uuid", null);
		if(id != null)
			this.uuid = UUID.fromString(id);
		
		this.userName = prefs.getString("name", null);
	}

}
