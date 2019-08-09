package asad.com.gankkotlin.http

import asad.com.gankkotlin.App
import asad.com.gankkotlin.R
import asad.com.gankkotlin.http.response.JDListResponse
import com.blankj.utilcode.util.NetworkUtils
import io.reactivex.FlowableSubscriber
import org.reactivestreams.Subscription

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.http
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-03 13:54
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/

abstract class BaseJDSibscriber: FlowableSubscriber<JDListResponse> {

    lateinit var mSub:Subscription

    override fun onComplete() {
    }

    override fun onSubscribe(s: Subscription) {
        mSub = s
        onStart()
        if(!NetworkUtils.isConnected()){
            onFail(ErrorCode.NETWORK_ERROR, App.INSTANCE.getString(R.string.network_erro))
        }else{
            mSub.request(Long.MAX_VALUE)
        }
    }

    override fun onNext(t: JDListResponse) {
        if("ok" == t.status){
            onSuccess(t)
        }else{
            onFail(ErrorCode.DATA_ERROR,App.INSTANCE.getString(R.string.data_error))
        }
    }

    override fun onError(t: Throwable?) {
        onFail(ErrorCode.SERVER_ERROR, App.INSTANCE.getString(R.string.server_error))
    }

    abstract fun onSuccess(t:JDListResponse)
    abstract fun onFail(code:ErrorCode,msg:String)
    open fun onStart() {

    }
}