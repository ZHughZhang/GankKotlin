package asad.com.gankkotlin.http.retrofit

import asad.com.gankkotlin.http.BaseGankSubscriber
import asad.com.gankkotlin.http.ScHelper
import asad.com.gankkotlin.http.api.GankApi
import asad.com.gankkotlin.http.api.JDApi
import asad.com.gankkotlin.http.api.WeixunApi
import asad.com.gankkotlin.http.response.gank.CategoryResponse
import asad.com.gankkotlin.http.response.gank.TodayResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.http.retrofit
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-02 20:55
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/
class GankRetrofitClient(private val okHttpClient: OkHttpClient) {
    private var server: GankApi

    init {
        val client = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(GankApi.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        server = client.create(GankApi::class.java)

    }

    /**
     * 今天
     * */

    fun today(suncriber: BaseGankSubscriber<TodayResponse>) {
        server.tody().compose(ScHelper.compose()).subscribe(suncriber)
    }

    /**
     * 分类
     *
     * */
    fun category(category: String, pageIndex: Int, subscriber: BaseGankSubscriber<CategoryResponse>) {
        server.category(category, 15, pageIndex)
            .compose(ScHelper.compose())
            .subscribe(subscriber)
    }

    /**
     * Gank 查询接口
     * */
    fun search(key: String, category: String, pageIndex: Int, subscriber: BaseGankSubscriber<CategoryResponse>) {
        server.search(key, category, 15, pageIndex)
            .compose(ScHelper.compose())
            .subscribe(subscriber)
    }

    /**/
}