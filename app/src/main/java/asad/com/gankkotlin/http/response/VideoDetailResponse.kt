package asad.com.gankkotlin.http.response

import com.google.gson.Gson

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.http.response
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-02 20:43
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/

class VideoDetailResponse {
    var retCode: Int = 0
    var retDesc: String? = null
    var data: DataBean? = null
    var succ: Boolean = false


    class DataBean {
        var text: String? = null
        var video: VideoBean? = null

        class VideoBean {
            var link: List<LinkBean>? = null
            var download: List<DownloadBean>? = null
            class LinkBean {


                var url: String? = null
                var buttonText: String? = null
                var type: String? = null
            }

            class DownloadBean {

                var url: String? = null
                var buttonText: String? = null
                var type: String? = null
            }
        }
    }

    override fun toString(): String {
        return Gson().toJson(this)
    }
}