package com.kristallik.jokeapp.domain.model

import android.os.Parcel
import android.os.Parcelable


data class Joke(
    val id: Int,
    val category: String,
    val setup: String,
    val delivery: String,
    val source: Source

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        Source.valueOf(parcel.readString() ?: "")
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(category)
        parcel.writeString(delivery)
        parcel.writeString(setup)
        parcel.writeString(source.name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Joke> {
        override fun createFromParcel(parcel: Parcel): Joke {
            return Joke(parcel)
        }

        override fun newArray(size: Int): Array<Joke?> {
            return arrayOfNulls(size)
        }
    }
}

enum class Source {
    TYPE_NETWORK,
    TYPE_MANUAL
}