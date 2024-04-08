package com.example.pme.act

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class ItemDecoration(private val horizontalSpace: Int=20, private val verticalSpace: Int=20) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = horizontalSpace
        outRect.right = horizontalSpace
        outRect.top = verticalSpace
        outRect.bottom = verticalSpace
    }
}
