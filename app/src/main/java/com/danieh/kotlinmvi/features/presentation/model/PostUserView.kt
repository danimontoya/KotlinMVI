package com.danieh.kotlinmvi.features.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by danieh on 19/04/2019.
 */
@Parcelize
data class PostUserView(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
    val userName: String
) : Parcelable