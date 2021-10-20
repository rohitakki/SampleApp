package com.trending.app.modules.main

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessRecyclerViewScrollListener : RecyclerView.OnScrollListener() {
    private var totalItemCount = 0
    private var lastVisibleItem = 0
    private var scrollingListener: ScrollingListener? = null
    abstract fun onLoadMore()
    protected abstract fun firstItemPosition(pos: Int)

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private val visibleThreshold: Int = 10

    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        if (dy != 0) {
            if (scrollingListener != null) scrollingListener!!.scrollingStarted()
        }
        val layoutManager = view.layoutManager as LinearLayoutManager?
        val firstVisibleItemPosition = layoutManager!!.findFirstVisibleItemPosition()
        firstItemPosition(firstVisibleItemPosition)
        if (dy > 0) {
            totalItemCount = view.layoutManager!!.itemCount
            if (view.layoutManager is GridLayoutManager) {
                lastVisibleItem =
                    (view.layoutManager as GridLayoutManager?)!!.findLastVisibleItemPosition()
            } else if (view.layoutManager is LinearLayoutManager) {
                lastVisibleItem =
                    (view.layoutManager as LinearLayoutManager?)!!.findLastVisibleItemPosition()
            }
            if (totalItemCount <= lastVisibleItem + visibleThreshold) {
                onLoadMore()
            }
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (scrollingListener != null) scrollingListener!!.scrollingStopped()
        }
        super.onScrollStateChanged(recyclerView, newState)
    }

    interface ScrollingListener {
        fun scrollingStarted()
        fun scrollingStopped()
    }
}