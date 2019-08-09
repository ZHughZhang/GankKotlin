package asad.com.gankkotlin.http.api

import asad.com.gankkotlin.http.response.VideoDetailResponse
import asad.com.gankkotlin.http.response.WeixunResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.http.api
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-02 20:30
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/

interface WeixunApi {
    companion object {
        const val baseUrl = "http://is.snssdk.com/"
        const val GET_ARTICLE_LIST = "api/news/feed/v62/?refer=1&count=20&loc_mode=4&device_id=34960436458&iid=13136511752"
    }

    @GET(GET_ARTICLE_LIST)
    fun getNewsList(@Query("category")category:String,
                    @Query("min_behot_time")lastTime:Long,
                    @Query("last_refresh_sub_entrance_interval")currentTime:Long): Flowable<WeixunResponse>

    @Headers(
        "Content-Type:application/x-www-form-urlencoded; charset=UTF-8",
        "Cookie:PHPSESSIID=334267171504; _ga=GA1.2.646236375.1499951727; _gid=GA1.2.951962968.1507171739; Hm_lvt_e0a6a4397bcb500e807c5228d70253c8=1507174305;Hm_lpvt_e0a6a4397bcb500e807c5228d70253c8=1507174305; _gat=1",
        "Origin:http://toutiao.iiilab.com")
    @POST("http://service.iiilab.com/video/toutiao")
    fun getVideoDetail(@Query("link")link:String,@Query("r")r:String,@Query("s")s:String):Flowable<VideoDetailResponse>

}