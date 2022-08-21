package com.vmware.nparui.gitpr.domain

import androidx.lifecycle.LiveData
import com.vmware.nparui.gitpr.data.di.DispatcherIO
import com.vmware.nparui.gitpr.data.entities.PullRequestInfo
import com.vmware.nparui.gitpr.data.repository.PAGE_CAP
import com.vmware.nparui.gitpr.data.repository.PRRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val POLL_INTERVAL = 60000L //1 min
class PullRequestUseCase @Inject constructor(
    private val prRepository: PRRepository,
    @DispatcherIO private val dispatcher : CoroutineDispatcher){

    private var page = 1
    private lateinit var job: Job

    fun startFetch() : LiveData<List<PullRequestInfo>> {
        job = CoroutineScope(dispatcher).launch {
            while (isActive){
                prRepository.poll(page)
                delay(POLL_INTERVAL)
            }
        }
        return prRepository.prList
    }

    fun nextPage() {
        page++
        job.cancel()
        startFetch()
    }

    fun prevPage() {
        if (page>0) {
            page--
            job.cancel()
            startFetch()
        }
    }

    fun showPageOptions() : Boolean {
        prRepository.prList.value?.let {
            if (it.size == PAGE_CAP) return true
        }
        return false
    }

    fun stop() {
        prRepository.close()
        dispatcher.cancel()
    }
}