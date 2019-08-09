package asad.com.gankkotlin.utils

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.utils
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-05 20:55
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/

object StringUtils{
    fun getGankReadTime(time: String): String{
        return time.split("T")[0]
    }
}