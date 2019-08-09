package asad.com.gankkotlin.ui.activity

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import asad.com.gankkotlin.BuildConfig
import asad.com.gankkotlin.R
import asad.com.gankkotlin.databinding.ActivitySplashBinding
import asad.com.gankkotlin.ui.base.BaseActivity

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.ui.activity
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-08 16:28
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/

class SplashActivity: BaseActivity<ActivitySplashBinding> () {


    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        binding.version.text = "v"+BuildConfig.VERSION_NAME
        binding.logo.startAnimation(AnimationUtils.loadAnimation(this@SplashActivity,R.anim.splash_logo_anim))
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }


    override fun onResume() {
        super.onResume()
        binding.root.postDelayed({
            MainActivity.start(this@SplashActivity)
            this@SplashActivity.finish()
        },2000)
    }

}