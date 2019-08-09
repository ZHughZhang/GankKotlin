package asad.com.gankkotlin.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import asad.com.gankkotlin.R
import asad.com.gankkotlin.adapter.mulitype.GankListAdapter
import asad.com.gankkotlin.adapter.mulitype.IndexItem
import asad.com.gankkotlin.databinding.FragmentListCommonBinding
import asad.com.gankkotlin.repository.Resource
import asad.com.gankkotlin.ui.activity.WebActivity
import asad.com.gankkotlin.ui.base.BaseFragment
import asad.com.gankkotlin.utils.UiTools
import asad.com.gankkotlin.viewmodel.GankListVewModel
import com.blankj.utilcode.util.ToastUtils

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

class GankListFragment : BaseFragment<FragmentListCommonBinding>(){

    private var category: String? = null
    private var pageindex = 1
    private var hasNext = true

    private lateinit var adapter: GankListAdapter
    private lateinit var viewModel: GankListVewModel


    companion object {
        const val CATEGORY = "category"

        fun newInstance(params: Bundle?): GankListFragment{
            val frag = GankListFragment();
            params.apply {
                frag.arguments = this
            }
            return frag
        }

    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_list_common
    }

    override fun initView() {
        super.initView()
        binding.rvIndex.layoutManager = LinearLayoutManager(context!!)
        binding.srl.setOnRefreshListener {
            pageindex =1
            category?.let {
                viewModel.loadData(it,pageindex)
            }
        }
        UiTools.initSwipRefresh(binding.srl)
    }

    override fun initListener() {
        super.initListener()
        binding.rvIndex.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            var lastVisibleItem = 0
            var isEnd= false
            var firstVisibleItem = 0

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem !=0 && !isEnd && hasNext){
                        if (lastVisibleItem >= adapter.itemCount -3){
                            loadMore()
                        }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(recyclerView.layoutManager is LinearLayoutManager){
                    (recyclerView.layoutManager as LinearLayoutManager).let {
                        firstVisibleItem = it.findFirstCompletelyVisibleItemPosition()
                        lastVisibleItem = it.findLastCompletelyVisibleItemPosition()
                        isEnd = firstVisibleItem ==0
                    }
                }
            }

        })
    }


    override fun initData() {
        super.initData()
        adapter = GankListAdapter(context!!);
        adapter.setItemClickListener(object: GankListAdapter.OnItemClick{
            override fun onItemClick(item: IndexItem) {
                val url = item.item?.url
                if (url != null) {
                    WebActivity.start(context!!,url)
                }else{
                    ToastUtils.showShort("链接为空")
                }

            }

        })
        binding.rvIndex.adapter = adapter
        category = arguments?.getString(CATEGORY)
        viewModel = ViewModelProviders.of(this).get(GankListVewModel::class.java)
        viewModel.getListdata().observe(this, Observer {
            when(it?.status){
                Resource.LOADING -> {
                    if (pageindex == 1) {
                        binding.status.showLoading()
                    }
                }
                Resource.SUCCESS -> {
                    binding.srl.isRefreshing = false
                    binding.status.showContent()
                    it?.data?.results?.run {
                        if(pageindex > 1){
                            adapter.add(this)
                        }else{
                            adapter.update(this)
                        }
                        if (this.size<15){
                            hasNext = false
                        }
                    }
                }
                Resource.ERROR -> {
                    binding.srl.isRefreshing = false
                    if (pageindex ==1){
                        binding.status.showError()
                    }else{
                        ToastUtils.showShort("加载出错")
                    }
                }
            }
        })
        category?.let{
            binding.status.showLoading()
            viewModel.loadData(it,pageindex)
        }
    }

    private fun loadMore () {
        pageindex ++
        category?.let {
            viewModel.loadData(it,pageindex)
        }
    }

}