package com.kalus.kotlinnetapi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * desc:
 *
 * @author biaowen.yu
 * @date 2020/11/23 16:46
 *
 **/
@Parcelize
data class User(val name: String, val age: Int) : Parcelable