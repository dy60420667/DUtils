package com.dy.hbjg.module.resource;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dy.baseutils.module.base.DYBaseFragment;
import com.dy.baseutils.module.base.DYBaseListFragment;
import com.dy.baseutils.view.viewpager.scrolltab.ScllorTabView;
import com.dy.hbjg.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Auth : dy
 * Time : 2017/2/10 11:49
 * Email: dymh21342@163.com
 * Description:
 */

public class F_Movies extends DYBaseFragment implements ViewPager.OnPageChangeListener {
    public static F_Movies createFragment(){
        F_Movies f_movies_top =new F_Movies();
        return f_movies_top;
    }

    private RadioGroup rg_tab;
    public ViewPager viewPager;
    private ScllorTabView viewp_tabview;

    @Override
    public int getFragmentLayout() {
        return R.layout.f_movies;
    }

    @Override
    public void initView() {
        viewp_tabview = (ScllorTabView) rootView.findViewById(R.id.viewp_tabview);
        viewp_tabview.setSelectedColor(ContextCompat.getColor(getContext(), R.color.colorAccent), ContextCompat.getColor(getContext(), R.color.colorAccent));

        viewp_tabview.setTabNum(2);
        viewp_tabview.setCurrentNum(0);

        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        viewPager.setAdapter(new MainAdapter(getChildFragmentManager()));

        viewPager.addOnPageChangeListener(this);

        rg_tab = (RadioGroup) rootView.findViewById(R.id.rg_tab);
        rg_tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                switch (radioButtonId) {
                    case R.id.button_1:
                        viewPager.setCurrentItem(0, true);
                        break;
                    case R.id.button_2:
                        viewPager.setCurrentItem(1, true);
                        break;
                }
            }
        });
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        viewp_tabview.setOffset(position, positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
        RadioButton button = (RadioButton) rg_tab.getChildAt(position);
        button.setChecked(true);

        DYBaseListFragment fragment = list.get(position);
        fragment.lazyLoadate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private List<DYBaseListFragment> list;

    private class MainAdapter extends FragmentPagerAdapter {


        public MainAdapter(FragmentManager fm) {
            super(fm);
            list = new ArrayList<>();
            list.add(F_Movies_top.createFragment());
            list.add(F_Movies_InTheaters.createFragment());
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
