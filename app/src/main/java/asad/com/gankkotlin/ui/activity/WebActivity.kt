package asad.com.gankkotlin.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.FrameLayout
import asad.com.gankkotlin.App
import asad.com.gankkotlin.R
import asad.com.gankkotlin.databinding.ActivityWebBinding
import asad.com.gankkotlin.ui.base.BaseActivity
import com.githang.statusbar.StatusBarCompat
import kotlinx.android.synthetic.main.activity_web.view.*

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.ui.activity
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-08 16:29
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/
class WebActivity : BaseActivity<ActivityWebBinding> (){

    private lateinit var url: String

    private val webView: WebView by lazy {
        WebView(App.INSTANCE)
    }


    companion object{
        fun  start(context: Context,url:String ){
            var intent = Intent(context,WebActivity::class.java)
            intent.putExtra("url",url)
            context.startActivity(intent)
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_web
    }

    override fun onResume() {
        super.onResume()
        StatusBarCompat.setStatusBarColor(this, Color.WHITE,true)
    }

    override fun iniListener() {
        super.iniListener()
        binding.viBack.setOnClickListener {
            if (webView.canGoBack()){
                webView.goBack()
            }else{

                this@WebActivity.finish()
            }
        }
    }

    override fun onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack()
        }else{
            super.onBackPressed()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        url = intent.getStringExtra("url")
        initWebView()
        webView.loadUrl(url)
    }
    private fun initWebView(){
        var param = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        binding.fl.addView(webView,param)
        val settings = webView.settings
        //支持javaScript
        settings.javaScriptEnabled = true
        //自适应屏幕
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        //支持缩放
        settings.setSupportZoom(true)
        settings.builtInZoomControls =true
        settings.displayZoomControls = true

        //设置缓存
        settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        //允许访问文件
        settings.allowFileAccess = true
        //自动加载图片
        settings.loadsImagesAutomatically = true
        //默认编码格式
        settings.defaultTextEncodingName = "utf-8"

        webView.webViewClient = object: WebViewClient() {
            //在应用中打开url
            override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {

                webView.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
            }
        }
        webView.webChromeClient = object: WebChromeClient(){
            override fun onReceivedTitle(view: WebView?, title: String?) {
                title?.let {
                    binding.tvTitle.text = it
                }
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress == 100){
                    binding.fl.loading.visibility = View.GONE
                }
            }
        }

    }

    override fun onDestroy() {

        webView.loadDataWithBaseURL(null,"","text/html","utf-8",null)
        webView.clearHistory()
        binding.fl.removeView(webView)
        webView.destroy()

        super.onDestroy()
    }


}