package com.example.todoapp.data

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.ViewModel

data class ListViewModel(val title: String, val desc: String, val date: String) : ViewModel(), Parcelable {
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(desc)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListViewModel> {
        override fun createFromParcel(parcel: Parcel): ListViewModel {
            return ListViewModel(
                parcel.readString()!!,
                parcel.readString()!!,
                parcel.readString()!!
            )
        }
        override fun newArray(size: Int): Array<ListViewModel?> {
            return arrayOfNulls(size)
        }
    }
}
