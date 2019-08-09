package asad.com.gankkotlin.http.bean

import android.content.Intent

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.http.bean
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-02 20:33
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/

data class PersonCenterBean(
    val title:String
):Comparable<PersonCenterBean>{
    var iconId:Int = -1
    var type:BeanType = BeanType.URL
    var url:String = ""
    var intent: Intent? = null
    var order = -1

    override fun compareTo(other: PersonCenterBean): Int {
        return this.order - other.order
    }
}

enum class BeanType{
    URL,
    ACTIVITY
}