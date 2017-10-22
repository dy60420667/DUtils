package dy.utils.libupdater.utils;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;


public class ToolSpan {
	
	
	/** 
	* 文字背景颜色 
	*/  
	public static SpannableString addBackColorSpan(SpannableString ss, int start, int end, int color) {
		if(TextUtils.isEmpty(ss)){
			return ss;
		}
		if(ss.length()<end){
			end =ss.length();
		}
		if(start>end){
			start = end;
		}
	    BackgroundColorSpan span = new BackgroundColorSpan(color);
	    ss.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	    return ss;
	}
	
	
	/** 
	* 文字颜色 
	*/
	public static SpannableString addForeColorSpan(SpannableString ss, int start, int end, int color) {
		if(TextUtils.isEmpty(ss)){
			return ss;
		}
		if(ss.length()<end){
			end =ss.length();
		}
		if(start>end){
			start = end;
		}
		ForegroundColorSpan span = new ForegroundColorSpan(color);
	    ss.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	    return ss;
	}
	
	/** 
	* 字体大小 
	*/  
	public static SpannableString addFontSpan(SpannableString ss, int start, int end, int textSize) {
		if(TextUtils.isEmpty(ss)){
			return ss;
		}
		if(ss.length()<end){
			end =ss.length();
		}
		if(start>end){
			start = end;
		}
		AbsoluteSizeSpan span = new AbsoluteSizeSpan(textSize);
	    ss.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	    return ss;
	}
	
	
	/** 
	* 粗体，斜体 
	* Typeface.BOLD_ITALIC
	*/  
	public static SpannableString addStyleSpan(SpannableString ss, int start, int end, int typeface) {
		if(TextUtils.isEmpty(ss)){
			return ss;
		}
		if(ss.length()<end){
			end =ss.length();
		}
		if(start>end){
			start = end;
		}
		StyleSpan span = new StyleSpan(typeface);
	    ss.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	    return ss;
	}
	
	/** 
	* 删除线 
	*/  
	public static SpannableString addStrikeSpan(SpannableString ss, int start, int end) {
		if(TextUtils.isEmpty(ss)){
			return ss;
		}
		if(ss.length()<end){
			end =ss.length();
		}
		if(start>end){
			start = end;
		}
		StrikethroughSpan span = new StrikethroughSpan();
	    ss.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	    return ss;
	}
	
	/** 
	* 下划线 
	*/  
	public static SpannableString addUnderLineSpan(SpannableString ss, int start, int end) {
		if(TextUtils.isEmpty(ss)){
			return ss;
		}
		if(ss.length()<end){
			end =ss.length();
		}
		if(start>end){
			start = end;
		}
		UnderlineSpan span = new UnderlineSpan();
	    ss.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	    return ss;
	}
	
	/** 
	*  图片 
	*/  
	public static SpannableString addImageSpan(Context context, SpannableString ss, int start, int imageId) {
		if(TextUtils.isEmpty(ss)){
			return ss;
		}
		if(ss.length()<start){
			start =ss.length();
		}
		Drawable d = context.getResources().getDrawable(imageId);
	    d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());  
	    
	    ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
	    ss.setSpan(span, start, start+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	    return ss;
	}
}
