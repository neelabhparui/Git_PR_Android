package com.vmware.nparui.gitpr.data.repository

import com.vmware.nparui.gitpr.RobolectricTestCase
import com.vmware.nparui.gitpr.data.entities.BaseInfo
import com.vmware.nparui.gitpr.data.entities.HeadInfo
import com.vmware.nparui.gitpr.data.entities.PullRequestInfo
import com.vmware.nparui.gitpr.data.entities.UserInfo
import com.vmware.nparui.gitpr.domain.PRData
import com.vmware.nparui.gitpr.domain.SUCCESS
import io.reactivex.rxjava3.core.Single
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations.initMocks

class PRRepositoryTest : RobolectricTestCase() {

    private lateinit var prRepository: PRRepository
    @Mock
    private lateinit var prApi : PullRequestAPI
    @Mock
    private lateinit var response : Single<List<PullRequestInfo>>

    override fun start() {
        initMocks(this)
        prRepository = PRRepository(prApi)
        Mockito.`when`(prApi.getClosedPullRequests(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(response)
    }

    @Test
    fun testGetPrList_empty() {
        Assert.assertNotNull(prRepository.prList)
        Assert.assertNull(prRepository.prList.value)
    }

    @Test
    fun testGetPrList_not_empty() {
        val prInfo = PullRequestInfo(0,"", "",  UserInfo("sa", "", null), "", "", HeadInfo(""), BaseInfo(""))
        prRepository.setData(PRData(SUCCESS, listOf(prInfo), null))
        Assert.assertNotNull(prRepository.prList)
        Assert.assertEquals(SUCCESS, prRepository.prList.value?.status)
        Assert.assertEquals(1, prRepository.prList.value?.data?.size)
    }

    @Test
    fun testPoll() {
        prRepository.poll(1)
        Mockito.verify(prApi).getClosedPullRequests("closed", 5, 1)
    }

    @Test
    fun testClose() {
        prRepository.poll(1)
        Assert.assertEquals(1, prRepository.compositeDisposable.size())
        prRepository.close()
        Assert.assertEquals(0, prRepository.compositeDisposable.size())
    }

    override fun tearDown() {
    }
}