package asad.com.gankkotlin.utils

import android.widget.ImageView
import asad.com.gankkotlin.R
import asad.com.gankkotlin.utils.glide.GlideApp
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade

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
object BindingUtils {
    fun bindGift(imageView: ImageView,url: String){
        GlideApp.with(imageView.context)
            .load(url)
            .error(R.mipmap.default_img)
            .circleCrop()
            .transition(withCrossFade())
            .into(imageView)
    }

    fun bindArticleImg(imageView: ImageView,url: String){
        GlideApp.with(imageView.context)
            .load(url)
            .error(R.mipmap.default_img)
            .transition(withCrossFade())
            .into(imageView)
    }


}