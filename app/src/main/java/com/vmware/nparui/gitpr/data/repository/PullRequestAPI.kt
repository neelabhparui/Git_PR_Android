package com.vmware.nparui.gitpr.data.repository

import com.vmware.nparui.gitpr.data.entities.PullRequestInfo
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PullRequestAPI {
    @GET("repos/neelabhparui/Git_PR_Android/pulls?state=closed")
    fun getClosedPullRequests(@Query("state") state : String = "closed"
                              ,@Query("per_page") perPage : Int = 5
                              ,@Query("page") page : Int) : Single<List<PullRequestInfo>>
}