package asad.com.gankkotlin.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import asad.com.gankkotlin.R
import asad.com.gankkotlin.adapter.JDGirlsAdapter
import asad.com.gankkotlin.databinding.FragmentGirlsBinding
import asad.com.gankkotlin.http.api.JDApi
import asad.com.gankkotlin.http.response.Comment
import asad.com.gankkotlin.repository.Resource
import asad.com.gankkotlin.ui.activity.ImageActivity
import asad.com.gankkotlin.ui.base.BaseFragment
import asad.com.gankkotlin.viewmodel.JDViewModel
import retrofit2.http.PATCH

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
class JDGirlsFragment : BaseFragment<FragmentGirlsBinding>() {


    private lateinit var viewModel: JDViewModel
    private var pageIndex = 1
    private var isDoRefresh = false
    private var hasNext = true
    private lateinit var adapter: JDGirlsAdapter
    private val spanCount = 2

    companion object {
        fun newInstance(params: Bundle?): JDGirlsFragment {
            val frag = JDGirlsFragment()
            params?.apply {
                frag.arguments = this
            }
            return frag
        }
    }


    override fun getLayoutID(): Int {
        return R.layout.fragment_girls
    }

    override fun initListener() {
        super.initListener()
        binding.srl.setOnRefreshListener {
            pageIndex = 1
            isDoRefresh = true
            viewModel.loadData(JDApi.TYPE_GIRL, pageIndex)
        }
        binding.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var lastVisibleItemPos = 0
            var firstVisibleItemPos = 0
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && hasNext) {
                    lastVisibleItemPos =
                        (recyclerView.layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()
                    firstVisibleItemPos =
                        (recyclerView.layoutManager as GridLayoutManager).findFirstCompletelyVisibleItemPosition()
                    if (firstVisibleItemPos != 0 && lastVisibleItemPos >= adapter.itemCount - spanCount * 2) {
                        loadMore()
                    }
                }

            }

        })
    }

    override fun initData() {
        super.initData()
        adapter = JDGirlsAdapter(context!!)
        adapter.setOnItemClickListener(object : JDGirlsAdapter.OnItemClick {
            override fun onItemClick(item: Comment) {
                ImageActivity.start(activity!!, ArrayList(item.pics))
            }
        })
        binding.rv.layoutManager = GridLayoutManager(context!!, spanCount)
        binding.rv.adapter = adapter
        viewModel = ViewModelProviders.of(this).get(JDViewModel::class.java)
        viewModel.getData().observe(this, Observer {
            when (it?.status) {
                Resource.LOADING -> {
                    if (pageIndex == 1 && isDoRefresh){
                        binding.sv.showLoading()
                    }
                }
                Resource.SUCCESS -> {
                    binding.sv.showContent()
                    binding.srl.isRefreshing = false
                    if (pageIndex == 1) {
                        it.data?.comments?.let {
                            adapter.update(ArrayList(it))
                        }
                    }else {
                        it.data?.comments?.let {
                            adapter.add(ArrayList(it))
                        }
                    }
                    if (it.data?.current_page == it.data?.page_count){
                        hasNext = false
                    }
                }
            }
        })
        viewModel.loadData(JDApi.TYPE_GIRL,pageIndex)
    }

    fun loadMore (){
        pageIndex ++
        viewModel.loadData(JDApi.TYPE_GIRL,pageIndex)
    }

}