package com.app.gtlrecipeassignment.resources

import com.app.gtlrecipeassignment.utils.NetworkCallStatus

/**
 * Created by Moiz Khan on 31/12/21
 * This class is responsible to get data from API and pass it to observer.
 */
data class Resource<out T>(val status: NetworkCallStatus, val data: T?, val message: String?) {
}