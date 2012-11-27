package sample.stampLally;

import sample.edukaquiz.R;
import android.app.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

public class Testmekuri extends Activity implements OnTouchListener {

	ViewFlipper viewflipper;
	float posX;
	Animation left_in;
	Animation right_in;
	Animation left_out;
	Animation right_out;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);

		// ViewFlipperを取得する
		viewflipper = (ViewFlipper)this.findViewById(R.id.viewflipper);

		// リスナーを設定する
		viewflipper.setOnTouchListener(this);

		// アニメーションを設定する
		left_in = AnimationUtils.loadAnimation(this, R.anim.left_in);
		right_in = AnimationUtils.loadAnimation(this, R.anim.right_in);
		left_out = AnimationUtils.loadAnimation(this, R.anim.left_out);
		right_out = AnimationUtils.loadAnimation(this, R.anim.right_out);

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
}

	
	/*
	

	// タッチイベント発生時
	@Override
	public boolean onTouch(View v, MotionEvent event) {

		switch(event.getAction()){

		case MotionEvent.ACTION_DOWN:
			// タッチ場所を取得
			posX = event.getX();
			break;
		case MotionEvent.ACTION_UP:
			if(posX > event.getX()){

				// アニメーションを設定
				viewflipper.setInAnimation(right_in);
				viewflipper.setOutAnimation(left_out);

				// 次ページへ移動
				viewflipper.showNext();
			}else if(posX < event.getX()){

				// アニメーションを設定
				viewflipper.setInAnimation(left_in);
				viewflipper.setOutAnimation(right_out);

				// 前ページへ移動
				viewflipper.showPrevious();
			}
		default:
			break;
		}
		return true;
	}
}*/