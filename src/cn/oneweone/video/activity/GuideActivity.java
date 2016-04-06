package cn.oneweone.video.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import cn.oneweone.video.R;
import cn.oneweone.video.util.preference.Preferences;
import cn.oneweone.video.util.preference.PreferencesUtils;
import cn.oneweone.video.widget.view.PointWidget;

/***
 * 第一次进入软件时的软件介绍界面
 * 
 * @author sailor
 * 
 */
public class GuideActivity extends Activity {
    private ArrayList<View> ViewPagerList;
    private PointWidget pw;
    private PreferencesUtils preferencesUtils;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        pw = (PointWidget) findViewById(R.id.litu_welcome_ponit);

        preferencesUtils = new PreferencesUtils(this, Preferences.CONFIG_FILE);
        preferencesUtils.putBoolean(Preferences.FIRST_START, false);

        ViewPagerList = new ArrayList<View>();
        /**
         * 页面图片
         */
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        ImageView view1 = new ImageView(this);
        ImageView view2 = new ImageView(this);
        View view3 = getLayoutInflater().inflate(R.layout.view_guide_three, null);

        // 按钮
        Button button = (Button) view3.findViewById(R.id.go_home);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showNextActivity();
            }
        });

        ImageView view4 = new ImageView(this);
        view1.setImageResource(R.drawable.ic_launcher);
        view2.setImageResource(R.drawable.ic_launcher);
        ViewPagerList.add(view1);
        ViewPagerList.add(view2);
        ViewPagerList.add(view3);
        ViewPagerList.add(view4);

        pw.setPointCount(ViewPagerList.size() - 1);
        viewPager.setAdapter(pa);
        viewPager.setOnPageChangeListener(onPageChangeListener);
    }

    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {

        @Override
        public void onPageSelected(int arg0) {
            pw.setPoint(arg0);
            if (arg0 == ViewPagerList.size() - 1) {
                return;
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (position == ViewPagerList.size() - 2 && positionOffset > 0) {
                showNextActivity();
            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

    private PagerAdapter pa = new PagerAdapter() {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return ViewPagerList.size();
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager) container).addView(ViewPagerList.get(position));
            return ViewPagerList.get(position);
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(ViewPagerList.get(position));
        }

        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub

        }
    };

    public void showNextActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
