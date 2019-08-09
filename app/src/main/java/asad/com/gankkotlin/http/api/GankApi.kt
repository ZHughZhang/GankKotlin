package asad.com.gankkotlin.http.api

import asad.com.gankkotlin.http.response.gank.CategoryResponse
import asad.com.gankkotlin.http.response.gank.TodayResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.http.api
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-02 20:20
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/
interface GankApi{
    companion object{
        const val baseUrl = "http://gank.io/api/"
    }

    /**
     * 今日推荐
     * */
    @GET("today")
    fun tody():Flowable<TodayResponse>

    /**
     * 获取分类的接口
     *
     */
    @GET("data/{category}/{pageNum}/{pageIndex}")
    fun category(@Path("category")category: String,@Path("pageNum")pageNum: Int,@Path("pageIndex")pageIndex: Int):Flowable<CategoryResponse>

    /**
     * 查询接口
     *
     * */

    @GET("search/query/{key}/category/{category}/count/{pageNum}/page/{pageIndex}")
    fun search(@Path("key")key:String,@Path("category")category:String,@Path("pageNum")pageNum:Int,@Path("pageIndex")pageIndex:Int):Flowable<CategoryResponse>



}
