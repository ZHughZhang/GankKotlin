package asad.com.gankkotlin.utils.diffutils

import android.support.v7.util.DiffUtil

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.utils.diffutils
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-05 20:53
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc DiffUtils 异常
 **/

abstract  class BaseDiffCallBack <T>(var oldList: List<T>,var newList: List<T>): DiffUtil.Callback() {
    abstract override fun areItemsTheSame(p0: Int, p1: Int): Boolean

    open override fun getOldListSize(): Int {

        return oldList.size

    }

    open override fun getNewListSize(): Int {
        return newList.size

    }

   abstract open override fun areContentsTheSame(p0: Int, p1: Int): Boolean

}