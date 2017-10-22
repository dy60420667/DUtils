package com.dy.baseutils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.dy.baseutils.R;


public class EyeImageView extends ImageView {
	private int type = 0;//0表示未选择，1表示选择
	

	public EyeImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	
	 public void changeType(){
		 type = (type==0)?1:0;
		 changeBackGround();
		 
	 }
	 
	 public void setType(int type){
		 this.type = type;
		 changeBackGround();
	 }
	 
	 public int getType(){
		 return type;
	 }

	private void changeBackGround() {
		if(type==1){
			setImageResource(R.drawable.tool_icon_show_display_pwd);
		}else{
			setImageResource(R.drawable.tool_icon_not_display_pwd);
		}
		
	}

}
