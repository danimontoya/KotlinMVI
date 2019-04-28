package com.danieh.kotlinmvi.core.platform

import android.content.Context
import com.danieh.kotlinmvi.core.extension.networkInfo
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by danieh on 19/04/2019.
 */
@Singleton
class NetworkHandler
@Inject constructor(private val context: Context) {

    val isConnected get() = context.networkInfo?.isConnectedOrConnecting
}
