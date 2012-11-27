package sample.stampLally;

import sample.edukaquiz.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnTouchModeChangeListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class HelpView extends LinearLayout implements OnTouchListener{

	private LayoutInflater inf;
	ViewFlipper viewFlipper;
	Animation left_in;
	Animation right_in;
	Animation left_out;
	Animation right_out;
	Context context;
	private Integer pageSize;
	private TextView tv;
	private CheckBox box;
	
	public HelpView(Context context,AttributeSet attrs) {
		super(context);
		this.context = context;
		//setFocusable(true);
		
		//inf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		
		View layout = LayoutInflater.from(context).inflate(R.layout.help, this);
		//View layout = inf.inflate(R.layout.help, this);
		this.viewFlipper = (ViewFlipper)layout.findViewById(R.id.viewFlipper1);
		this.viewFlipper.setOnTouchListener(this);
		
		this.left_in = AnimationUtils.loadAnimation(this.context, R.anim.left_in);
		this.right_in = AnimationUtils.loadAnimation(context, R.anim.right_in);
		this.left_out = AnimationUtils.loadAnimation(context, R.anim.left_out);
		this.right_out = AnimationUtils.loadAnimation(context, R.anim.right_out);
		
		//viewにセットされたページ数を返す
		Log.d("#getChildCount",String.valueOf(viewFlipper.getChildCount()));
		
		pageSize = viewFlipper.getChildCount();
		
		tv = (TextView)layout.findViewById(R.id.helpPage);
		tv.setText("1/"+pageSize);
		
		this.box = (CheckBox)layout.findViewById(R.id.checkBox1);
		
		box.setChecked(true);
		writePref(box.isChecked());
		this.box.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ

				writePref(box.isChecked());

			}
		});
		
		
	}
	
	private void writePref(Boolean b){
		SharedPreferences prefs = context.getSharedPreferences("E_DUKA", context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean("stampHelp", !b).commit();
		
	}
	/*
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO 自動生成されたメソッド・スタブ
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		this.setMeasuredDimension(width, height);
	}*/

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		float posX=0;
		//#getDisplayedChild()現在のviewの位置を返す
		//Log.d("AAA",String.valueOf(viewFlipper.getDisplayedChild()));
		switch(event.getAction()){
		
		case MotionEvent.ACTION_DOWN:
			posX = event.getAction();
			break;
		case MotionEvent.ACTION_UP:
			if(posX > event.getX() && viewFlipper.getDisplayedChild()+1 < this.pageSize){
				Log.d("Touch_ACTION_UP",String.valueOf(posX));
				viewFlipper.setInAnimation(right_in);
				viewFlipper.setOutAnimation(left_out);				
				viewFlipper.showNext();
			}else if(posX < event.getX() &&  viewFlipper.getDisplayedChild() > 0){
				
				viewFlipper.setInAnimation(left_in);
				viewFlipper.setOutAnimation(right_out);
				
				viewFlipper.showPrevious();
				
			}
			
			default:
				tv.setText(viewFlipper.getDisplayedChild()+1+"/"+pageSize);
				break;
		}		
		
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}
	

	

}
