package com.zorzolli.apploja.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    val uid: String = " ",
    val name: String = " ",
    val price: String = " ",
    val url: String = " "):Parcelable
