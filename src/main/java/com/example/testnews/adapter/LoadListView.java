package com.example.testnews.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.testnews.R;


/**
 * Created by Administrator on 2016/8/2.
 */

public class LoadListView extends ListView implements AbsListView.OnScrollListener {
    private int first, end, totalitem;
    private LayoutInflater inflater;
    private View footer, header;
    private boolean isLoading;
    Listinterface listinterface;
    RefreshListener refreshListener;
    private int headerHeight;
    private final static int HIDE_HEADER = 0;
    private final static int PULL = 1;
    private final static int RELESE = 2;
    private final static int REFRESHING = 3;
    int state = 0;
    int startY;
    private int scrollState;

    public LoadListView(Context context) {
        super(context);
        initView(context);

    }

    public LoadListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        inflater = LayoutInflater.from(context);
        footer = inflater.inflate(R.layout.footerlayout, null);
        header = inflater.inflate(R.layout.headerlayout, null);
        measureView(header);//测量header
        headerHeight = header.getMeasuredHeight();
        topPadding(-headerHeight);//将header这个view偏移至屏幕外一个高度
        Log.d("ff", "initView: " + headerHeight);
        footer.findViewById(R.id.loading_layout).setVisibility(GONE);
        this.addFooterView(footer);
        this.addHeaderView(header);
        this.setOnScrollListener(this);//添加scrollListener监听器

    }

    /**
     * 设置偏移的距离
     * @param i
     */
    private void topPadding(int i) {
        header.setPadding(header.getPaddingLeft(), i, header.getPaddingRight(), header.getPaddingBottom());
        header.invalidate();
    }

    /**
     * 通知父布局，占用的高度
     * @param view
     */
    private void measureView(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width = getChildMeasureSpec(0, 0, p.width);
        int height;
        int tempHeight = p.height;
        if (tempHeight > 0) {
            height = MeasureSpec.makeMeasureSpec(tempHeight, MeasureSpec.EXACTLY);
        } else {
            height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        view.measure(width, height);
    }


    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        scrollState = i;
        //滑到最底部时进行加载新的数据
        if (end == totalitem && i == SCROLL_STATE_IDLE) {
            footer.findViewById(R.id.loading_layout).setVisibility(VISIBLE);

            listinterface.onLoad();
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        end = i + i1;
        totalitem = i2;
        first = i;

    }

    public void setListinterface(Listinterface listinterface) {
        this.listinterface = listinterface;
    }

    public interface Listinterface {
        void onLoad();
    }

    public void setRefreshListener(RefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    public interface RefreshListener {
        void onRefresh();
    }

    /**
     * 重写onTouchEven事件，对滑动，向下，向上定义
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                if (first == 0) {
                    state = PULL;

                    startY = (int) ev.getY();
                }
                break;
            case MotionEvent.ACTION_MOVE:

                onMove(ev);
                break;
            case MotionEvent.ACTION_UP:

                if (state == PULL) {
                    state = HIDE_HEADER;
                    onRefreshView();
                } else if (state == RELESE) {

                    state = REFRESHING;
                    onRefreshView();
                    loadComplete();
                }

                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 读取完毕时的方法
     */
    private void loadComplete() {
        refreshListener.onRefresh();
        state = HIDE_HEADER;
        onRefreshView();
    }

    /**
     * hudong
     * @param ev
     */
    private void onMove(MotionEvent ev) {
        if (first != 0) {
            return;
        }
        int space = (int) (ev.getY() - startY);
        int toppading = space - headerHeight;

        switch (state) {
            case HIDE_HEADER:
                if (space > 0) {
                    state = PULL;
                    onRefreshView();

                }
                break;
            case PULL:
                topPadding(toppading);
                if (space >= headerHeight + 20 && scrollState == SCROLL_STATE_TOUCH_SCROLL) {

                    state = RELESE;
                    onRefreshView();

                }

                break;

            case RELESE:
                topPadding(toppading);
                if (space < headerHeight + 20) {

                    state = PULL;
                    onRefreshView();

                } else if (space <= 0) {

                    state = HIDE_HEADER;
                    onRefreshView();
                }

                break;
        }


    }

    /**
     * 刷新界面View
     */
    private void onRefreshView() {
        ImageView arrow = (ImageView) header.findViewById(R.id.arrow);
        TextView tips = (TextView) header.findViewById(R.id.tips);
        ProgressBar pbar = (ProgressBar) header.findViewById(R.id.header_progressbar);
        RotateAnimation anim = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(300);
        anim.setFillAfter(true);
        RotateAnimation anim2 = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim2.setDuration(300);
        anim2.setFillAfter(true);

        switch (state) {
            case HIDE_HEADER:
                arrow.clearAnimation();
                topPadding(-headerHeight);
                break;
            case PULL:
                arrow.clearAnimation();
                arrow.setVisibility(VISIBLE);
                pbar.setVisibility(GONE);
                tips.setText("下拉刷新");
                arrow.startAnimation(anim2);
                break;
            case RELESE:
                arrow.clearAnimation();
                arrow.setVisibility(VISIBLE);
                pbar.setVisibility(GONE);
                tips.setText("释放刷新");
                arrow.startAnimation(anim);
                break;
            case REFRESHING:
                arrow.clearAnimation();
                topPadding(50);
                arrow.setVisibility(GONE);
                pbar.setVisibility(VISIBLE);
                tips.setText("正在刷新");

                break;

        }


    }

}
