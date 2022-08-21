package com.vmware.nparui.gitpr.data.entities

import com.google.gson.annotations.SerializedName

data class PullRequestInfo(
    var id : Int,
    var state : String,
    var title : String,
    var user : UserInfo,
    @SerializedName("created_at")
    var createdAt : String,
    @SerializedName("closed_at")
    var closedAt : String,
    var head : HeadInfo,
    var base : BaseInfo
)
