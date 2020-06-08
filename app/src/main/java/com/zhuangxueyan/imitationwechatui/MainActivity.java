package com.zhuangxueyan.imitationwechatui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private List<Fragment> fragments = new ArrayList<>();
    private List<TextView> textViews = new ArrayList<>();
    private List<ImageView> imageViews = new ArrayList<>();
    private List<Chat> chats = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initSystemBar(true);

        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }


        viewPager = findViewById(R.id.view_pager);
        linearLayout = findViewById(R.id.tab_layout);
        final ImageView imageView1 = findViewById(R.id.tab_img1);
        final ImageView imageView2 = findViewById(R.id.tab_img2);
        final ImageView imageView3 = findViewById(R.id.tab_img3);
        final ImageView imageView4 = findViewById(R.id.tab_img4);
        final TextView textView1 = findViewById(R.id.tab_text1);
        final TextView textView2 = findViewById(R.id.tab_text2);
        final TextView textView3 = findViewById(R.id.tab_text3);
        final TextView textView4 = findViewById(R.id.tab_text4);

        textViews.add(textView1);
        textViews.add(textView2);
        textViews.add(textView3);
        textViews.add(textView4);

        imageViews.add(imageView1);
        imageViews.add(imageView2);
        imageViews.add(imageView3);
        imageViews.add(imageView4);





        for (int i = 0;i < linearLayout.getChildCount();i++){
            final LinearLayout linearLayoutChild = (LinearLayout) linearLayout.getChildAt(i);
            final ImageView imageView = (ImageView) linearLayoutChild.getChildAt(0);
            final TextView textView = (TextView) linearLayoutChild.getChildAt(1);
            if (i == 0){
                imageView.setSelected(true);
                textView.setTextColor(getResources().getColor(R.color.c1));
            }
            linearLayoutChild.setTag(i);
            linearLayoutChild.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    viewPager.setCurrentItem((Integer) linearLayoutChild.getTag());
                }
            });
        }

//        View view1 = getLayoutInflater().inflate(R.layout.chat, null);
//        View view2 = getLayoutInflater().inflate(R.layout.address_book, null);
//        View view3 = getLayoutInflater().inflate(R.layout.find, null);
//        View view4 = getLayoutInflater().inflate(R.layout.me, null);
//
//        views.add(view1);
//        views.add(view2);
//        views.add(view3);
//        views.add(view4);


        PagerFragment pagerFragment1 = PagerFragment.getInstance(0);
        PagerFragment pagerFragment2 = PagerFragment.getInstance(1);
        PagerFragment pagerFragment3 = PagerFragment.getInstance(2);
        PagerFragment pagerFragment4 = PagerFragment.getInstance(3);

        fragments.add(pagerFragment1);
        fragments.add(pagerFragment2);
        fragments.add(pagerFragment3);
        fragments.add(pagerFragment4);


        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int j = 0;j < fragments.size();j++){
                    textViews.get(j).setTextColor(getResources().getColor(R.color.c0));
                    imageViews.get(j).setSelected(false);

                }
                textViews.get(position).setTextColor(getResources().getColor(R.color.c1));
                imageViews.get(position).setSelected(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    public void initSystemBar(Boolean isLight) {
        if (Build.VERSION.SDK_INT >= 21) {
            //LAYOUT_FULLSCREEN 、LAYOUT_STABLE：让应用的主体内容占用系统状态栏的空间；
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
            Window window = getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            if (isLight) {
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            } else {
                window.setStatusBarColor(getResources().getColor(R.color.colorAccent));
            }

            //状态栏颜色接近于白色，文字图标变成黑色
            View decor = window.getDecorView();
            int ui = decor.getSystemUiVisibility();
            if (isLight) {
                //light --> a|=b的意思就是把a和b按位或然后赋值给a,   按位或的意思就是先把a和b都换成2进制，然后用或操作，相当于a=a|b
                ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                //dark  --> &是位运算里面，与运算,  a&=b相当于 a = a&b,  ~非运算符
                ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decor.setSystemUiVisibility(ui);
        }
    }
}
