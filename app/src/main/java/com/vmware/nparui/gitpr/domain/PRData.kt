package com.vmware.nparui.gitpr.domain

import androidx.annotation.IntDef
import com.vmware.nparui.gitpr.data.entities.PullRequestInfo

const val SUCCESS = 1
const val FAIL = 2
data class PRData(@PRStatus val status : Int, val  data : List<PullRequestInfo>?, val message: String?)

@IntDef(SUCCESS, FAIL)
@Retention(AnnotationRetention.SOURCE)
annotation class PRStatus