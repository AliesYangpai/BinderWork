package org.alie.client

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.alie.aidl.IUserInfoAidlInterface
import org.alie.client.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var tag = MainActivity::class.java.toString()

    private lateinit var mBinding: ActivityMainBinding

    private var proxy: IUserInfoAidlInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        mBinding.btn1.setOnClickListener {
            bindService(Intent().apply {
                action = "org.alie.server.bindserver"
                setPackage("org.alie.server")
            }, object : ServiceConnection {
                override fun onServiceConnected(
                    name: ComponentName?,
                    iBinder: IBinder?
                ) {
                    proxy = IUserInfoAidlInterface.Stub.asInterface(iBinder)
                    Log.i(tag, "onServiceConnected proxy : $proxy")
                }

                override fun onServiceDisconnected(p0: ComponentName?) {
                    Log.i(tag, "onServiceDisconnected ")
                }
            }, Context.BIND_AUTO_CREATE)
        }

        mBinding.btn2.setOnClickListener {
            mBinding.tv1.text = proxy?.add(5,9).toString()
        }
    }
}