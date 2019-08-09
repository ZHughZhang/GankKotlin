package asad.com.gankkotlin.ui.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity

/**
 * @ProjectName:      GankKotlin
 *@Package:                asad.com.gankkotlin.ui.base
 *@ClassName:           BaseActivity
 *@Autor:                   Asia
 *@CreateDate:         2019/6/25  11:26
 *@UpdateDate:        2019/6/25  11:26
 *@UpdateUser:        (更新人)
 *@UpdateRemark:  (更新说明)
 *@Description:        (java类作用描述)
 *@Version:                (版本号 )
 **/
abstract class  BaseActivity<T: ViewDataBinding>: AppCompatActivity() {
    val TAG = this.javaClass.name
    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPreLayout(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,getLayoutId())
        iniListener()
        initData(savedInstanceState)


    }

    /**
     * 设置布局
     * */

    abstract fun getLayoutId(): Int

    /**
     * 在初始化布局之前调用
     *
     * */

    open fun initPreLayout(savedInstanceState: Bundle?){

    }

    /**
     * 注册监听器
     * */
    open fun iniListener() {

    }

    /**
     * 初始化数据
     * */

    open fun initData(savedInstanceState: Bundle?){

    }


}