package com.gl4.tp2

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class NoSpaceItemDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = 0
        outRect.top = 0
        outRect.right = 0
        outRect.bottom = 0
    }
}