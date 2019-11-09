package org.oppia.app.databinding

import android.text.format.DateUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.util.*

/**
 * Allows binding drawables to an [ImageView] via "android:src". Source: https://stackoverflow.com/a/35809319/3689782.
 */
@BindingAdapter("profile:date")
fun setTextWithDate(textView: TextView, timeMs: Long) {
  val dateText = "Last used " + DateUtils.getRelativeTimeSpanString(timeMs, Date().time, DateUtils.MINUTE_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE)
  textView.text = dateText
}
