package asad.com.gankkotlin.ui.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import asad.com.gankkotlin.R
import asad.com.gankkotlin.databinding.ActivityImgBinding
import asad.com.gankkotlin.ui.base.BaseActivity
import com.github.piasy.biv.view.BigImageView
import com.github.piasy.biv.view.GlideImageViewFactory

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
class ImageActivity : BaseActivity<ActivityImgBinding>() {



    companion object{
        fun  start(context: Context,urls: ArrayList<String>){
                var intent: Intent = Intent(context,ImageActivity::class.java)
                intent.putExtra("url",urls)
                context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_img
    }


    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        val url = intent.getStringArrayListExtra("url")
        binding.vp.adapter = ImageAdapter(this,url)
    }


    class  ImageAdapter: PagerAdapter{

        private var mData: ArrayList<String>
        private var mContext: Context

        constructor(context: Context,data:ArrayList<String>): super(){
            mData = data
            mContext=context
        }



        override fun isViewFromObject(p0: View, p1: Any): Boolean {
            return p0 == p1
        }

        override fun getCount(): Int {
           return mData.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {

            var imageView = BigImageView(mContext)
            val url = mData[position]
            imageView.showImage(Uri.parse(url))
            imageView.setOnClickListener{
                (mContext as ImageActivity).finish()
            }
            imageView.setInitScaleType(BigImageView.INIT_SCALE_TYPE_CENTER_CROP)
            imageView.setImageViewFactory(GlideImageViewFactory())
            container.addView(imageView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
            return imageView

        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

            container.removeView(`object` as View)
        }
    }
}
