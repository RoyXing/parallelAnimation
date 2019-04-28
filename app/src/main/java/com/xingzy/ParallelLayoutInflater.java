package com.xingzy;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class ParallelLayoutInflater extends LayoutInflater {

    private ParallelFragment fragment;

    public ParallelLayoutInflater(LayoutInflater original, Context newContext) {
        super(original, newContext);
    }

    protected ParallelLayoutInflater(LayoutInflater original, Context newContext, ParallelFragment fragment) {
        super(original, newContext);
        this.fragment = fragment;
        setFactory2(new ParallelFactory(this));
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new ParallelLayoutInflater(this, newContext);
    }

    class ParallelFactory implements Factory2 {

        private final String[] prefixs = {
                "android.widget.",
                "android.view.",
                "android.support.v7.widget."
        };

        private int[] attrIds = {
                R.attr.a_in,
                R.attr.a_out,
                R.attr.x_in,
                R.attr.x_out,
                R.attr.y_in,
                R.attr.y_out
        };

        private LayoutInflater inflater;

        public ParallelFactory(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        @Override
        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
            Log.e("roy", name);
            View view = createMyView(name, context, attrs);

            if (view == null)
                return null;

            TypedArray array = context.obtainStyledAttributes(attrs, attrIds);
            if (array != null && array.length() > 0) {
                ParallelViewTag viewTag = new ParallelViewTag();
                viewTag.alphaIn = array.getFloat(0, 0f);
                viewTag.alphaOut = array.getFloat(1, 0f);
                viewTag.xIn = array.getFloat(2, 0f);
                viewTag.xOut = array.getFloat(3, 0f);
                viewTag.yIn = array.getFloat(4, 0f);
                viewTag.yOut = array.getFloat(5, 0f);
                view.setTag(viewTag);
            }
            fragment.getParallelViews().add(view);
            array.recycle();
            return view;
        }

        /**
         * @param name    系统控件 TextView 自定义view 完成包名类名
         * @param context
         * @param attrs
         * @return
         */
        private View createMyView(String name, Context context, AttributeSet attrs) {
            if (name.contains(".")) {
                return reflectView(name, "", context, attrs);
            } else {
                for (String prefix : prefixs) {
                    View view = reflectView(name, prefix, context, attrs);
                    if (view != null) {
                        return view;
                    }
                }
            }
            return null;
        }

        private View reflectView(String name, String prefix, Context context, AttributeSet attrs) {
            try {
                //通过系统的inflater创建视图，读取系统的属性
                return inflater.createView(name, prefix, attrs);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            return null;
        }
    }
}
