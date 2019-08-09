package asad.com.gankkotlin.http.response.gank

import asad.com.gankkotlin.http.bean.TitleBean
import asad.com.gankkotlin.http.response.BaseGankResponse

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
class CategoryResponse: BaseGankResponse() {

    var results:ArrayList<TitleBean>? = null
}
