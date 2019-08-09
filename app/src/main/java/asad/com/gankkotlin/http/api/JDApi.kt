package asad.com.gankkotlin.http.api

import asad.com.gankkotlin.http.response.JDListResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.http.api
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-02 20:29
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/

interface JDApi {
    companion object {
        const val baseUrl = "http://i.jandan.net/"

        const val TYPE_GIRL = "jandan.get_ooxx_comments"

    }

    @GET
    fun getDetailData(
        @Url url:String,
        @Query("oxwlxojflwblxbsapi") type:String,
        @Query("page") page :Int): Flowable<JDListResponse>
}