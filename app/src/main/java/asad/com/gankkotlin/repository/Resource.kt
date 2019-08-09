package asad.com.gankkotlin.repository

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.repository
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-02 17:17
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/
class Resource<T: Any?> {
    val status: Int
    val data: T?
    val msg: String?

    constructor(status: Int,data: T?,msg: String?){
        this.status = status
        this.data = data
        this.msg =msg
    }

    companion object{
        const val  ERROR = 0
        const val LOADING = 1
        const val SUCCESS = 2

        fun <T> success(data: T): Resource<T>{
            return Resource(SUCCESS,data,null)
        }
        fun <T> error(data: T?,msg: String?): Resource<T>{
            return Resource(ERROR,data,msg)
        }

        fun  <T> loading(data: T?): Resource<T>{
            return Resource(LOADING,data,null)
        }
    }

}