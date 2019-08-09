package asad.com.gankkotlin.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.ColorSpace
import android.os.BaseBundle
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.transition.Explode
import android.view.Window
import asad.com.gankkotlin.R
import asad.com.gankkotlin.databinding.ActivityMainBinding
import asad.com.gankkotlin.ui.base.BaseActivity
import asad.com.gankkotlin.ui.fragment.*
import com.blankj.utilcode.util.ToastUtils
import com.githang.statusbar.StatusBarCompat
import com.winton.bottomnavigationview.NavigationView
import com.zxing.activity.CodeUtils
import com.zxing.activity.ScanActivity

class MainActivity :BaseActivity<ActivityMainBinding> (){

    private lateinit var mNV: NavigationView
    private lateinit var nvItems: ArrayList<NavigationView.Model>

    private lateinit var fragment: ArrayList<Model>

    //下次按下返回键的时间
    private var lastDownBackKey = 0L
    private var currentIndex = -1
    private val fm: FragmentManager by lazy {
        supportFragmentManager
    }

    companion object{
        fun start (context: Context) {
            var intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)

        }
        const val REQ_SCAN = 1
    }


    override fun initPreLayout(savedInstanceState: Bundle?) {
        super.initPreLayout(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            window.enterTransition = Explode()
            window.exitTransition = Explode()
        }
    }

    override fun getLayoutId(): Int {
       return R.layout.activity_main
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        mNV = binding.nv
        initFragment()
        initNavigation()


    }


    private fun initFragment(){
        fragment = ArrayList()
        fragment.apply {
            this.add(Model("index",IndexFragment.newInstance(null)))
            this.add(Model("news",NewsFragment.newInstance(null)))
            this.add(Model("girls",JDGirlsFragment.newInstance(null)))
            this.add(Model("me",MyFragment.newInstance(null)))
        }
    }

    private fun  initNavigation() {
        nvItems = ArrayList()
        nvItems.apply {
            this.add(NavigationView.Model.Builder(R.mipmap.index_check,R.mipmap.index_uncheck).title("首页").build())
            this.add(NavigationView.Model.Builder(R.mipmap.news_check,R.mipmap.news_uncheck).title("新闻").build())
            this.add(NavigationView.Model.Builder(R.mipmap.girl_check,R.mipmap.girl_uncheck).title("打望").build())
            this.add(NavigationView.Model.Builder(R.mipmap.me_check,R.mipmap.me_uncheck).title("我的").build())
        }
        mNV.setItems(nvItems)
        mNV.build()
        mNV.setOnTabSelectedListener(object: NavigationView.OnTabSelectedListener{
            override fun unselected(p0: Int, p1: NavigationView.Model?) {

            }

            override fun selected(p0: Int, p1: NavigationView.Model?) {
                changeFragment(p0)
                when(p0){
                    3->{
                        StatusBarCompat.setStatusBarColor(this@MainActivity,Color.BLACK,false)
                    }
                    1,2 -> {
                        StatusBarCompat.setStatusBarColor(this@MainActivity,ContextCompat.getColor(this@MainActivity,android.R.color.holo_blue_light),false)
                    }
                    else ->{
                        StatusBarCompat.setStatusBarColor(this@MainActivity,Color.WHITE,true)
                    }
                }
            }

        })

        mNV.check(0)
        changeFragment(0)
        StatusBarCompat.setStatusBarColor(this@MainActivity,Color.WHITE,true)
    }

    private fun changeFragment(index: Int){
        if (index == currentIndex){
            return
        }

        val lastIndex = currentIndex
        currentIndex = index
        val model = fragment[index]
        val ft = fm.beginTransaction()
        if (!model.fragment.isAdded){
            ft.add(R.id.fl_content,model.fragment,model.tag)
        }
        if (lastIndex !=-1){
            ft.hide(fragment[lastIndex].fragment)
        }
        ft.show(model.fragment)
        ft.commit()
    }

    override fun onBackPressed() {
       val currentBackTime = System.currentTimeMillis()
        if (currentBackTime - lastDownBackKey > 2000){
            ToastUtils.showShort("再按一次返回键退出")
            lastDownBackKey = currentBackTime
        }else{
            finish()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK){
            return
        }
        when(requestCode) {
            REQ_SCAN -> {
                val type = data!!.getIntExtra(CodeUtils.RESULT_TYPE,-1)
                val result = data!!.getStringExtra(CodeUtils.RESULT_STRING)
                if(type == CodeUtils.RESULT_SUCCESS){
                    ScanResultActivity.start(this@MainActivity,result)
                }else{
                    ToastUtils.showShort("扫描失败")
                }
            }
        }
    }






    private class Model(tag: String, fragment: Fragment) {
        val tag = tag
        val fragment = fragment
    }

}
