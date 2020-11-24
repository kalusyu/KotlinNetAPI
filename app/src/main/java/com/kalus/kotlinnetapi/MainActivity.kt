package com.kalus.kotlinnetapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 最基本的ViewModel创建方法，后续使用Hilt重构
        viewModel = ViewModelProviders.of(this).get(NetViewModel::class.java)
        getUser.setOnClickListener {
            viewModel.getName()
        }
        viewModel.user.observe(this) {
            test.text = "${it.name} + ${it.age}"
        }

    }
}