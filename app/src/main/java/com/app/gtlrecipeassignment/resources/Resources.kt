package com.app.gtlrecipeassignment.resources

import com.app.gtlrecipeassignment.utils.NetworkCallStatus

data class Resource<out T>(val status: NetworkCallStatus, val data: T?, val message: String?) {
}