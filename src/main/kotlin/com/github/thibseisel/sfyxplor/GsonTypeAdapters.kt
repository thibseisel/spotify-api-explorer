package com.github.thibseisel.sfyxplor

import com.github.thibseisel.api.spotify.*
import com.github.thibseisel.api.spotify.Pitch.*
import com.google.gson.*
import com.google.gson.internal.Streams
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class MusicalModeJsonAdapter : TypeAdapter<MusicalMode>() {

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

class SpotifyPitchJsonAdapter : TypeAdapter<Pitch?>() {

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

class SearchableAdapterFactory : TypeAdapterFactory {

    override fun <T : Any?> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        if (type.rawType != SearchableResource::class.java) {
            return null
        }

        @Suppress("unchecked_cast")
        return SearchableAdapter(this, gson, type as TypeToken<SearchableResource>) as TypeAdapter<T>
    }

    private class SearchableAdapter(
        private val factory: TypeAdapterFactory,
        private val gson: Gson,
        private val type: TypeToken<SearchableResource>
    ) : TypeAdapter<SearchableResource?>() {

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
}