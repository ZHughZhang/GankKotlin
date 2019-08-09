package asad.com.gankkotlin.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Point
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import asad.com.gankkotlin.R
import asad.com.gankkotlin.adapter.NewsAdapter
import asad.com.gankkotlin.databinding.FragmentNewsBinding
import asad.com.gankkotlin.repository.Resource
import asad.com.gankkotlin.ui.base.BaseFragment
import asad.com.gankkotlin.viewmodel.NewsViewModel
import com.blankj.utilcode.util.LogUtils
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.CommonUtil
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper
import com.shuyu.gsyvideoplayer.video.NormalGSYVideoPlayer

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.ui.fragment
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-08 16:30
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/

class NewsFragment : BaseFragment<FragmentNewsBinding>() {

    private var firstVisibleItem = 0
    private var lastVisibleItem = 0
    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter

    private val smallGSYVideoHelper: GSYVideoHelper by lazy {
        GSYVideoHelper(activity,NormalGSYVideoPlayer(activity))
    }

    private val smallGSYVideoHelperBuilder:GSYVideoHelper.GSYVideoHelperBuilder by lazy {
        GSYVideoHelper.GSYVideoHelperBuilder().apply {
            this.isHideActionBar = true
            this.isNeedLockFull = true
            this.isCacheWithPlay = true
            this.isShowFullAnimation = false
            this.isRotateViewAuto = false
            this.isLockLand = false
            this.videoAllCallBack = object : GSYSampleCallBack() {
                override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                    super.onQuitFullscreen(url, *objects)
                    if (smallGSYVideoHelper.playPosition >0 && smallGSYVideoHelper.playTAG == NewsAdapter.TAG){
                        val pos = smallGSYVideoHelper.playPosition
                        if(pos !in firstVisibleItem.. lastVisibleItem){
                            smallGSYVideoHelper.releaseVideoPlayer()
                            adapter.notifyDataSetChanged()
                        }
                    }
                }

                override fun onPrepared(url: String?, vararg objects: Any?) {
                    super.onPrepared(url, *objects)
                    LogUtils.d(url)
                }
            }
        }
    }


    companion object{
        fun newInstance(params: Bundle?): NewsFragment{

            val frag = NewsFragment()

            params?.apply {
                frag.arguments = this
            }

            return frag
        }
    }



    override fun getLayoutID(): Int {
        return  R.layout.fragment_news
    }


    override fun initView() {
        super.initView()
        binding.rv.layoutManager = LinearLayoutManager(context!!)
        smallGSYVideoHelper.setGsyVideoOptionBuilder(smallGSYVideoHelperBuilder)
        binding.rv.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                firstVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                lastVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                if(smallGSYVideoHelper.playPosition >= 0 && smallGSYVideoHelper.playTAG == NewsAdapter.TAG){
                    var currentPos = smallGSYVideoHelper.playPosition
                    if(currentPos !in firstVisibleItem .. lastVisibleItem){
                        val size = CommonUtil.dip2px(context!!,150F)
                        smallGSYVideoHelper.showSmallVideo(Point(size,size),true,true)
                    }
                }else{
                    if (smallGSYVideoHelper.isSmall){
                        smallGSYVideoHelper.smallVideoToNormal()
                    }
                }
            }
        })
    }

    override fun initListener() {
        super.initListener()
        binding.srl.setOnRefreshListener {
            viewModel.loadRefreshData()
        }
    }

    override fun initData() {
        super.initData()
        adapter = NewsAdapter(context!!)
        adapter.setVideoPlayerHelper(smallGSYVideoHelper,smallGSYVideoHelperBuilder)
        binding.rv.adapter = adapter
        viewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        viewModel.getList().observe(this, Observer {
            when(it?.status){
                Resource.LOADING ->{

                }
                Resource.SUCCESS ->{
                    it.data?.data?.let {
                        binding.sv.showContent()
                        binding.srl.isRefreshing = false
                        adapter.refresh(it)
                    }
                }
                Resource.ERROR ->{
                    binding.sv.showContent()
                    binding.srl.isRefreshing = false
                }
            }
        })
        viewModel.loadRefreshData()
        binding.sv.showLoading()
    }
}