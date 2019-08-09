package asad.com.gankkotlin.adapter

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.adapter
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-08 16:42
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/

open class BaseRvHolder  : RecyclerView.ViewHolder {

    lateinit var binding: ViewDataBinding

    constructor(itemView: View) : super(itemView)

    open fun bind(variable: Int,value: Any){
        binding.setVariable(variable,value)
        binding.executePendingBindings()
    }
}