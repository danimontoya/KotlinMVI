package com.danieh.kotlinmvi.features.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by danieh on 19/04/2019.
 */
@Parcelize
data class UserView(
        val id: Int,
        val name: String,
        val username: String,
        val email: String
) : Parcelable