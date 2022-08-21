package com.vmware.nparui.gitpr

import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = RobolectricApplication::class)
@Ignore
abstract class RobolectricTestCase {

    @Before
    abstract fun start()

    @After
    abstract fun tearDown()

}