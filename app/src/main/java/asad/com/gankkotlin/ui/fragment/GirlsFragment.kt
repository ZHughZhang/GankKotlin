package asad.com.gankkotlin.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import asad.com.gankkotlin.R
import asad.com.gankkotlin.adapter.GirlsAdapter
import asad.com.gankkotlin.databinding.FragmentGirlsBinding
import asad.com.gankkotlin.http.bean.TitleBean
import asad.com.gankkotlin.repository.Resource
import asad.com.gankkotlin.ui.activity.ImageActivity
import asad.com.gankkotlin.ui.base.BaseFragment
import asad.com.gankkotlin.utils.UiTools
import asad.com.gankkotlin.viewmodel.GankListVewModel

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.ui.fragment
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
class GirlsFragment : BaseFragment<FragmentGirlsBinding>(){

    private var category:String? = null
    private var pageIndex = 1
    private var hasNext = true
    private val spanCount = 2
    private var showTitle = false
    private lateinit var adapter: GirlsAdapter
    private lateinit var viewModel: GankListVewModel



    companion object{
        const val CATEGORY = "category"
        const val SHOW_TITLE = "show_title"

        fun newInstance(params: Bundle?): GirlsFragment{
            var frag = GirlsFragment()
            params?.apply {
                frag.arguments = this
            }

            return frag
        }
    }



    override fun getLayoutID(): Int {
        return R.layout.fragment_girls
    }


    override fun initView() {
        binding.rv.layoutManager = StaggeredGridLayoutManager(spanCount,StaggeredGridLayoutManager.VERTICAL)
        UiTools.initSwipRefresh(binding.srl)
    }

    override fun initListener() {
        super.initListener()
        binding.rv.addOnScrollListener(object: RecyclerView.OnScrollListener(){

            var visibleItem: IntArray = IntArray(spanCount)


            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE&& hasNext){
                    (recyclerView.layoutManager as  StaggeredGridLayoutManager ).findLastCompletelyVisibleItemPositions(visibleItem)
                    for (pos in visibleItem){
                        if (pos >= adapter.itemCount -1 -(2* spanCount)){
                            loadMore()
                            break
                        }
                    }
                }
            }

        })

        binding.srl.setOnRefreshListener {
            category?.let {
                pageIndex = 1
                viewModel.loadData(it,pageIndex)
            }
        }
    }

    override fun initData() {

        adapter = GirlsAdapter(context!!)
        adapter.setOnItemClick(object : GirlsAdapter.OnItemClick{
            override fun onItemClick(bean: TitleBean) {
                ImageActivity.start(activity!!,ArrayList<String>().apply { add(bean.url) })
            }
        })
        binding.rv.adapter = adapter

        category = arguments?.getString(CATEGORY)
        showTitle = arguments?.getBoolean(SHOW_TITLE,true)?: true
        if (!showTitle){
            binding.tvTitle.visibility = View.GONE
        }
        viewModel = ViewModelProviders.of(this).get(GankListVewModel::class.java)
        viewModel.getListdata().observe(this, Observer {
            when(it?.status){
                Resource.LOADING -> {
                    if (pageIndex ==1) {
                        binding.sv.showLoading()
                    }
                }
                Resource.SUCCESS -> {
                    binding.sv.showContent()
                    binding.srl.isRefreshing = false
                    it.data?.results?.run {
                        if (pageIndex >1){
                            adapter.add(this)
                        }else {
                            adapter.update(this)
                        }
                        if (this.size <15){
                            hasNext = false
                        }
                    }
                }
                Resource.ERROR -> {
                    binding.srl.isRefreshing = false
                    if (pageIndex ==1) {
                        binding.sv.showError()
                    }
                }
            }
        })
        category?.let {
            binding.sv.showLoading()
            viewModel.loadData(it,pageIndex)
        }
    }

    private fun loadMore() {
        category?.let {
            pageIndex ++
            viewModel.loadData(it,pageIndex)
        }

    }
}


