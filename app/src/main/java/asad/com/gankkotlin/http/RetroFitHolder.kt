package asad.com.gankkotlin.http

import asad.com.gankkotlin.App
import asad.com.gankkotlin.constant.DeveloperConfig
import asad.com.gankkotlin.http.retrofit.GankRetrofitClient
import asad.com.gankkotlin.http.retrofit.JDRetrofitClient
import asad.com.gankkotlin.http.retrofit.WeixunRetrofit
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.OkHttpClient
import java.sql.Time
import java.util.concurrent.TimeUnit

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.http
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-06 19:46
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/
object RetroFitHolder {
    /*
    * 默认时间
    * */
    private const val DEFAULT_TIMEOUT = 15L

    private val mOkhttpClient: OkHttpClient

    private var gankInstance: GankRetrofitClient? = null
    private var jdInstance: JDRetrofitClient? = null
    private var wInstance: WeixunRetrofit? = null

    init {
        val cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(App.INSTANCE))
        val okhttpBuilder = OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .cookieJar(cookieJar)
        if (DeveloperConfig.isDebug) {
            okhttpBuilder.addInterceptor(LoggerInterceptor("okhttp"))

        }
        mOkhttpClient = okhttpBuilder.build()
    }


    /*获取Gank Server*/
    fun gankInstance(): GankRetrofitClient {
        if (gankInstance == null)
            gankInstance = GankRetrofitClient(mOkhttpClient)

        return gankInstance!!
    }

    /*获取煎蛋 server*/
    fun jdInstance(): JDRetrofitClient {
        if (jdInstance == null)
            jdInstance = JDRetrofitClient(mOkhttpClient)
        return jdInstance!!
    }

    /*获取weixun 客户端*/

    fun wInstance(): WeixunRetrofit{
        if (wInstance == null){
            wInstance = WeixunRetrofit(mOkhttpClient)
        }
        return wInstance!!
    }


}