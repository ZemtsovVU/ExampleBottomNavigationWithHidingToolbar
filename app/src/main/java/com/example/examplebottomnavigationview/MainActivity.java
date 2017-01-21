package com.example.examplebottomnavigationview;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.example.examplebottomnavigationview.one.OneFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Toolbar toolbar;
    private NestedScrollView scrollView;
    private FrameLayout containerViewGroup;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initViews();
        configureViews();
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        scrollView = (NestedScrollView) findViewById(R.id.scroll_view);
        containerViewGroup = (FrameLayout) findViewById(R.id.container_view_group);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
    }

    private void initViews() {
        setSupportActionBar(toolbar);
        showFragment(OneFragment.newInstance());
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewGroup.getId(), fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    private void configureViews() {
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_MOVE:
                        try {
                            int halfHistorySize = event.getHistorySize() / 2;
                            float currentY = event.getY();
                            float lastY = event.getHistoricalY(halfHistorySize);

                            if (currentY - lastY > 0) {
                                Log.d(TAG, "onTouch: up to down");
                                // show
                                if (!bottomNavigationView.isShown()) {
                                    bottomNavigationView.setVisibility(View.VISIBLE);
                                }
                            } else {
                                Log.d(TAG, "onTouch: down to up");
                                // hide
                                if (bottomNavigationView.isShown()) {
                                    bottomNavigationView.setVisibility(View.GONE);
                                }
                            }
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        }
                        return scrollView.onTouchEvent(event);
                    default:
                        return scrollView.onTouchEvent(event);
                }
            }
        });
    }
}
