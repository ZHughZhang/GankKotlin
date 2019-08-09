package asad.com.gankkotlin.adapter

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.adapter
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-08 16:41
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/
abstract  class BaseRVAdapter<T,V:BaseRvHolder> : ListAdapter<T, V> {

    constructor(diffcallBack: DiffUtil.ItemCallback<T>): super(diffcallBack)

}