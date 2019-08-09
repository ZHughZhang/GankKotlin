package asad.com.gankkotlin.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.media.Image
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import asad.com.gankkotlin.App
import asad.com.gankkotlin.R
import asad.com.gankkotlin.databinding.ItemMeCenterBinding
import asad.com.gankkotlin.databinding.ItemNewsListVideoBinding
import asad.com.gankkotlin.http.ErrorCode
import asad.com.gankkotlin.http.bean.NewsContent
import asad.com.gankkotlin.utils.BindingUtils
import asad.com.gankkotlin.utils.VideoPathDecoder
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper
import com.shuyu.gsyvideoplayer.view.SmallVideoTouch

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.adapter
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-09 19:58
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/
class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder>{
    companion object{
        const val TAG = "NewsAdapter"
    }

    private var mData: ArrayList<NewsContent> = ArrayList()
    private var mContext: Context
    private lateinit var smallVideoHelper: GSYVideoHelper

    private lateinit var gsySmallVideoHelper: GSYVideoHelper.GSYVideoHelperBuilder

    constructor(mContext: Context): super() {
        this.mContext = mContext
    }

    fun refresh(data: ArrayList<NewsContent>){
        mData.addAll(0,data)
        notifyDataSetChanged()
    }

    fun more(data: ArrayList<NewsContent>){
        val oldSize = mData.size
        mData.addAll(data)
        notifyItemRangeInserted(oldSize,data.size)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NewsAdapter.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemNewsListVideoBinding>(LayoutInflater.from(mContext), R.layout.item_news_list_video,p0,false)
        val holder = ViewHolder(this,binding)
        holder.setVideoPlayerHelper(smallVideoHelper!!,gsySmallVideoHelper!!)
        return holder
    }

    override fun getItemCount(): Int =mData.size

    override fun onBindViewHolder(p0: NewsAdapter.ViewHolder, p1: Int) {
        p0.bindData(mData[p1],p1)
        p0.itemView.tag = mData[p1]
    }


    class ViewHolder: BaseRvHolder{
        private lateinit var smallVideoHelper: GSYVideoHelper

        private lateinit var gsyVideoHelper: GSYVideoHelper.GSYVideoHelperBuilder

        private var imageVIew:ImageView
        private var adapter:NewsAdapter

        constructor(adapter: NewsAdapter,binding: ViewDataBinding):super(binding.root){
            this.binding = binding
            imageVIew = ImageView(binding.root.context)
            imageVIew.scaleType = ImageView.ScaleType.FIT_XY
            this.adapter = adapter
        }

        fun  bindData(news: NewsContent,position: Int){
            (binding as ItemNewsListVideoBinding).run {
                BindingUtils.bindArticleImg(imageVIew,news.video_detail_info.detail_video_large_image.url)
                smallVideoHelper.addVideoPlayer(position,imageVIew, TAG,itemContainer,playBtn)
                this.title.text = news.title
                playBtn.setOnClickListener{
                    adapter.notifyDataSetChanged()
                    smallVideoHelper.setPlayPositionAndTag(position,TAG)
                    gsyVideoHelper.videoTitle = news.title
                   /* {
                        val decoder = object: VideoPathDecoder(){
                            override fun onDecodeSuccess(url: String?) {
                                App.mUIHandler.post {
                                    url?.let {
                                        LogUtils.dTag("windton","playurl: $it")
                                        gsyVideoHelper.url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
                                        smallVideoHelper.startPlay()
                                    }
                                }
                            }

                            override fun onDecodeFailure(code: ErrorCode) {
                                ToastUtils.showShort("解析数据失败")
                            }
                        }
                        decoder.decodePath(news.url)
                    }*/

                    gsyVideoHelper.url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
                    smallVideoHelper.startPlay()


                }
            }
        }


        fun setVideoPlayerHelper(videoHelper: GSYVideoHelper,videoHelperBuilder: GSYVideoHelper.GSYVideoHelperBuilder){
            smallVideoHelper = videoHelper
            gsyVideoHelper = videoHelperBuilder
        }
    }

    fun setVideoPlayerHelper(videoHelper: GSYVideoHelper,videoHelperBuilder: GSYVideoHelper.GSYVideoHelperBuilder){
        smallVideoHelper = videoHelper
        gsySmallVideoHelper = videoHelperBuilder
    }

}