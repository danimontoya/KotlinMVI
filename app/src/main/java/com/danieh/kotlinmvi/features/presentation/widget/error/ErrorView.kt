package com.danieh.kotlinmvi.features.presentation.widget.error

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.danieh.kotlinmvi.R
import kotlinx.android.synthetic.main.view_error.view.*

/**
 * Created by danieh on 20/04/2019.
 */
class ErrorView : ConstraintLayout {

    var errorListener: ErrorListener? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.view_error, this)
        button_try_again.setOnClickListener { errorListener?.onTryAgainClicked() }
    }

}