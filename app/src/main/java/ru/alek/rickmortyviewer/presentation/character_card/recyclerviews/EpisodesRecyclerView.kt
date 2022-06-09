package ru.alek.rickmortyviewer.presentation.character_card.recyclerviews

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * Custom RecyclerView for horizontal nested scrolling inside horizontal parent RecyclerView
 *
 * Пользовательский RecyclerView для горизонтальной вложенной прокрутки внутри горизонтального родительского RecyclerView
 *
 */
class EpisodesRecyclerView : RecyclerView {
    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    ) {
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        parent.requestDisallowInterceptTouchEvent(true)
        return super.onInterceptTouchEvent(event)
    }
}