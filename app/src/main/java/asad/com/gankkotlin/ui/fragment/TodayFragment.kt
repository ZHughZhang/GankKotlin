package asad.com.gankkotlin.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.media.Image
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import asad.com.gankkotlin.R
import asad.com.gankkotlin.adapter.IndexVPAdapter
import asad.com.gankkotlin.adapter.mulitype.IndexAdapter
import asad.com.gankkotlin.adapter.mulitype.IndexItem
import asad.com.gankkotlin.databinding.FragmentListCommonBinding
import asad.com.gankkotlin.http.response.gank.ResultBean
import asad.com.gankkotlin.repository.Resource
import asad.com.gankkotlin.ui.activity.ImageActivity
import asad.com.gankkotlin.ui.base.BaseFragment
import asad.com.gankkotlin.utils.UiTools
import asad.com.gankkotlin.viewmodel.TodayViewModel
import com.blankj.utilcode.util.ToastUtils
import com.winton.librarystatue.IStatueListener

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


class TodayFragment : BaseFragment<FragmentListCommonBinding>() {

    private var isFirstLoad = true
    private lateinit var viewModel: TodayViewModel
    private lateinit var adapter: IndexAdapter



    companion object {
        fun newInstance(params: Bundle?): TodayFragment {
            var frag = TodayFragment()
            params?.apply {
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
        UiTools.initSwipRefresh(binding.srl)
        binding.srl.setOnRefreshListener {
            viewModel.start()
        }
        binding.status.mRetryListener = object : IStatueListener{
            override fun onRetry() {
                viewModel.start()
            }
        }
    }

    override fun initData() {
        super.initData()

        viewModel  =ViewModelProviders.of(this).get(TodayViewModel::class.java)
        adapter = IndexAdapter(context!!)
        adapter.registerOnItemClickListener(object : IndexAdapter.OnItemClick{
            override fun onItemClick(item: IndexItem) {
                when(item.getType()){
                    IndexAdapter.T_IMAGE -> {
                        val url = item.item?.url
                        url?.run {
                            if (this.isNotEmpty()){
                                ImageActivity.start(context!!,ArrayList<String>().apply { add(url) })
                            }else{
                                ToastUtils.showLong("链接为空")
                            }
                        }
                    }
                    else -> {
                        var url = item.item?.url
                        url?.run {
                            if (this.isNotEmpty()){
                                ImageActivity.start(context!!,ArrayList<String>().apply { add(url) })
                            }else{
                                ToastUtils.showLong("链接为空")
                            }
                        }
                    }
                }
            }

        })
        binding.rvIndex.adapter = adapter
        viewModel.getTodayData().observe(this, Observer {
            it?.let {
                when(it.status){
                    Resource.ERROR ->{
                        binding.status.showError()
                    }
                    Resource.SUCCESS -> {
                        submitResult(it.data?.results)
                        binding.status.showContent()
                        binding.srl.isRefreshing = false
                    }
                    Resource.LOADING ->{
                        if (isFirstLoad) {
                            binding.status.showLoading()
                        }
                    }
                }
            }
        })
        viewModel.start()
    }

    private fun submitResult (result: ResultBean?) {
        result?.let {
            adapter.update(it)
        }
    }
}