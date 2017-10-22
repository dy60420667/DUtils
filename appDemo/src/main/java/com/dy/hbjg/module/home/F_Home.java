package com.dy.hbjg.module.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dy.baseutils.Tools;
import com.dy.baseutils.module.newsshow.A_View_msg;
import com.dy.baseutils.view.viewpager.autoscroll.AutoScrollViewPager;
import com.dy.hbjg.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Auth : dy
 * Time : 2017/1/20 16:55
 * Email: dymh21342@163.com
 * Description:
 */

public class F_Home extends Fragment {
    private View rootView;
    private AutoScrollViewPager viewPager;


    /**
     * 装点点的ImageView数组
     */
    private ImageView[] tips;

    /**
     * 装ImageView数组
     */
    private List<View> views;

    /**
     * 图片资源id
     */
    private int[] imgIdArray;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.f_home, null);
        initViewPager();

        initView();

        return rootView;
    }

    private void initView() {
        rootView.findViewById(R.id.layout_1).setOnClickListener(toDetail);
        rootView.findViewById(R.id.layout_2).setOnClickListener(toDetail);
        rootView.findViewById(R.id.layout_3).setOnClickListener(toDetail);
        rootView.findViewById(R.id.layout_4).setOnClickListener(toDetail);
        rootView.findViewById(R.id.layout_5).setOnClickListener(toDetail);
        rootView.findViewById(R.id.layout_6).setOnClickListener(toDetail);



    }

    View.OnClickListener toDetail = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            A_View_msg.gotoActivity(getActivity());
        }
    };

    private void initViewPager() {

        ViewGroup group = (ViewGroup) rootView.findViewById(R.id.viewGroup);
        viewPager = (AutoScrollViewPager) rootView.findViewById(R.id.viewPager);


        //载入图片资源ID
        imgIdArray = new int[]{R.drawable.leaf_01, R.drawable.leaf_02, R.drawable.leaf_03};


        //将点点加入到ViewGroup中
        tips = new ImageView[imgIdArray.length];
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Tools.dip2px(getActivity(), 8), Tools.dip2px(getActivity(), 8));
            params.setMargins(Tools.dip2px(getActivity(), 5), Tools.dip2px(getActivity(), 11), Tools.dip2px(getActivity(), 5), Tools.dip2px(getActivity(), 11));
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            tips[i] = imageView;
            if (i == 0) {
                tips[i].setImageResource(R.drawable.ic_banner_select);
            } else {
                tips[i].setImageResource(R.drawable.ic_banner_unselect);
            }
            group.addView(imageView);
        }
        views = new ArrayList<>();

        for (int i = 0; i < imgIdArray.length; i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_layout_image, null);
            ImageView image = (ImageView) view.findViewById(R.id.image);
            image.setImageResource(imgIdArray[i]);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    A_View_msg.gotoActivity(getActivity());
                }
            });
            views.add(view);
        }

        //设置Adapter
        viewPager.setAdapter(new MyAdapter());
       viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
           }

           @Override
           public void onPageSelected(int position) {
               for (int i = 0; i < tips.length; i++) {
                   ImageView imageView = tips[i];

                   if (i == position) {
                       tips[i].setImageResource(R.drawable.ic_banner_select);
                   } else {
                       tips[i].setImageResource(R.drawable.ic_banner_unselect);
                   }
               }
           }

           @Override
           public void onPageScrollStateChanged(int state) {
           }
       });

        viewPager.setInterval(2000);
        viewPager.setScrollDurationFactor(2.0);
        viewPager.startAutoScroll();
    }


    @Override
    public void onPause() {
        super.onPause();
        if(viewPager!=null){
            viewPager.stopAutoScroll();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(viewPager!=null){
            viewPager.startAutoScroll();
        }
    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object arg1) {
            return view == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }
    }


}
