package sample.edukaquiz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class PreferencesActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.prefs_screen);
		this.getPreferenceManager().setSharedPreferencesName("E_DUKA");
		this.addPreferencesFromResource(R.xml.prefs);
		
		PreferenceScreen twitter = (PreferenceScreen)this.findPreference("twitter");
		
		twitter.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			
			@Override
			public boolean onPreferenceClick(Preference preference) {
				// TODO 自動生成されたメソッド・スタブ
				
				new AlertDialog.Builder(PreferencesActivity.this).setTitle("お前を消す方法").setPositiveButton("はい", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO 自動生成されたメソッド・スタブ
						
					}
				})
				.setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO 自動生成されたメソッド・スタブ
						
					}
				}).show();
				return false;
			}
		});
		
		PreferenceScreen stamp = (PreferenceScreen)this.findPreference("stampClear");
		stamp.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			
			@Override
			public boolean onPreferenceClick(Preference preference) {
				// TODO 自動生成されたメソッド・スタブ
				
				new AlertDialog.Builder(PreferencesActivity.this).setTitle("スタンプを消去します").setPositiveButton("はい", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO 自動生成されたメソッド・スタブ
						SharedPreferences prefs = getSharedPreferences("stamp", MODE_PRIVATE);
						SharedPreferences.Editor editor = prefs.edit();
						editor.clear().commit();
						Toast.makeText(PreferencesActivity.this, "スタンプを消去しました", Toast.LENGTH_SHORT).show();
						
					}
				})
				.setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO 自動生成されたメソッド・スタブ
						
					}
				}).show();
				return false;
			}
		});
		
		
	}
	
	

}
