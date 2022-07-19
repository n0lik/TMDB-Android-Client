package com.n0lik.sample.common.domain

import com.n0lik.sample.common.model.ImageConfig

interface ConfigRepository {

    suspend fun getImageConfig(): ImageConfig
}