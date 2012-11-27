package sample.edukaquiz;

import sample.stampLally.StampLallyActivity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DebugListActivity extends ListActivity {
	String edukaquiz = "sample.edukaquiz.";
	String postquiz = "sample.postquiz.";
	String stamp = "sample.stampLally.";
	String[] activitys = {edukaquiz+"ResultActivity",edukaquiz+"OfflineQuizActivity",edukaquiz+"ResultActivity",edukaquiz+"SelectMenuActivity",
			postquiz+"PostQuizHistoryActivity",postquiz+"PostQuizMenuActivity",
			postquiz+"PostQuizPlayActivity",postquiz+"PostQuizPostingActivity",stamp+"StampLallyActivity",stamp+"Testmekuri"};
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,activitys));
        
    }

	@Override
    protected void onListItemClick(ListView list,View view,int position,long id){
    	
    	super.onListItemClick(list, view, position, id);
    	
    	String testName = activitys[position];
    	try{
    		Class clazz = Class.forName(testName);
    		Intent intent = new Intent(this,clazz);
    		startActivity(intent);
    	}catch (ClassNotFoundException e){
    		e.printStackTrace();
    		
    	}    	
    }
}
