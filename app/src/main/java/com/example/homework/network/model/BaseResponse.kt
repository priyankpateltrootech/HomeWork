package com.example.homework.network.model

/**
 * Created by Aaditya on 05/08/2019.
 *
 * This base response of type of a typical response from server.
 * This is same for basically every API service.
 *
 *   {
 *     "status" : 200/404/500
 *     "data" : { object } or [ array ]
 *     "message" : "Yay! Success!!!"
 *   }
 */
data class BaseResponse<
        T>(
    var errorCode: String,
    var errorMessage: String,
    var user: T

)