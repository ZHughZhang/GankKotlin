package asad.com.gankkotlin.http.response.gank

import asad.com.gankkotlin.http.bean.TitleBean
import asad.com.gankkotlin.http.response.BaseGankResponse
import com.google.gson.annotations.SerializedName

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.http.response.gank
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-02 20:53
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/
class TodayResponse: BaseGankResponse(){


    var results:ResultBean? = null

    var category:List<String>? = null



}

class ResultBean{
    @SerializedName("Android")
    var android:List<TitleBean>? = null

    @SerializedName("App")
    var app:List<TitleBean>? = null

    @SerializedName("iOS")
    var iOS:List<TitleBean>? = null

    @SerializedName("休息视频")
    var video:List<TitleBean>? = null

    @SerializedName("福利")
    var gift:List<TitleBean>? = null

    @SerializedName("瞎推荐")
    var recommond:List<TitleBean>? = null

}
