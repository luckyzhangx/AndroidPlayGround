package com.luckyzhangx.gsonmultitypecollection.adapter

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.internal.Streams
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.luckyzhangx.gsonmultitypecollection.data.StyleData

class StyleDataTypeFactory : TypeAdapterFactory {
    val provider = mutableMapOf<Class<*>, StyleClassProvider<*, *>>()

    fun registerProvider(styleProvider: StyleClassProvider<*, *>) {
        provider[styleProvider.clazz] = styleProvider
    }

    override fun <T : Any?> create(gson: Gson?, type: TypeToken<T>?): TypeAdapter<T>? {
        if (!StyleData::class.java.isAssignableFrom(type!!.rawType))
            return null

        val provider = provider[type.rawType]

        if (provider != null) {
            val delegate = gson!!.getDelegateAdapter(this, type)
            return object : TypeAdapter<T>() {
                override fun write(out: JsonWriter?, value: T) {
                    delegate.write(out, value)
                }

                override fun read(`in`: JsonReader?): T {
                    val jsonElement = Streams.parse(`in`)

                    val tempData = jsonElement.asJsonObject.get("data")

                    jsonElement.asJsonObject.remove("data")

//                if(tempData == null)...

                    val data: T = delegate.fromJsonTree(jsonElement)
                    if (data is StyleData<*>) {
                        val clazz = provider.getTypeToken(data.style)
                        try {
                            data::data.set(Gson().fromJson(tempData, clazz))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    return data
                }
            }
        }
        return null
    }
}

open class StyleClassProvider<T, S : StyleData<T>>(val clazz: Class<S>,
                                                   val map: Map<String, Class<out T>>) {
    fun getTypeToken(style: String): Class<*>? {
        return map[style]
    }
}