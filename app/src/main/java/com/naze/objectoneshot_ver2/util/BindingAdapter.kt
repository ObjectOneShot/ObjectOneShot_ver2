package com.naze.objectoneshot_ver2.util

import android.annotation.SuppressLint
import android.graphics.Paint
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.mackhartley.roundedprogressbar.RoundedProgressBar
import com.naze.objectoneshot_ver2.R
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


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

@BindingAdapter("app:setRoundProgressDrawableColorComplete")
fun setColorCompleteProgressBar(view: RoundedProgressBar, value: Double) {
    if (value == 100.0) {
        view.setProgressDrawableColor(ContextCompat.getColor(view.context,R.color.point3))
    } else {
        view.setProgressDrawableColor(ContextCompat.getColor(view.context,R.color.grey_400))
    }
}

@BindingAdapter("date")
fun setTextDate(view: TextView, date: Long) {
    val dateFormat = SimpleDateFormat("yy-MM-dd", Locale.getDefault())
    val timeDiffInMillis = getCurrentDate() - date
    val daysLeft = TimeUnit.MILLISECONDS.toDays(timeDiffInMillis)

    Log.d("TEST_objective_item","$daysLeft")

    val spannableString =
        SpannableString("D-$daysLeft / ${dateFormat.format(date)}")

    spannableString.setSpan(
        ForegroundColorSpan(ContextCompat.getColor(view.context, R.color.error)),
        0,
        "D-$daysLeft".length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    spannableString.setSpan(
        StyleSpan(Typeface.BOLD),
        0,
        "D-$daysLeft".length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    spannableString.setSpan(
        ForegroundColorSpan(ContextCompat.getColor(view.context, R.color.grey_600)),
        "D-$daysLeft".length + 3,
        spannableString.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    view.text = spannableString
}

@BindingAdapter("progress","date")
fun TextView.setTextDateComplete(progress:Double, date: Long ) {
    val dateFormat = SimpleDateFormat("yy-MM-dd", Locale.getDefault())
    val timeDiffInMillis = getCurrentDate() - date
    val daysLeft = TimeUnit.MILLISECONDS.toDays(timeDiffInMillis)

    Log.d("TEST_objective_item","$daysLeft")

    if (progress >= 100.0) {
        val spannableString =
            SpannableString("달성 / ${dateFormat.format(date)}")

        spannableString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this.context, R.color.error)),
            0,
            2,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            2,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this.context, R.color.grey_600)),
            5,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        this.text = spannableString
    } else {
        val spannableString =
            SpannableString("미달성 / ${dateFormat.format(date)}")

        spannableString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this.context, R.color.secondary)),
            0,
            3,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            3,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this.context, R.color.grey_600)),
            6,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        this.text = spannableString
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

@BindingAdapter("enableEdit")
fun EditText.enableEdit(enable: Boolean) {
    Log.d("TEST_BindingAdapter","enable: $enable")
    isEnabled = !enable
    isFocusable = !enable
    if (enable) {
        this.paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        this.paintFlags = this.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}
