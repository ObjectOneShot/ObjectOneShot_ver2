package com.naze.objectoneshot_ver2.util

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.mackhartley.roundedprogressbar.RoundedProgressBar
import com.naze.objectoneshot_ver2.R
import java.sql.Date


@BindingAdapter("android:progressDouble")
fun setProgressDouble(view: ProgressBar, value: Double) {
    view.progress = if (value > 0) value.toInt() else 0
}

@BindingAdapter("android:textDueDate")
fun setDueDate(view: TextView, value: Long) {
    view.text = "마감일 : 23-04-07"
}

@BindingAdapter("isSelected")
fun setIsSelected(view: View, value: Boolean) {
    Log.d("Status Setting","$value")
    view.isSelected = value
}

@BindingAdapter("app:setRoundProgressDrawableColor")
fun setColorProgressBar(view: RoundedProgressBar, value: Double) {
    when (value) {
        in 0.0..30.0 -> {
            view.setProgressDrawableColor(ContextCompat.getColor(view.context,R.color.point1))
        }
        in 31.0..60.0 -> {
            view.setProgressDrawableColor(ContextCompat.getColor(view.context,R.color.point2))
        }
        in 61.0..100.0 -> {
            view.setProgressDrawableColor(ContextCompat.getColor(view.context,R.color.point3))
        }
    }
}

@BindingAdapter("app:setRoundProgress")
fun setRoundProgressBar(view: RoundedProgressBar, value: Double) {
    view.setProgressPercentage(value.toDouble())
}

@SuppressLint("SetTextI18n")
@BindingAdapter("startDate","endDate")
fun setTextDateRange(view: TextView, startDate: Long, endDate: Long) {
    Log.d("TEST_BindingAdapter","$startDate, $endDate")
    view.text = "${Date(startDate)} ~ ${Date(endDate)}"
}

