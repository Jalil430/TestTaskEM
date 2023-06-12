package com.example.testtaskem.view.ui.custom_view

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import com.example.domain.common.Constants.SHARED_PREF
import com.example.domain.common.Constants.SHARED_PREF_LOCATION
import com.example.testtaskem.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationAndDateCustomView : FrameLayout {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREF, Activity.MODE_PRIVATE)

    private var tvLocation: TextView? = null
    private var tvDate: TextView? = null
    private var txtLocation = sharedPreferences.getString(
        SHARED_PREF_LOCATION,
        "Нет доступа к геолокации пользователя"
    ) ?: "Нет доступа к геолокации пользователя"
    private var txtDate = "dsfsdfsdf"

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context)
    }

    private fun initView(context: Context) {
        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        LayoutInflater.from(context).inflate(R.layout.custom_view_location_and_date, this, true)
        tvLocation = findViewById(R.id.tvLocation)
        tvDate = findViewById(R.id.tvDate)
        if (txtLocation.isNotEmpty()) {
            tvLocation?.text = txtLocation
        }
        if (txtDate.isNotEmpty()) {
            tvDate?.text = txtDate
        }
    }
}