package com.dy.hbjg.module.me.register;


import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.dy.baseutils.module.base.DYBaseFragment;
import com.dy.baseutils.utils.dialog.ToastUtils;
import com.dy.hbjg.R;

public class RegisterFragment extends DYBaseFragment implements View.OnClickListener {
    public static RegisterFragment getFragment(){
        RegisterFragment f = new RegisterFragment();
        return f;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.f_register;
    }



    private EditText et_phone;
    @Override
    public void initView() {
        rootView.findViewById(R.id.btn_ok).setOnClickListener(this);

        et_phone = (EditText) rootView.findViewById(R.id.et_phone);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_ok:
                String phtonNum = et_phone.getText().toString();
                if(TextUtils.isEmpty(phtonNum)){
                    ToastUtils.showToast(getContext(),R.string.input_phone);
                    return;
                }
                break;
        }
    }
}
