package com.vmware.nparui.gitpr.data.entities

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("login")
    var name : String,
    @SerializedName("avatar_url")
    var avatarUrl : String,
    var avatar : Bitmap?
)
