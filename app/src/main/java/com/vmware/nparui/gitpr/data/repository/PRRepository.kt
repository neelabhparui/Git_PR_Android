package com.vmware.nparui.gitpr.data.repository

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vmware.nparui.gitpr.data.di.RetrofitPullRequestAPI
import com.vmware.nparui.gitpr.data.entities.PullRequestInfo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

private const val TAG  = "PRRepository"
private const val CLOSED = "closed"
const val PAGE_CAP = 5
class PRRepository @Inject constructor(
    @RetrofitPullRequestAPI private val prApi : PullRequestAPI
    ) {

    private val _prList : MutableLiveData<List<PullRequestInfo>> = MutableLiveData()

    val prList : LiveData<List<PullRequestInfo>> = _prList

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val compositeDisposable = CompositeDisposable()

    fun poll(page : Int) {
        Log.println(Log.INFO, TAG, "poll")
        compositeDisposable.add(prApi.getClosedPullRequests(CLOSED, PAGE_CAP, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<PullRequestInfo>>(){

                override fun onSuccess(t: List<PullRequestInfo>) {
                    Log.println(Log.INFO, TAG, "result - $t")
                    _prList.value = t
                }

                override fun onError(e: Throwable) {
                    Log.println(Log.ERROR, TAG, e.toString())
                }
            }))
    }

    fun close() {
        compositeDisposable.clear()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun setList(list: List<PullRequestInfo>) {
        _prList.value = list
    }
}