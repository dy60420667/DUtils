package com.dy.hbjg.module.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;

import com.dy.baseutils.module.base.DYBaseActivity;
import com.dy.hbjg.R;
import com.dy.hbjg.module.me.F_Me;
import com.dy.hbjg.module.resource.F_Movies;
import com.dy.hbjg.module.search.A_search;


public class A_Home extends DYBaseActivity implements View.OnClickListener{

    public static void gotoActivity(Context mContext){
        Intent it = new Intent(mContext, A_Home.class);
        mContext.startActivity(it);
    }

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViewPager();
        initBottom();

        setEnableShowBack(false);
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.a_home;
    }

    @Override
    public void changeFragment() {
    }

    @Override
    public Fragment initFragment() {
        return null;
    }


    private void initBottom(){
        RadioGroup group = (RadioGroup) findViewById(R.id.rg_tab);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int arg1) {
                int radioButtonId = radioGroup.getCheckedRadioButtonId();

                switch (radioButtonId){
                    case R.id.button_1:
                        viewPager.setCurrentItem(0,false);
                        setTitle(getResources().getString(R.string.app_name));
                        getToolbar().getMenu().findItem(R.id.ab_search).setVisible(true);
                        break;
                    case R.id.button_2:
                        setTitle(getResources().getString(R.string.resource));
                        viewPager.setCurrentItem(1,false);
                        break;
                    case R.id.button_3:
                        viewPager.setCurrentItem(2,false);
                        setTitle("");
                        getToolbar().getMenu().findItem(R.id.ab_search).setVisible(false);
                        break;
                }
            }
        });
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item.getItemId()==R.id.ab_search){
            A_search.gotoActivity(this);
        }
        return super.onMenuItemClick(item);
    }

    @Override
    public void onClick(View view) {

    }

    private class MainAdapter extends FragmentPagerAdapter {
        public MainAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return createFragmentByIndex(position);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    public Fragment createFragmentByIndex(int id) {
        switch (id) {
            case 0:
                return new F_Home();
            case 1:
                return F_Movies.createFragment();
            case 2:
                return new F_Me();
            default:
                return new F_Me();
        }
    }
}
