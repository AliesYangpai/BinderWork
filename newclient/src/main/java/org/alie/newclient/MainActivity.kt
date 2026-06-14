package org.alie.newclient

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.alie.binderwork.IMyAidlInterface
import org.alie.newclient.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "NewClient"
    }

    private var proxy: IMyAidlInterface? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName?,
            iBinder: IBinder?
        ) {
            proxy = IMyAidlInterface.Stub.asInterface(iBinder)
            Log.i(TAG, "onServiceConnected: ${name?.className}")
            mBinding.tvShow.text = "服务已连接"
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            proxy = null
            Log.w(TAG, "onServiceDisconnected: ${name?.className}")
            mBinding.tvShow.text = "服务已断开"
        }

        override fun onBindingDied(name: ComponentName?) {
            proxy = null
            Log.e(TAG, "onBindingDied: ${name?.className}")
            mBinding.tvShow.text = "Binder 已死亡"
        }

        override fun onNullBinding(name: ComponentName?) {
            Log.e(TAG, "onNullBinding: ${name?.className}")
            mBinding.tvShow.text = "服务返回 null Binder"
        }
    }

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.btn1.setOnClickListener {
            startBindService()
        }
        mBinding.btn2.setOnClickListener {
            val result = proxy?.addWork(4, 9)
            mBinding.tvShow.text = result?.toString() ?: "服务未连接"
        }
    }

    private fun startBindService() {
        val intent = Intent().apply {
            action = "org.alie.binderwork.aidl.ACTION_BIND_SERVICE"
            setPackage("org.alie.binderwork")
        }
        val bound = bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        if (bound) {
            Log.i(TAG, "bindService 调用成功，等待回调...")
            mBinding.tvShow.text = "正在绑定服务..."
        } else {
            Log.e(TAG, "bindService 失败！请检查 Service 是否存在或 <queries> 是否声明")
            mBinding.tvShow.text = "绑定失败：找不到服务"
        }
    }

}