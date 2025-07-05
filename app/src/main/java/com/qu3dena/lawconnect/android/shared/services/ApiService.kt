package com.qu3dena.lawconnect.android.shared.services

import com.squareup.moshi.Moshi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.javaType
import kotlin.reflect.typeOf

@OptIn(ExperimentalStdlibApi::class)
@Singleton
class ApiService @Inject constructor(
    val raw: RawRetrofitService,
    val moshi: Moshi
) {
    suspend inline fun <reified T> get(path: String): T {
        val body = raw.getRaw(path).string()
        val type = typeOf<T>().javaType
        val adapter = moshi.adapter<T>(type)
        return adapter.fromJson(body) ?: error("Empty JSON")
    }

    suspend inline fun <reified Req : Any, reified Res> post(
        path: String,
        body: Req
    ): Res {
        val json = moshi.adapter(Req::class.java).toJson(body)
        val reqBody = json.toRequestBody("application/json".toMediaType())
        return raw.postRaw(path, reqBody).parse(moshi)
    }

    suspend inline fun <reified Req : Any, reified Res> put(
        path: String,
        body: Req
    ): Res {
        val json = moshi.adapter(Req::class.java).toJson(body)
        val reqBody = json.toRequestBody("application/json".toMediaType())
        return raw.putRaw(path, reqBody).parse(moshi)
    }

    suspend inline fun delete(path: String) {
        raw.deleteRaw(path)
    }
}

@OptIn(ExperimentalStdlibApi::class)
inline fun <reified T> ResponseBody.parse(moshi: Moshi): T {
    val type = typeOf<T>().javaType
    val adapter = moshi.adapter<T>(type)
    val parsed = adapter.fromJson(string())
    return parsed ?: error("Empty response")
}