package com.github.thibseisel.sfyxplor

import com.github.thibseisel.api.spotify.*
import com.github.thibseisel.api.spotify.Pitch.*
import com.google.gson.*
import com.google.gson.internal.Streams
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.lang.reflect.*

internal class MusicalModeJsonAdapter : TypeAdapter<MusicalMode>() {

    override fun write(writer: JsonWriter, mode: MusicalMode?) {
        if (mode == null) {
            writer.nullValue()
        } else writer.value(when (mode) {
            MusicalMode.MINOR -> 0
            MusicalMode.MAJOR -> 1
        })
    }

    override fun read(reader: JsonReader): MusicalMode? {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            return null
        }

        return when (reader.nextInt()) {
            0 -> MusicalMode.MINOR
            1 -> MusicalMode.MAJOR
            else -> null
        }
    }
}

internal class SpotifyPitchJsonAdapter : TypeAdapter<Pitch?>() {

    override fun write(writer: JsonWriter, pitch: Pitch?) {
        if (pitch == null) {
            writer.nullValue()
        } else {
            writer.value(pitch.ordinal)
        }
    }

    override fun read(reader: JsonReader): Pitch? {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            return null
        }

        return when (reader.nextInt()) {
            0 -> C
            1 -> C_SHARP
            2 -> D
            3 -> D_SHARP
            4 -> E
            5 -> F
            6 -> F_SHARP
            7 -> G
            8 -> G_SHARP
            9 -> A
            10 -> A_SHARP
            11 -> B
            else -> null
        }
    }
}

internal class SearchableAdapter(
    private val factory: TypeAdapterFactory,
    private val gson: Gson,
    private val type: TypeToken<SearchableResource>
) : TypeAdapter<SearchableResource?>() {

    object Factory : TypeAdapterFactory {
        override fun <T : Any?> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
            if (type.rawType != SearchableResource::class.java) {
                return null
            }

            @Suppress("unchecked_cast")
            return SearchableAdapter(this, gson, type as TypeToken<SearchableResource>) as TypeAdapter<T>
        }
    }

    override fun write(writer: JsonWriter, value: SearchableResource?) {
        if (value == null) {
            writer.nullValue()
        } else {
            val serializer = gson.getDelegateAdapter(factory, type)
            serializer.write(writer, value)
        }
    }

    override fun read(reader: JsonReader): SearchableResource? {
        when (reader.peek()) {
            JsonToken.NULL -> {
                reader.nextNull()
                return null
            }

            JsonToken.BEGIN_OBJECT -> {
                val resourceJsonObject = Streams.parse(reader).asJsonObject
                val resourceType = getTargetType(resourceJsonObject)

                val deserializer = gson.getDelegateAdapter(factory, resourceType)
                return deserializer.fromJsonTree(resourceJsonObject)
            }

            else -> throw JsonParseException("Unable to deserialize to a $type: JSON is not an object.")
        }
    }

    private fun getTargetType(json: JsonObject): TypeToken<out SearchableResource> {
        val targetClass = when(val jsonType = json["type"].asString) {
            "artist" -> Artist::class.java
            "album" -> Album::class.java
            "track" -> Track::class.java
            else -> throw JsonParseException("Unable to identify resource from its \"type\" property ($jsonType)")
        }

        return TypeToken.get(targetClass)
    }
}

internal class JsonWrapperTypeAdapter(
    private val elementsTypeAdapter: TypeAdapter<Any?>
) : TypeAdapter<JsonWrapper<Any?>?>() {

    override fun write(writer: JsonWriter, value: JsonWrapper<Any?>?) {
        if (value == null) {
            writer.nullValue()
        } else {
            writer.beginObject()
            writer.name(value.propertyName)

            writer.beginArray()
            for (element in value.payload) {
                elementsTypeAdapter.write(writer, element)
            }
        }
    }

    override fun read(reader: JsonReader): JsonWrapper<*>? = when (reader.peek()) {
        JsonToken.NULL -> {
            reader.nextNull()
            null
        }

        JsonToken.BEGIN_OBJECT -> {
            reader.beginObject()
            val propertyName = reader.nextName()
            val elements = mutableListOf<Any?>()

            reader.beginArray()
            while (reader.hasNext()) {
                elements += elementsTypeAdapter.read(reader)
            }
            reader.endArray()
            reader.endObject()

            JsonWrapper(propertyName, elements)
        }

        else -> throw JsonParseException("Unable to de-serialize to a Payload")
    }

    object Factory : TypeAdapterFactory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : Any?> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
            if (type.rawType != JsonWrapper::class.java) return null

            val arrayElementType = (type.type as ParameterizedType).actualTypeArguments[0]
            val arrayElementTypeToken = TypeToken.get(arrayElementType)
            val contentTypeAdapter = gson.getDelegateAdapter(this, arrayElementTypeToken) as TypeAdapter<Any?>
            return JsonWrapperTypeAdapter(contentTypeAdapter) as TypeAdapter<T>
        }
    }
}