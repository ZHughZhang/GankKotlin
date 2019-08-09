package asad.com.gankkotlin

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Handler
import android.support.multidex.MultiDex
import com.blankj.utilcode.util.Utils
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader
import com.github.piasy.biv.view.BigImageView
import com.github.piasy.biv.view.GlideImageViewFactory
import com.winton.library.PriorityExecutor
import com.winton.librarystatue.StatusViewFactory
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * @ProjectName:      GankKotlin
 *@Package:                asad.com.gankkotlin
 *@ClassName:           App
 *@Autor:                   Asia
 *@CreateDate:         2019/6/22  13:46
 *@UpdateDate:        2019/6/22  13:46
 *@UpdateUser:        (更新人)
 *@UpdateRemark:  (更新说明)
 *@Description:        (java类作用描述)
 *@Version:                (版本号 )
 **/
@SuppressLint("Registered")
class App: Application(),HasActivityInjector{


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


    companion object {

        var  INSTANCE: App by Delegates.notNull()

        /*优先线程管理器*/
        val appExcuter: PriorityExecutor by lazy {
            PriorityExecutor(2,10,10)
        }

        /*主线程*/
        val mainThread = Thread.currentThread()
        /*主线程ID*/
        val mainThreadId = android.os.Process.myPid()
        var  mUIHandler: Handler by Delegates.notNull()
    }


    override fun activityInjector() = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        INSTANCE = this;
        mUIHandler = Handler()
        //AppInjector.init(this)
        Utils.init(this)
        BigImageViewer.initialize(GlideImageLoader.with(this))
        StatusViewFactory.appConfig().loadingView(R.layout.layout_page_loading)

    }
}