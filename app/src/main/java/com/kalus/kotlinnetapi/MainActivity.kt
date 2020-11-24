package com.kalus.kotlinnetapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(NetViewModel::class.java)
        getUser.setOnClickListener {
            viewModel.getName()
        }
        viewModel.user.observe(this, Observer {
            test.text = "${it.name} + ${it.age}"
        })

    }
}