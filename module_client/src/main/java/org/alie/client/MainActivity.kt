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
import org.alie.aidl.ICommonCallback
import org.alie.aidl.IUserInfo
import org.alie.aidl.IUserInfoAidlInterface
import org.alie.client.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var tag = MainActivity::class.java.toString()

    private lateinit var mBinding: ActivityMainBinding

    private var proxy: IUserInfoAidlInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)



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
                    Log.i(tag, "binderwork client onServiceConnected proxy : $proxy")
                }

                override fun onServiceDisconnected(p0: ComponentName?) {
                    Log.i(tag, "binderwork client onServiceDisconnected ")
                }
            }, Context.BIND_AUTO_CREATE)
        }

        mBinding.btn2.setOnClickListener {
            mBinding.tv1.text = proxy?.add(5, 9).toString()
        }

        mBinding.btn3.setOnClickListener {
            mBinding.tv1.text = proxy?.getScore(IUserInfo("tom", 12, "this is a cat")).toString()
        }
        mBinding.btn4.setOnClickListener {
            mBinding.tv1.text = proxy?.getNewScore(
                listOf(
                    IUserInfo("tom", 12, "this is a cat"),
                    IUserInfo("jerry", 12, "this is a mouse"),
                    IUserInfo("gether", 12, "this is a dag")
                )
            ).toString()
        }

        mBinding.btn5.setOnClickListener {
            mBinding.tv1.text = proxy?.userInfoList?.get(0)?.introduction
        }
        mBinding.btn6.setOnClickListener {
             proxy?.workToGetUserInfoList(object : ICommonCallback.Stub() {
                override fun onSuccess(dataMsg: String?) {
                        mBinding.tv1.text = dataMsg
                }


                 override fun onFail(msgMsg: String?) {
                     mBinding.tv1.text = msgMsg
                 }

             })
        }
    }
}