package com.n0lik.common.test.response

import okhttp3.MediaType
import okhttp3.ResponseBody

const val CONTENT_NOT_FOUND_JSON = "{ \"message\": \"Not found!\" }"

fun String.toResponseBody(): ResponseBody = ResponseBody.create(MediaType.get("application/json"), this)