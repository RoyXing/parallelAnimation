package com.xingzy;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

public class ParallelContainer extends FrameLayout implements ViewPager.OnPageChangeListener {

    private List<ParallelFragment> fragments;
    private ImageView iv_man;
    private ParallelPagerAdapter adapter;

    public ParallelContainer(Context context) {
        super(context);
    }

    public ParallelContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallelContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setIvMan(ImageView iv_man) {
        this.iv_man = iv_man;
    }

    public void setUp(int... childIds) {
        fragments = new ArrayList<>();
        for (int i = 0; i < childIds.length; i++) {
            ParallelFragment fragment = new ParallelFragment();
            Bundle args = new Bundle();
            args.putInt("layoutId", childIds[i]);
            fragment.setArguments(args);
            fragments.add(fragment);
        }

        ViewPager viewPager = new ViewPager(getContext());
        viewPager.setId(R.id.parallax_pager);
        viewPager.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        SplashActivity activity = (SplashActivity) getContext();
        adapter = new ParallelPagerAdapter(activity.getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);
        addView(viewPager);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //动画
        int containerWidth = getWidth();
        ParallelFragment outFragment = null;
        try {
            outFragment = fragments.get(position - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (outFragment != null) {
            List<View> views = outFragment.getParallelViews();
            for (View view : views) {
                ParallelViewTag viewTag = (ParallelViewTag) view.getTag();
                if (viewTag == null) {
                    continue;
                }
                ViewHelper.setTranslationX(view, (containerWidth - positionOffsetPixels) * viewTag.xIn);
                ViewHelper.setTranslationY(view, (containerWidth - positionOffsetPixels) * viewTag.yIn);
            }
        }

        ParallelFragment inFragment = null;
        try {
            inFragment = fragments.get(position);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (inFragment != null) {
            List<View> views = inFragment.getParallelViews();
            for (View view : views) {
                ParallelViewTag viewTag = (ParallelViewTag) view.getTag();
                if (viewTag == null) {
                    continue;
                }
                //view从原始位置开始向上移动，translation应该为负值
                ViewHelper.setTranslationX(view, (0 - positionOffsetPixels) * viewTag.xOut);
                ViewHelper.setTranslationY(view, (0 - positionOffsetPixels) * viewTag.yOut);
            }
        }
    }

    @Override
    public void onPageSelected(int i) {
        if (i == adapter.getCount() - 1) {
            iv_man.setVisibility(INVISIBLE);
        } else {
            iv_man.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        AnimationDrawable animation = (AnimationDrawable) iv_man.getBackground();
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                animation.start();
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                animation.stop();
                break;
            default:
                break;
        }
    }
}
