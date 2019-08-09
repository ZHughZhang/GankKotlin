package asad.com.gankkotlin.http.response


/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.http.response
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-02 20:38
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/
data class JDListResponse (
    val status: String,
    val current_page:Int,
    val total_comments: Int,
    val page_count: Int,
    val comments: List<Comment>
)

data class Comment (
    val comment_ID: String,
    val comment_post_ID: String,
    val comment_author: String,
    val comment_date: String,
    val comment_date_gmt: String,
    val comment_content: String,
    val user_id: String,
    val vote_positive: String,
    val vote_negative: String,
    val sub_comment_count: String,
    val text_content: String,
    val pics: List<String>
)