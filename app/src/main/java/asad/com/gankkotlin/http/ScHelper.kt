package asad.com.gankkotlin.http

import io.reactivex.FlowableTransformer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.http
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-03 09:19
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/
object ScHelper {
    fun <T> compose(): FlowableTransformer<T,T>{
        return FlowableTransformer{
            observable ->
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}