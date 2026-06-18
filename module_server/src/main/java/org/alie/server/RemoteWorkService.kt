package org.alie.server

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import org.alie.aidl.ICommonCallback
import org.alie.aidl.IUserInfo
import org.alie.aidl.IUserInfoAidlInterface

class RemoteWorkService : Service() {
    private var tag = RemoteWorkService::class.java.toString()

    override fun onCreate() {
        Log.i(tag,"binderwork server onCreate")
        super.onCreate()
    }

    override fun onBind(intent: Intent): IBinder {
        Log.i(tag,"binderwork server onBind")
        return object : IUserInfoAidlInterface.Stub() {
            override fun add(a: Int, b: Int): Int {

                Log.i(tag,"binderwork server add a:$a b:$b")
                return (a+b ) *10
            }

            override fun getUserInfoList(): List<IUserInfo?>? {
                val list = listOf(
                    IUserInfo("tom", 12, "this is a cat"),
                    IUserInfo("jerry", 12, "this is a mouse"),
                    IUserInfo("lucas", 12, "this is a dog"))
                Log.i(tag,"binderwork server getUserInfoList size:${list.size}")
                return list
            }

            override fun workToGetUserInfoList(iCommonCallback: ICommonCallback?) {

                val list = listOf(
                    IUserInfo("tommmm", 12, "this is a cat"),
                    IUserInfo("jerryyy", 12, "this is a mouse"),
                    IUserInfo("lucassss", 12, "this is a dog"))
                iCommonCallback?.onSuccess(list[0].name)
            }

            override fun getScore(iUserInfo: IUserInfo?): Int {
                Log.i(tag,"binderwork server getScore iUserInfo:$iUserInfo")
                return 87
            }

            override fun getNewScore(list: List<IUserInfo?>?): Int {
                Log.i(tag,"binderwork server getNewScore listSize:${list?.size}")
                return 88
            }
        }
    }
}