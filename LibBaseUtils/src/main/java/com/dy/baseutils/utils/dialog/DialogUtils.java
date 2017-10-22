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

public class DialogUtils {
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
	 * @param msg
	 * @return
	 */
	public static void showEditTextDialog(final Activity context, String msg,
										  final IDialogOkOnlickListener onlickListener, int maxLength, final String errorMsg) {
		dismissDialog();
		if(context==null||context.isFinishing()){
			return;
		}
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.dialog_edit, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		// main.xml中的ImageView
		final EditText et = (EditText) v.findViewById(R.id.et_msm);
		et.setHint(msg);
		Button btnOK = (Button) v.findViewById(R.id.btn_ok);
		Button btnNO = (Button) v.findViewById(R.id.btn_no);
		btnOK.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String newMsg = et.getText().toString();
				if (TextUtils.isEmpty(newMsg)) {
					ToastUtils.ShowToast(context, errorMsg);
				} else {
					dialogCreate.dismiss();
					arg0.setTag(newMsg);
					if(onlickListener!=null){
						onlickListener.onclick(arg0);
						
					}
				}
			}
		});
		btnNO.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialogCreate.dismiss();
			}
		});
		dialogCreate = new Dialog(context, R.style.Theme_ToolCustomDialog);// 创建自定义样式dialog
		dialogCreate.setContentView(v, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));// 设置布局
		dialogCreate.show();
	}

	public interface IDialogOkOnlickListener {
		void onclick(View view);
	}

}
