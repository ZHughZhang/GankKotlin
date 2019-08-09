package asad.com.gankkotlin.http

import android.os.Parcel
import android.os.Parcelable
import asad.com.gankkotlin.App
import asad.com.gankkotlin.R
import asad.com.gankkotlin.http.response.BaseGankResponse
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import io.reactivex.FlowableSubscriber
import org.reactivestreams.Subscription

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.http
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-03 13:40
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/
abstract class BaseGankSubscriber <T: BaseGankResponse> : FlowableSubscriber<T>, Parcelable {


    var mSub: Subscription? = null

    override fun onComplete() {
    }

    override fun onSubscribe(s: Subscription) {
        mSub = s
        start()
        if (!NetworkUtils.isConnected()){
            onFail(ErrorCode.NETWORK_ERROR, App.INSTANCE.getString(R.string.network_erro))

        }else{
            s.request(Long.MAX_VALUE)
        }
    }

    override fun onNext(t: T) {
        if (t.error){
            LogUtils.e(t)
            onFail(ErrorCode.DATA_ERROR,App.INSTANCE.getString(R.string.request_error))
        }else{
            onSuccess(t)
        }
    }

    override fun onError(t: Throwable?) {
        LogUtils.e(t?.message+">>>>>>>>>>"+t.toString())
        onFail(ErrorCode.SERVER_ERROR,App.INSTANCE.getString(R.string.server_error))
        t?.printStackTrace()
        //LogUtils.e(t)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    abstract fun onSuccess(t: T)
    abstract fun onFail(code: ErrorCode,errorMsg: String)


    open fun start(){

    }


}