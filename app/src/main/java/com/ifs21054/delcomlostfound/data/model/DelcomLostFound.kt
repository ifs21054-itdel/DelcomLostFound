package com.ifs21054.delcomlostfound.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DelcomLostfound (
    val id: Int,
    val title: String,
    val description: String,
    val status: String,
    val isCompleted: Boolean,
    val cover: String?,
) : Parcelable