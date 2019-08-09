package asad.com.gankkotlin.ui.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @ProjectName:      GankKotlin
 *@Package:                asad.com.gankkotlin.ui.base
 *@ClassName:           BaseFragment
 *@Autor:                   Asia
 *@CreateDate:         2019/6/25  11:34
 *@UpdateDate:        2019/6/25  11:34
 *@UpdateUser:        (更新人)
 *@UpdateRemark:  (更新说明)
 *@Description:        (java类作用描述)
 *@Version:                (版本号 )
 **/
abstract class BaseFragment<T: ViewDataBinding> :Fragment(){

    lateinit var binding: T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       binding = DataBindingUtil.inflate(inflater,getLayoutID(),container,false)
        initView()
      initListener()
        initData()
        return  binding.root
    }

    open fun  initView(){

    }
    open fun initData(){

    }
    open fun initListener(){

    }

    abstract fun getLayoutID(): Int


}