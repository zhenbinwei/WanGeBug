package com.example.weizhenbin.wangebug.tools;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.tools.interfaces.IDatePickerResult;
import com.example.weizhenbin.wangebug.tools.interfaces.ITimePickerResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by weizhenbin on 2018/9/7.
 */

public class DateTool {

    //日期转时间戳
    public static long getStringToDate(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.CHINESE);
        Date date = new Date();
        try{
            date = dateFormat.parse(dateString);
        } catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }


    public static void showDateDialog(Context context, final IDatePickerResult iDatePickerResult){
        View view= View.inflate(context, R.layout.dialog_datepicker_layout,null);
        final DatePicker datePicker=view.findViewById(R.id.dp_date);
        Dialog alertDialog = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                .setView(view)
                .setNegativeButton(R.string.cancel_string, null)
                .setPositiveButton(R.string.confirm_string, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (iDatePickerResult!=null){
                            iDatePickerResult.onDateResult(datePicker.getYear(),datePicker.getMonth()+1,datePicker.getDayOfMonth());
                        }
                    }
                })
                .create();
        alertDialog.show();
    }
    public static void showTimeDialog(Context context , final ITimePickerResult iTimePickerResult){
        View view=View.inflate(context,R.layout.dialog_timepicker_layout,null);
        final TimePicker timePicker=view.findViewById(R.id.tp_date);
        Dialog alertDialog = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                .setView(view)
                .setNegativeButton(R.string.cancel_string, null)
                .setPositiveButton(R.string.confirm_string, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (iTimePickerResult!=null){
                            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                                iTimePickerResult.onTimeResult(timePicker.getHour(), timePicker.getMinute());
                            }else {
                                iTimePickerResult.onTimeResult(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                            }
                        }
                    }
                })
                .create();
        alertDialog.show();
    }
}
