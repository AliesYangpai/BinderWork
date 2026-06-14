package org.alie.binderwork

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.logging.Logger

class RemoteWorkService : Service() {

    private val mBinder = object : IMyAidlInterface.Stub() {

        override fun addWork(a: Int, b: Int): Int {
            return (a+b) * 10
        }

    }

    override fun onCreate() {
        super.onCreate()
        Log.i("RemoteWorkService","RemoteWorkService onCreate")
    }

    override fun onBind(intent: Intent): IBinder {
        Log.i("RemoteWorkService","RemoteWorkService onBind")
        return  mBinder
    }
}