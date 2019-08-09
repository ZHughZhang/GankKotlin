package asad.com.gankkotlin.utils.glide

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.utils.glide
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-05 20:54
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/

@GlideModule
class CustomGlideModule : AppGlideModule(){
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDefaultRequestOptions(RequestOptions().format(DecodeFormat.PREFER_RGB_565))
    }
}