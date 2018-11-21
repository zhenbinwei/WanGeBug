package com.example.weizhenbin.wangebug.tools

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import com.example.weizhenbin.wangebug.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by weizhenbin on 2018/9/7.
 */

object DateTool {

    //日期转时间戳
    fun getStringToDate(dateString: String, pattern: String): Long {
        val dateFormat = SimpleDateFormat(pattern, Locale.CHINESE)
        var date = Date()
        try {
            date = dateFormat.parse(dateString)
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        return date.time
    }

    //日期转时间戳
    fun getDateToString(date: Long, pattern: String): String {
        val dateFormat = SimpleDateFormat(pattern, Locale.CHINESE)
        return dateFormat.format(Date(date))
    }

    fun showDateDialog(context: Context, iDatePickerResult:(( year:Int, monthOfYear:Int, dayOfMonth:Int)->Unit)?) {
        val view = View.inflate(context, R.layout.dialog_datepicker_layout, null)
        val datePicker = view.findViewById<DatePicker>(R.id.dp_date)
        val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                .setView(view)
                .setNegativeButton(R.string.cancel_string, null)
                .setPositiveButton(R.string.confirm_string) { _, _ ->
                    iDatePickerResult?.let { it(datePicker.year, (datePicker.month + 1), (datePicker.dayOfMonth)) }
                }
                .create()
        alertDialog.show()
    }

    fun showTimeDialog(context: Context, iTimePickerResult:((hourOfDay:Int, minute:Int)->Unit)?) {
        val view = View.inflate(context, R.layout.dialog_timepicker_layout, null)
        val timePicker = view.findViewById<TimePicker>(R.id.tp_date)
        val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                .setView(view)
                .setNegativeButton(R.string.cancel_string, null)
                .setPositiveButton(R.string.confirm_string) { _, _ ->
                    if (iTimePickerResult != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            iTimePickerResult(timePicker.hour, timePicker.minute)
                        } else {
                            iTimePickerResult(timePicker.currentHour, timePicker.currentMinute)
                        }
                    }
                }
                .create()
        alertDialog.show()
    }
}
