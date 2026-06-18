package org.alie.server

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
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

            override fun getScore(iUserInfo: IUserInfo?): Int {
                Log.i(tag,"binderwork server getScore iUserInfo:$iUserInfo")
                return 87
            }
        }
    }
}