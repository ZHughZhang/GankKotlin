package asad.com.gankkotlin.http.bean

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.http.bean
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-02 20:37
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/
data class TitleBean(
    val _id: String,
    val createdAt: String,
    val desc: String,
    val images: List<String>,
    val publishedAt: String,
    val source: String,
    val type: String,
    val url: String,
    val used: Boolean,
    val who: String
)