package asad.com.gankkotlin.http

import asad.com.gankkotlin.App
import asad.com.gankkotlin.R
import asad.com.gankkotlin.http.response.VideoDetailResponse
import com.blankj.utilcode.util.NetworkUtils
import io.reactivex.FlowableSubscriber
import org.reactivestreams.Subscription

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.http
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-05 17:03
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/

abstract  class VideoDetailSubscriber: FlowableSubscriber<VideoDetailResponse>{


    lateinit var mSub: Subscription


    override fun onComplete() {
    }

    override fun onSubscribe(s: Subscription) {
        mSub = s
        start()
        if(NetworkUtils.isConnected()){
            s.request(Long.MAX_VALUE)
        }else{
            onFail(ErrorCode.NETWORK_ERROR, App.INSTANCE.getString(R.string.network_erro))
        }
    }

    override fun onNext(t: VideoDetailResponse) {
        when(t.retCode){
            200 ->{
                //请求成功
                onSuccess(t)
            }
            else ->{
                //请求失败，重试
                onFail(ErrorCode.DATA_ERROR,App.INSTANCE.getString(R.string.data_error))
            }
        }
    }

    override fun onError(t: Throwable?) {
        onFail(ErrorCode.SERVER_ERROR,App.INSTANCE.getString(R.string.server_error))
    }

    /**
     * 数据获取成功
     */
    abstract fun onSuccess(t:VideoDetailResponse)

    /**
     * 数据获取失败
     */
    abstract fun onFail(code:ErrorCode,errorMsg:String)

    /**
     * 请求开始
     */
    open fun start(){

    }



}