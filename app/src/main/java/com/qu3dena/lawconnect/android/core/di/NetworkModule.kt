package com.qu3dena.lawconnect.android.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import okhttp3.OkHttpClient
import javax.inject.Singleton

import retrofit2.Retrofit

import com.qu3dena.lawconnect.android.core.util.Constants
import com.qu3dena.lawconnect.android.shared.services.RawRetrofitService
import com.squareup.moshi.*
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.UUID

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add { type, _, _ ->
                if (type == UUID::class.java) {
                    object : JsonAdapter<UUID>() {
                        override fun fromJson(reader: JsonReader): UUID? {
                            val string = reader.nextString()
                            return UUID.fromString(string)
                        }

                        override fun toJson(writer: JsonWriter, value: UUID?) {
                            writer.value(value?.toString())
                        }
                    }
                } else null
            }
            .addLast(KotlinJsonAdapterFactory())
            .build()


    @Provides
    @Singleton
    fun provideRetrofit(
        moshi: Moshi,
        client: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Provides
    @Singleton
    fun provideRawService(retrofit: Retrofit): RawRetrofitService =
        retrofit.create(RawRetrofitService::class.java)
}