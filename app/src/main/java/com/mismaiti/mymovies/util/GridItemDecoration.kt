package com.mismaiti.mymovies.util

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.mismaiti.mymovies.R

class GridItemDecoration(
    private val space: Int,
    private val outSpace: Int)
    : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val positionItem = parent.getChildAdapterPosition(view)
        val lastItem = parent.adapter?.itemCount?.minus(1)
        val grid = parent.layoutManager as androidx.recyclerview.widget.GridLayoutManager

        positionItem.let { position ->
                if (grid.canScrollHorizontally()) {
                    when (position) {
                        0 -> {
                            outRect.left = outSpace
                            outRect.right = space
                        }
                        lastItem -> {
                            outRect.left = space
                            outRect.right = outSpace
                        }
                        else -> {
                            outRect.left = space
                            outRect.right = space
                        }
                    }
                }
                else {
                    when (position) {
                        1 -> {
                            outRect.left = 0
                            outRect.right = 0
                        }
                        else -> {
                            outRect.left = space
                            outRect.right = space
                        }
                    }
                }

            outRect.top = space
            outRect.bottom = space
        }
    }
}