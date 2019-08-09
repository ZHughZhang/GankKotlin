package asad.com.gankkotlin.utils

import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import asad.com.gankkotlin.App
import asad.com.gankkotlin.http.ErrorCode
import asad.com.gankkotlin.http.RetroFitHolder
import asad.com.gankkotlin.http.VideoDetailSubscriber
import asad.com.gankkotlin.http.response.VideoDetailResponse
import com.blankj.utilcode.util.LogUtils
import retrofit2.Retrofit

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.utils
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-05 20:56
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/
abstract class VideoPathDecoder {
    companion object {

        const val NICK: String = "winton"
    }


    fun decodePath(url:String) {
        val webView = WebView(App.INSTANCE)
        webView.settings.javaScriptEnabled = true
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE

        val parseFunc = ParseFunc(object : ParseFunc.ParseListener {

            override fun onGetParams(r: String, s: String) {
                sendRequest(url,r,s)

            }
            override fun onGetPath(path: String){
                LogUtils.dTag("decoder",path)
                onDecodeSuccess(path)
            }
        })
        webView.addJavascriptInterface(parseFunc, NICK)
        webView.loadUrl(url)
        webView.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                App.mUIHandler.postDelayed({
                    if (view != null) {
                        addJs(view)
                    }
                },5000)
            }
        }
    }
    private fun sendRequest(url: String,r: String,s: String){
        RetroFitHolder.wInstance().getVideoDetail(url,r,s,object : VideoDetailSubscriber(){
            override fun onSuccess(t: VideoDetailResponse) {
                val videos = t.data?.video?.download

                videos.let {
                    if (it!!.isNotEmpty()){
                        LogUtils.dTag("winton","video bean${it.size}")
                        onDecodeSuccess(it[it.size-1].url)
                    }
                }

            }

            override fun onFail(code: ErrorCode, errorMsg: String) {
                when(code){
                    ErrorCode.DATA_ERROR ->{
                        decodePath(url)
                    }
                    else -> {
                        onDecodeFailure(code)
                    }
                }
            }

        })
    }

    private fun addJs(webView: WebView){
        val js =  "javascript:(function getVideoPath(){" +
                "var videos = window.document.getElementsByTagName(\"video\");" +
                "window.winton.onGetPath(document.title);" +
                "var path = videos[0].src;" +
                "window.winton.onGetPath(path);" +
                "})();"

        webView.loadUrl(js)
    }


    abstract fun onDecodeSuccess(url: String?)
    abstract fun onDecodeFailure(code: ErrorCode)

    class ParseFunc(val listener: ParseListener){



        @JavascriptInterface
        fun onReceivrPrams(r:String, s:String){
            this.listener.onGetParams(r,s)
        }

        @JavascriptInterface
        fun onGetPath(path:String){
            this.listener.onGetPath(path)
        }


        interface ParseListener {
            fun onGetParams(r: String,s: String)
            fun  onGetPath(path: String)
        }
    }
}