package asad.com.gankkotlin.viewmodel

import android.arch.lifecycle.MutableLiveData
import asad.com.gankkotlin.http.BaseGankSubscriber
import asad.com.gankkotlin.http.ErrorCode
import asad.com.gankkotlin.http.RetroFitHolder
import asad.com.gankkotlin.http.response.gank.TodayResponse
import asad.com.gankkotlin.repository.Resource

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
class IndexViewModel : BaseViewModel() {
    private var indexData: MutableLiveData<Resource<TodayResponse>> = MutableLiveData()


    fun getIndexData()= indexData


    override fun start() {
        super.start()
        loadToday()
    }

    override fun stop() {
        super.stop()
        indexRequest.mSub?.cancel()

    }

    private fun loadToday(){
        RetroFitHolder.gankInstance().today(indexRequest)
    }

    /**
     * 首页请求
     */
    private val indexRequest = object : BaseGankSubscriber<TodayResponse>(){

        override fun start() {
            super.start()
            indexData.value = Resource.loading(null)
        }

        override fun onSuccess(t: TodayResponse) {
            indexData.value = Resource.success(t)

        }

        override fun onFail(code: ErrorCode, errorMsg: String) {
            indexData.value = Resource.error(null,msg = errorMsg)
        }

    }

}