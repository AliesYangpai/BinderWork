package org.alie.server

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import org.alie.aidl.IUserInfoAidlInterface

class RemoteWorkService : Service() {
    private var tag = RemoteWorkService::class.java.toString()

    override fun onCreate() {
        Log.i(tag,"onCreate")
        super.onCreate()
    }

    override fun onBind(intent: Intent): IBinder {
        Log.i(tag,"onBind")
        return object : IUserInfoAidlInterface.Stub() {
            override fun add(a: Int, b: Int): Int {
                return (a+b ) *10
            }
        }
    }
}