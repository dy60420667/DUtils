package com.dy.hbjg.module.me.login;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dy.hbjg.R;
import com.dy.hbjg.module.me.register.RegisterActivity;

public class LoginFragment extends Fragment implements View.OnClickListener {
    public static LoginFragment getFragment(){
        LoginFragment f = new LoginFragment();
        return f;
    }

    private View viewRoot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.f_login,null);
        initView();
        Log.i("xx","onCreateView");
        return viewRoot;
    }

    private void initView() {
        viewRoot.findViewById(R.id.tv_kuaisuzhuce).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_kuaisuzhuce:
                RegisterActivity.gotoActivity(getActivity());
                break;
        }
    }
}
