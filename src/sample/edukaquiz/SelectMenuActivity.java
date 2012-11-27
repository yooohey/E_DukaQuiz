package sample.edukaquiz;

import sample.postquiz.PostQuizMenuActivity;
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
		
		if(item.getItemId() == R.id.menu_settings ){
			Intent i = new Intent(this,PreferencesActivity.class);
			startActivity(i);		
		}else{
			Intent i = new Intent(this,DebugListActivity.class);
			startActivity(i);		
		}
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
			
//			Intent i = new Intent(this,ResultActivity.class);
//			this.startActivityForResult(i,0);
			
		}else if(view.getId() == R.id.menuThoko){
			
			Intent i = new Intent(this,PostQuizMenuActivity.class);
			this.startActivityForResult(i,0);
			
		}else{
			Intent i = new Intent(this,StampLallyActivity.class);
			this.startActivity(i);
		}
	}

}
