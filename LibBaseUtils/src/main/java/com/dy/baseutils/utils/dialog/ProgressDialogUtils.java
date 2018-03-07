package com.dy.baseutils.utils.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dy.baseutils.R;

public class ProgressDialogUtils {
	private static Dialog dialogCreate = null;

	public static void dismissDialog() {
		try {
			dialogCreate.dismiss();
		} catch (Exception e) {
		}
	}


	/**
	 * 得到自定义的progressDialog
	 * 
	 * @param context
	 * @param msg
	 * @return
	 */
	public static void showLoadingDialog(Activity context, String msg) {
		dismissDialog();
		if(context==null||context.isFinishing()){
			return;
		}
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		// main.xml中的ImageView
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		tipTextView.setText(msg);// 设置加载信息

		dialogCreate = new Dialog(context, R.style.Theme_ToolCustomDialog);// 创建自定义样式dialog

		dialogCreate.setContentView(v, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));// 设置布局
		dialogCreate.show();
	}

	/**
	 * 得到自定义的progressDialog
	 *
	 * @param context
	 * @return
	 */
	public static void showProgressDialog(Activity context,IProgress iProgress) {
		dismissDialog();
		if(context==null||context.isFinishing()){
			return;
		}
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		// main.xml中的ImageView
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
//		tipTextView.setText(msg);// 设置加载信息

		dialogCreate = new Dialog(context, R.style.Theme_ToolCustomDialog);// 创建自定义样式dialog

		dialogCreate.setContentView(v, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));// 设置布局
		dialogCreate.show();
	}

	public interface IProgress{
		void onProgress(long progress, long max);
		void onSucess();
		void onError(Exception e);
	}
}
