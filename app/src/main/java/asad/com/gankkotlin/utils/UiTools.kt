package asad.com.gankkotlin.utils

import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.view.Window
import android.view.WindowManager
import asad.com.gankkotlin.App
import asad.com.gankkotlin.R

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
object UiTools{
    fun  fitTitleBar(window: Window,color: Int){
        /*沉浸式代码*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN).and(View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
        }


    }
    fun initSwipRefresh(view: SwipeRefreshLayout){
        view.setColorSchemeColors(ContextCompat.getColor(App.INSTANCE,R.color.swip_color_1),ContextCompat.getColor(App.INSTANCE,R.color.swip_color_2),ContextCompat.getColor(App.INSTANCE,R.color.swip_color_3))
    }
}