package asad.com.gankkotlin.viewmodel

import android.arch.lifecycle.MutableLiveData
import asad.com.gankkotlin.http.BaseWeixunSubscriber
import asad.com.gankkotlin.http.ErrorCode
import asad.com.gankkotlin.http.RetroFitHolder
import asad.com.gankkotlin.http.response.NewsResponse
import asad.com.gankkotlin.http.response.WeixunResponse
import asad.com.gankkotlin.repository.Resource
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.viewmodel
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-06 20:44
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/

class NewsViewModel : BaseViewModel() {
    private var lastTime = 0L

    private var loadMoreTime = 0L
    private var firstLoadTime = 0L
    private val mList: MutableLiveData<Resource<WeixunResponse>> = MutableLiveData()
    private val mVideoList: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private val gson: Gson by lazy { Gson() }

    fun getList() = mVideoList

    /**
     * 下拉刷新
     * */

    fun loadRefreshData() {
        val currentTime = System.currentTimeMillis()
        lastTime = SPUtils.getInstance().getLong("video", 0)
        if (lastTime == 0L) {
            firstLoadTime = currentTime
        }
        RetroFitHolder.wInstance().getNewsList("video", lastTime, currentTime, listRequest)
        lastTime = currentTime;
        SPUtils.getInstance().put("video", lastTime)
    }

    /**
     *
     * 上拉加载更多
     * */
    fun loadMoreData() {
        if (loadMoreTime == 0L) {
            loadMoreTime = firstLoadTime
        }
        RetroFitHolder.wInstance().getNewsList("video", loadMoreTime - 60 * 10, loadMoreTime, listRequest)
    }

    private val listRequest = object : BaseWeixunSubscriber() {
        override fun start() {
            super.start()
            mList.value = Resource.loading(null)
        }

        override fun onSuccess(t: NewsResponse) {
            mVideoList.value = Resource.success(t)
        }

        override fun onFail(code: ErrorCode, msg: String) {
            mList.value = Resource.error(null,msg)
        }

    }


    override fun start() {
        super.start()
    }

    override fun stop() {
        super.stop()
        listRequest.mSub?.cancel()
    }
}


