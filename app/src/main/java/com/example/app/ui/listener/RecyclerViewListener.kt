package com.example.app.ui.listener

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.example.app.ui.adapter.CommonAdapter

class RecyclerViewListener(
    context: Context,
    private val callback: (Int) -> Unit
) : RecyclerView.SimpleOnItemTouchListener() {

    private val detector = GestureDetector(
        context,
        object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                return true
            }
        }
    )

    override fun onInterceptTouchEvent(view: RecyclerView, event: MotionEvent): Boolean {
        view.findChildViewUnder(event.x, event.y)?.let { child ->
            if (detector.onTouchEvent(event)) {
                callback.invoke(view.getChildAdapterPosition(child))
            }
        }

        return false
    }
}

inline fun <reified T> RecyclerView.setClickListener(noinline callback: (T) -> Unit) {
    addOnItemTouchListener(
        RecyclerViewListener(context) { position ->
            ((adapter as CommonAdapter<*>).itemAt(position) as? T)
                ?.let { callback.invoke(it) }
        }
    )
}