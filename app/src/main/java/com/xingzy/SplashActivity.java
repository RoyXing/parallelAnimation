package com.xingzy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;


public class SplashActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParallelContainer container = findViewById(R.id.parallel_container);

        container.setUp(R.layout.view_intro_1,
                R.layout.view_intro_2,
                R.layout.view_intro_3,
                R.layout.view_intro_4,
                R.layout.view_intro_5,
                R.layout.view_login);

        ImageView imageView = findViewById(R.id.iv_man);
        imageView.setBackgroundResource(R.drawable.man_run);
        container.setIvMan(imageView);
    }
}
