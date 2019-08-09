package asad.com.gankkotlin.viewmodel

import android.arch.lifecycle.MutableLiveData
import asad.com.gankkotlin.http.BaseJDSibscriber
import asad.com.gankkotlin.http.ErrorCode
import asad.com.gankkotlin.http.RetroFitHolder
import asad.com.gankkotlin.http.response.JDListResponse
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

class JDViewModel : BaseViewModel(){

    private var data: MutableLiveData<Resource<JDListResponse>> = MutableLiveData()

    fun getData(): MutableLiveData<Resource<JDListResponse>> {
        return data
    }

    override fun start() {
        super.start()
    }

    override fun stop() {
        super.stop()

    }

    fun loadData(type: String,pageIndex: Int){
        RetroFitHolder.jdInstance().getDetail(type,pageIndex,listRequest)
    }

    private val listRequest = object : BaseJDSibscriber(){

        override fun onStart() {
            data.value = Resource.loading(null)
        }
        override fun onSuccess(t: JDListResponse) {

            data.value = Resource.success(t)

        }

        override fun onFail(code: ErrorCode, msg: String) {
            data.value = Resource.error(null,msg)

        }

    }


}