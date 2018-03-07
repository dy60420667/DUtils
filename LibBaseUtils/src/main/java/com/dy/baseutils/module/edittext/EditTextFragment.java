package com.dy.baseutils.module.edittext;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dy.baseutils.R;
import com.dy.baseutils.module.base.DYBaseFragment;
import com.dy.baseutils.utils.dialog.ToastUtils;

/**
 * Descripty:
 * Auth:  邓渊  dengyuan3@jd.com
 * Date: 2018/1/12.17:55
 */

public class EditTextFragment extends DYBaseFragment {
    private final static int MAX        = 10;
    private int maxLength               = MAX;

    public static EditTextFragment newInstance(String defaultStr,int maxLength) {
        Bundle args = new Bundle();
        args.putString("defaultStr",defaultStr);
        args.putInt("maxLength",maxLength);
        EditTextFragment fragment = new EditTextFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public String getInputText(){
        return text_input.getText().toString();
    }
    
    @Override
    public int getFragmentLayout() {
        return R.layout.f_libbase_edittext;
    }


    private EditText text_input;
    private TextView tag_input_tips;

    @Override
    public void initView() {
        text_input = rootView.findViewById(R.id.text_input);
        tag_input_tips = rootView.findViewById(R.id.tag_input_tips);

        Bundle bundle = getArguments();
        maxLength = bundle.getInt("maxLength", MAX);
        String defaultStr = bundle.getString("defaultStr");

        if (!TextUtils.isEmpty(defaultStr)) {
            text_input.setText(defaultStr);
            if (defaultStr.length() <= maxLength) {
                tag_input_tips.setText("你还可以输入" + (maxLength - defaultStr.length()) + "个字  ("
                        + defaultStr.length() + "/" + maxLength + ")");
            }
        }

        text_input.addTextChangedListener(mTextWatcher);
    }

    TextWatcher mTextWatcher = new TextWatcher() {
        private int editStart;
        private int editEnd;

        @Override
        public void beforeTextChanged(CharSequence s, int arg1, int arg2,
                                      int arg3) {
        }

        @Override
        public void onTextChanged(CharSequence s, int arg1, int arg2,
                                  int arg3) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            editStart = text_input.getSelectionStart();
            editEnd = text_input.getSelectionEnd();
            if (s.toString().length() > maxLength) {
                ToastUtils.showToast(getContext(),"你输入的字数已经超过了限制！");
                s.delete(editStart - 1, editEnd);
                int tempSelection = editStart;
                text_input.setText(s);
                text_input.setSelection(tempSelection);
            }
            tag_input_tips.setText("你还可以输入"
                    + (maxLength - s.toString().length())
                    + "个字  (" + s.toString().length() + "/"
                    + maxLength + ")");
        }
    };


}
