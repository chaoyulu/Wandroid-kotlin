package com.cyl.wandroid.listener

import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

abstract class AppbarStateChangeListener : AppBarLayout.OnOffsetChangedListener {
    enum class State {
        EXPANDED, COLLAPSED, IDLE
    }

    private var currentState: State = State.IDLE

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        when {
            verticalOffset == 0 -> {
                if (currentState != State.EXPANDED) {
                    onStateChanged(appBarLayout, State.EXPANDED);
                }
                currentState = State.EXPANDED;
            }
            abs(verticalOffset) >= appBarLayout.totalScrollRange -> {
                if (currentState != State.COLLAPSED) {
                    onStateChanged(appBarLayout, State.COLLAPSED);
                }
                currentState = State.COLLAPSED;
            }
            else -> {
                if (currentState != State.IDLE) {
                    onStateChanged(appBarLayout, State.IDLE);
                }
                currentState = State.IDLE;
            }
        }
    }

    abstract fun onStateChanged(appBarLayout: AppBarLayout, state: State)
}