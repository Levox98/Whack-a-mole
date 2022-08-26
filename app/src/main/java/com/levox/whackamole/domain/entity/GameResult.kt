package com.levox.whackamole.domain.entity

import android.os.Parcel
import android.os.Parcelable


data class GameResult(
    val score: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(score)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GameResult> {
        override fun createFromParcel(parcel: Parcel): GameResult {
            return GameResult(parcel)
        }

        override fun newArray(size: Int): Array<GameResult?> {
            return arrayOfNulls(size)
        }
    }
}