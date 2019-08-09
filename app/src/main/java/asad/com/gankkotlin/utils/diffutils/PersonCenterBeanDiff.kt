package asad.com.gankkotlin.utils.diffutils

import android.support.v7.util.DiffUtil
import asad.com.gankkotlin.http.bean.PersonCenterBean

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.utils.diffutils
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-05 20:54
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/

class PersonCenterBeanDiff : DiffUtil.ItemCallback<PersonCenterBean>(){
    override fun areItemsTheSame(p0: PersonCenterBean, p1: PersonCenterBean): Boolean {

        return p0.title == p1.title

    }

    override fun areContentsTheSame(p0: PersonCenterBean, p1: PersonCenterBean): Boolean {
        return p0.title == p1.title
    }

}