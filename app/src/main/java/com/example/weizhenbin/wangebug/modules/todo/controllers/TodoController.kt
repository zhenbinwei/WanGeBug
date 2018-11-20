package com.example.weizhenbin.wangebug.modules.todo.controllers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.TextUtils
import com.example.weizhenbin.wangebug.base.App
import com.example.weizhenbin.wangebug.base.DataResult
import com.example.weizhenbin.wangebug.modules.todo.alarm.AlarmReceiver
import com.example.weizhenbin.wangebug.modules.todo.entity.TBAlarmMapBean
import com.example.weizhenbin.wangebug.modules.todo.entity.TBAlarmMapBean_
import com.example.weizhenbin.wangebug.modules.todo.entity.TBTodoBean
import com.example.weizhenbin.wangebug.modules.todo.entity.TBTodoBean_
import rx.Observable
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by weizhenbin on 18/9/8.
 */

object TodoController {


    /**
     * 获取闹钟个数
     */
    fun remindCount(): Long{
        val todoBeanBox = App.app.boxStore.boxFor(TBTodoBean::class.java)
        return todoBeanBox.query().equal(TBTodoBean_.todoRemind, 1).build().count()
    }

    /**
     * 获取最后添加的数据 获取最大id
     */

    fun getLastId(): Long{
        val tbAlarmMapBeanBox = App.app.boxStore.boxFor(TBAlarmMapBean::class.java)
        val alarmMapBean = tbAlarmMapBeanBox.query().orderDesc(TBAlarmMapBean_.id).build().findFirst()
        return alarmMapBean?.id ?: 0
    }


    /***
     * 存储todo
     */
    fun saveTodo(tbTodoBean: TBTodoBean): Long {
        val todoBeanBox = App.app.boxStore.boxFor(TBTodoBean::class.java)
        return todoBeanBox.put(tbTodoBean)

    }

    /**
     * 获取todo列表
     */
    @JvmOverloads
    fun getTodoList(todoStatus: Int, page: Int, pageCount: Int = 20): List<TBTodoBean> {
        val todoBeanBox = App.app.boxStore.boxFor(TBTodoBean::class.java)
        return if (todoStatus == -1) {
            todoBeanBox.query().orderDesc(TBTodoBean_.id).build().find(((page - 1) * pageCount).toLong(), pageCount.toLong())
        } else {
            todoBeanBox.query().orderDesc(TBTodoBean_.id).equal(TBTodoBean_.todoStatus, todoStatus.toLong()).build().find()
        }
    }

    fun getTodoBean(uuid: String): TBTodoBean? {
        val todoBeanBox = App.app.boxStore.boxFor(TBTodoBean::class.java)
        return todoBeanBox.query().equal(TBTodoBean_.uuid, uuid).build().findFirst()
    }


    /**
     * 修改todo
     */
    fun updateTodoByUuid(todoBean: TBTodoBean, uuid: String): Long {
        val todoBeanBox = App.app.boxStore.boxFor(TBTodoBean::class.java)
        val tbTodoBean = todoBeanBox.query().equal(TBTodoBean_.uuid, uuid).build().findFirst()
        if (tbTodoBean != null) {
            tbTodoBean.update(todoBean)
            return todoBeanBox.put(tbTodoBean)
        }
        return -1
    }


    fun asynGetTodoList(todoStatus: Int, page: Int, pageCount: Int, dataResult: DataResult<List<TBTodoBean>>?) {
        dataResult?.onStart()
        Observable.create(Observable.OnSubscribe<List<TBTodoBean>> { subscriber ->
            val beanList = TodoController.getTodoList(todoStatus, page, pageCount)
            subscriber.onNext(beanList)
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : Observer<List<TBTodoBean>> {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
                        dataResult?.onError(e)
                    }

                    override fun onNext(todoBeen: List<TBTodoBean>) {

                        dataResult?.onSuccess(todoBeen)

                    }
                })
    }


    /**
     * 删除todo
     */
    fun delTodo(uuid: String): Long {
        val todoBeanBox = App.app.boxStore.boxFor(TBTodoBean::class.java)
        return todoBeanBox.query().equal(TBTodoBean_.uuid, uuid).build().remove()
    }


    /**
     * 设置闹钟
     */
    fun setAlarm(context: Context, tbTodoBean: TBTodoBean) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(AlarmReceiver.ACTION_KEY)
        intent.setClass(context, AlarmReceiver::class.java)
        intent.putExtra(AlarmReceiver.KEY_ID, tbTodoBean.uuid)
        intent.putExtra(AlarmReceiver.KEY_TITLE, tbTodoBean.todoTitle)
        intent.putExtra(AlarmReceiver.KEY_CONTENT, tbTodoBean.todoContent)
        var number = isExistAlarmMap(tbTodoBean.uuid)
        if (number == 0) {
            number = (getLastId() + 1).toInt()
        }
        addAlarmMap(TBAlarmMapBean(number, tbTodoBean.uuid!!))
        intent.putExtra(AlarmReceiver.KEY_NUMBER, number)
        val sender = PendingIntent.getBroadcast(
                context, number, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        //闹铃间隔， 这里设为1分钟闹一次，在第2步我们将每隔1分钟收到一次广播
        //int interval = 60 * 1000;
        //am.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, interval, sender);
        if (am != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, tbTodoBean.todoRemindTime, sender)
            } else {
                am.set(AlarmManager.RTC_WAKEUP, tbTodoBean.todoRemindTime, sender)
            }
        }
    }

    /**
     * 删除闹钟
     */
    fun cancelAlarm(context: Context, tbTodoBean: TBTodoBean) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(AlarmReceiver.ACTION_KEY)
        intent.setClass(context, AlarmReceiver::class.java)
        intent.putExtra(AlarmReceiver.KEY_ID, tbTodoBean.uuid)
        intent.putExtra(AlarmReceiver.KEY_TITLE, tbTodoBean.todoTitle)
        intent.putExtra(AlarmReceiver.KEY_CONTENT, tbTodoBean.todoContent)

        val number = getIdByKey(tbTodoBean.uuid).toInt()
        intent.putExtra(AlarmReceiver.KEY_NUMBER, number)
        val sender = PendingIntent.getBroadcast(
                context, number, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        //闹铃间隔， 这里设为1分钟闹一次，在第2步我们将每隔1分钟收到一次广播
        //int interval = 60 * 1000;
        //am.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, interval, sender);
        am.cancel(sender)
    }

    /**
     * 删除闹钟
     */
    fun cancelAlarm(context: Context, uuid: String) {

        val tbTodoBean = getTodoBean(uuid) ?: return
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(AlarmReceiver.ACTION_KEY)
        intent.putExtra(AlarmReceiver.KEY_ID, tbTodoBean.uuid)
        intent.putExtra(AlarmReceiver.KEY_TITLE, tbTodoBean.todoTitle)
        intent.putExtra(AlarmReceiver.KEY_CONTENT, tbTodoBean.todoContent)

        val number = getIdByKey(tbTodoBean.uuid).toInt()
        intent.putExtra(AlarmReceiver.KEY_NUMBER, number)
        val sender = PendingIntent.getBroadcast(
                context, number, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        //闹铃间隔， 这里设为1分钟闹一次，在第2步我们将每隔1分钟收到一次广播
        //int interval = 60 * 1000;
        //am.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, interval, sender);
        am?.cancel(sender)
    }

    /**
     * 添加alarm映射
     */
    fun addAlarmMap(tbAlarmMapBean: TBAlarmMapBean) {
        val tbAlarmMapBeanBox = App.app.boxStore.boxFor(TBAlarmMapBean::class.java)
        if (!TextUtils.isEmpty(tbAlarmMapBean.key)) {
            val alarmMapBean = tbAlarmMapBeanBox.query().equal(TBAlarmMapBean_.key, tbAlarmMapBean.key).build().findFirst()
            if (alarmMapBean == null) {
                tbAlarmMapBeanBox.put(tbAlarmMapBean)
            }
        }
    }

    fun isExistAlarmMap(uuid: String?): Int {
        val tbAlarmMapBeanBox = App.app.boxStore.boxFor(TBAlarmMapBean::class.java)
        if (TextUtils.isEmpty(uuid)) {
            return 0
        }
        val alarmMapBean = tbAlarmMapBeanBox.query().equal(TBAlarmMapBean_.key, uuid!!).build().findFirst()
                ?: return 0
        return alarmMapBean.number
    }

    fun getIdByKey(key: String?): Long {
        val tbAlarmMapBeanBox = App.app.boxStore.boxFor(TBAlarmMapBean::class.java)

        val alarmMapBean = tbAlarmMapBeanBox.query().equal(TBAlarmMapBean_.key, key!!).build().findFirst()
        return alarmMapBean?.id ?: 0

    }

}
/**
 * 获取todo列表
 */
