package com.kristallik.jokeapp.data

import android.os.Parcel
import android.os.Parcelable

data class Joke(
    val id: Int,
    val category: String,
    val question: String,
    val answer: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(category)
        parcel.writeString(question)
        parcel.writeString(answer)
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