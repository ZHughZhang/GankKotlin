package asad.com.gankkotlin.http.retrofit

import asad.com.gankkotlin.http.BaseWeixunSubscriber
import asad.com.gankkotlin.http.ScHelper
import asad.com.gankkotlin.http.VideoDetailSubscriber
import asad.com.gankkotlin.http.api.WeixunApi
import asad.com.gankkotlin.http.bean.NewsContent
import asad.com.gankkotlin.http.response.NewsResponse
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.http.retrofit
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-03 13:14
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/
class WeixunRetrofit(private val okHttpClient: OkHttpClient) {
    private var server: WeixunApi

    private val gson: Gson by lazy {
        Gson()
    }


    init {
        val client = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(WeixunApi.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        server = client.create(WeixunApi::class.java)
    }

    fun getNewsList(category:String,lastTime:Long,currentTime:Long,subscriber: BaseWeixunSubscriber){
        server.getNewsList(category,lastTime,currentTime)
            .map {
                val list:ArrayList<NewsContent> = ArrayList()
                for(data in it.data){
                    val sData = gson.fromJson<NewsContent>(data.content,NewsContent::class.java)
                    list.add(sData)
                }
                NewsResponse(list,it.has_more,it.has_more_to_refresh,it.tips.display_info,it.total_number,it.message)
            }
            .compose(ScHelper.compose())
            .subscribe(subscriber)
    }


    fun getVideoDetail(link:String,r: String,s:String,subscriber: VideoDetailSubscriber){
        server.getVideoDetail(link, r, s)
            .compose(ScHelper.compose())
            .subscribe(subscriber)
    }

}
