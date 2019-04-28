package com.xingzy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ParallelFragment extends Fragment {

    //此fragment上所有的需要实现视差动画的视图
    private List<View> parallelViews = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        int layoutId = args.getInt("layoutId");

        ParallelLayoutInflater layoutInflater = new ParallelLayoutInflater(inflater, getContext(), this);
        return layoutInflater.inflate(layoutId, null);
    }

    public List<View> getParallelViews() {
        return parallelViews;
    }
}
