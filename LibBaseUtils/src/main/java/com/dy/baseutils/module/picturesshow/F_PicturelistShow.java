package com.dy.baseutils.module.picturesshow;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dy.baseutils.R;
import com.dy.baseutils.module.base.DYBaseFragment;
import com.dy.baseutils.utils.ImageUtils;

import java.util.ArrayList;

/**
 * Auth : dy
 * Time : 2016/8/3 09:13
 * Email: dymh21342@163.com
 * Description:
 */
public class F_PicturelistShow extends DYBaseFragment {
    public static Fragment createFragment(ArrayList<String> list,int position){
        F_PicturelistShow f = new F_PicturelistShow();
        Bundle b = new Bundle();
        b.putSerializable("list",list);
        b.putInt("position",position);
        f.setArguments(b);
        return f;
    }


    private ArrayList<ImageView> listPoints = new ArrayList<ImageView>();
    private ArrayList<View> listViews = new ArrayList<View>();

    private ArrayList<String> list = new ArrayList<String>();
    private ViewPager viewPager;
    private PagerAdapter adapter;

    private int position;



    @Override
    public int getFragmentLayout() {
        return R.layout.f_picturelistshow;
    }

    @Override
    public void initView() {
        Bundle b = getArguments();
        list = b.getStringArrayList("photos");
        position = b.getInt("position");
        intListView();
        initPagerView();
    }

    private void intListView() {
        LinearLayout viewGroup = (LinearLayout) rootView.findViewById(R.id.viewGroup);
        for (int i = 0; i < list.size(); i++) {
            View v = inflater.inflate(R.layout.layout_item_pictureshow, null);
            ImageView img = (ImageView) v.findViewById(R.id.imageView1);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    getActivity().finish();
                    getActivity().overridePendingTransition(-1, -1);
                }
            });
            String url = list.get(i);
            if (TextUtils.isEmpty(url)) {
                continue;
            }
            if (!url.startsWith("http://")) {
                Bitmap bm = ImageUtils.decodeBitmapNoMax(url, getContext());
                bm = rotateBitmap(bm);
                if (bm != null) {
                    img.setImageBitmap(bm);
                }
            }
            listViews.add(v);

            ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(20, 20));
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            listPoints.add(imageView);
            if (i == position) {
                imageView.setImageResource(R.drawable.ic_banner_select);
            } else {
                imageView.setImageResource(R.drawable.ic_banner_unselect);
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            viewGroup.addView(imageView, layoutParams);
        }

        if(list.size()==0){
            viewGroup.setVisibility(View.GONE);
        }
    }

    private Bitmap rotateBitmap(Bitmap bm) {
        int w = bm.getWidth();
        int h = bm.getHeight();
        if (w - h > 50) {
            // 旋转图片
            Matrix m = new Matrix();
            m.postRotate(90);
            bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
        }
        return bm;
    }

    private void initPagerView() {
        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        adapter = new MyAdapter();
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new MyChangeListener());
        viewPager.setCurrentItem(position);
    }

    public class MyChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int selectItems) {
            selectItems = selectItems % listPoints.size();
            for (int i = 0; i < listPoints.size(); i++) {
                ImageView img = listPoints.get(i);
                if (i == selectItems) {
                    img.setImageResource(R.drawable.ic_banner_select);
                } else {
                    img.setImageResource(R.drawable.ic_banner_unselect);
                }
            }
        }
    }

    public class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            if (list.size() <= 2) {
                return list.size();
            } else {
                return Integer.MAX_VALUE;
            }
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            // ((ViewPager) container).removeView(listViews.get(position %
            // listViews.size()));
        }

        /**
         * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
         */
        @Override
        public Object instantiateItem(View container, int position) {
            position = position % listViews.size();

            View v = listViews.get(position);
            final TextView textView1 = (TextView) v.findViewById(R.id.textView1);
            String url = list.get(position);
            if (url.startsWith("http://")) {
                final ImageView img = (ImageView) v.findViewById(R.id.imageView1);
                Glide.with(getContext())
                        .load(url)
                        .placeholder(R.drawable.iconfont_photo)
                        .into(img);
            }

            if (v.getParent() == null) {
                ((ViewPager) container).addView(v, 0);
            } else {
                ((ViewGroup) v.getParent()).removeView(listViews.get(position));
                ((ViewPager) container).addView(v);

            }
            return v;
        }
    }

}
