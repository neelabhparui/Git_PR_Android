package com.vmware.nparui.gitpr.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vmware.nparui.gitpr.data.entities.PullRequestInfo
import com.vmware.nparui.gitpr.domain.PullRequestUseCase
import com.vmware.nparui.gitpr.presentation.PRListAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "ShowPullRequestsViewModel"
@HiltViewModel
class ShowPullRequestsViewModel @Inject constructor(private val pullRequestsUseCase: PullRequestUseCase): ViewModel(), DefaultLifecycleObserver {

    val adapter = PRListAdapter(ArrayList())

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        pullRequestsUseCase.startFetch().observe(owner) {
            Log.println(Log.INFO, TAG, "data - $it")
            adapter.update(it)
        }
    }

    fun nextPage() {
        pullRequestsUseCase.nextPage()
    }

    fun prevPage() {
        pullRequestsUseCase.prevPage()
    }

    fun shouldShowPageOptions() = pullRequestsUseCase.showPageOptions()

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        pullRequestsUseCase.stop()
    }
}