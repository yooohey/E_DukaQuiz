package sample.edukaquiz;

import sample.stampLally.StampLallyActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SelectMenuActivity extends Activity{
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO 自動生成されたメソッド・スタブ
		
		Intent i = new Intent(this,PreferencesActivity.class);
		startActivity(i);
		return super.onMenuItemSelected(featureId, item);
		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        
        return true;
    }


	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                
        setContentView(R.layout.menu);
        
    }
	
	public void qesS(View view){
		if(view.getId() == R.id.menuQuiz){
			Intent i = new Intent(this,OfflineQuizActivity.class);
			this.startActivityForResult(i,0);
		}else{
			Intent i = new Intent(this,StampLallyActivity.class);
			this.startActivity(i);
		}
	}

}
