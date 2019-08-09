package asad.com.gankkotlin.http.response

import asad.com.gankkotlin.http.bean.NewsContent

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.http.response
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-02 20:42
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/
data class NewsResponse (
    val data:ArrayList<NewsContent>,
    val hasMore: Boolean,
    val hasMoreToRefresh: Boolean,
    val displayInfo: String,
    val totalNumber: Int,
    val message:String
)