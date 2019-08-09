package asad.com.gankkotlin.http.retrofit

import android.os.Parcel
import android.os.Parcelable
import asad.com.gankkotlin.http.BaseJDSibscriber
import asad.com.gankkotlin.http.ScHelper
import asad.com.gankkotlin.http.api.JDApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.http.retrofit
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-03 11:42
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/
class  JDRetrofitClient(private val okHttpClient: OkHttpClient)  {
    private var server: JDApi


    init {
        val client =  Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(JDApi.baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        server = client.create(JDApi::class.java)
    }
    fun getDetail(type:String,page:Int,subscriber:BaseJDSibscriber ){
        server.getDetailData(JDApi.baseUrl,type,page)
            .compose(ScHelper.compose())
            .subscribe(subscriber)
    }


}