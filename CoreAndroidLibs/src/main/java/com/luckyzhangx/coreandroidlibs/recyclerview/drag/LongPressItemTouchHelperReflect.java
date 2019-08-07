package com.luckyzhangx.coreandroidlibs.recyclerview.drag;

import android.os.SystemClock;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

// Created by luckyzhangx on 2019-08-07.

/**
 * ItemTouchHelper 长按事件通过 GestureDetector 实现，
 * 长按任务启动之前收到 up 或者 cancel 事件会取消长按任务。
 * 侧滑控件通常都需要 requestDisallowIntercept 导致 RecyclerView 收不到后续的 up 和 cancel 事件，
 * 导致侧滑完成后 ViewHolder 也会被选中。
 * <p>
 * 解决方案：requestDisallowIntercept 给 GestureDetector 发送 cancel 事件
 */
public class LongPressItemTouchHelperReflect extends ItemTouchHelper {


    private RecyclerView.OnItemTouchListener touchListener = new RecyclerView.OnItemTouchListener() {

        private GestureDetectorCompat gestureDetectorCompat;

        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            if (gestureDetectorCompat == null) {
                try {
                    gestureDetectorCompat = (GestureDetectorCompat) ItemTouchHelper.class.getDeclaredField("mGestureDetector").get(LongPressItemTouchHelperReflect.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (gestureDetectorCompat != null) {
                long now = SystemClock.uptimeMillis();
                gestureDetectorCompat.onTouchEvent(
                        MotionEvent.obtain(now, now,
                                MotionEvent.ACTION_CANCEL, 0.0f, 0.0f, 0)
                );
            }
        }
    };

    public LongPressItemTouchHelperReflect(Callback callback) {
        super(callback);
    }


    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) {
        if (recyclerView != null) {
            recyclerView.removeOnItemTouchListener(touchListener);
        }
        super.attachToRecyclerView(recyclerView);
        recyclerView.addOnItemTouchListener(touchListener);

    }
}
