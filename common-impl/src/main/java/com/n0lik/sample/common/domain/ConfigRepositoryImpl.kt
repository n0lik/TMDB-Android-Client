package com.n0lik.sample.common.domain

import android.content.SharedPreferences
import androidx.core.content.edit
import com.n0lik.sample.common.data.api.ConfigApi
import com.n0lik.sample.common.dispatcher.AppDispatcher
import com.n0lik.sample.common.model.ImageConfig
import kotlinx.coroutines.withContext
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import javax.inject.Inject

internal class ConfigRepositoryImpl
@Inject constructor(
    private val preferences: SharedPreferences,
    private val configApi: ConfigApi,
    private val appDispatcher: AppDispatcher,
    private val json: Json
) : ConfigRepository {

    override suspend fun getImageConfig(): ImageConfig = withContext(appDispatcher.io) {
        val config = getValueByKey<ImageConfig>(IMAGE_CONFIG_KEY)
        val shouldUpdateConfig = config == null || isConfigOutdated()
        return@withContext if (shouldUpdateConfig) {
            loadAndSave()
            getValueByKey(IMAGE_CONFIG_KEY)!!
        } else {
            config!!
        }
    }

    private suspend fun loadAndSave() {
        configApi.getConfig().let {
            ImageConfig(
                secureBaseUrl = it.secureBaseUrl,
                posterSizes = it.posterSizes,
                backdropSizes = it.backdropSizes
            )
        }.also {
            saveValueByKey(IMAGE_CONFIG_KEY, it)
            preferences.edit(commit = true) { putLong(LAST_SYNC_KEY, System.currentTimeMillis()) }
        }
    }

    private fun isConfigOutdated(): Boolean {
        val lastSyncTime = preferences.getLong(LAST_SYNC_KEY, 0L)
        return if (lastSyncTime == 0L) {
            true
        } else {
            (lastSyncTime + SYNC_PERIOD_HOUR) < System.currentTimeMillis()
        }
    }

    @OptIn(InternalSerializationApi::class)
    private inline fun <reified T : Any> getValueByKey(key: String): T? =
        preferences.getString(key, null)
            ?.let { json.decodeFromString(T::class.serializer(), it) }

    @OptIn(InternalSerializationApi::class)
    private inline fun <reified T : Any> saveValueByKey(key: String, value: T) =
        preferences.edit(commit = true) {
            val data = json.encodeToString(T::class.serializer(), value)
            putString(key, data)
        }

    companion object {

        private const val SYNC_PERIOD_HOUR = 1000 * 60 * 60 * 24 // 24h
        private const val LAST_SYNC_KEY = "last_sync_key"
        private const val IMAGE_CONFIG_KEY = "image_config_key"
    }
}