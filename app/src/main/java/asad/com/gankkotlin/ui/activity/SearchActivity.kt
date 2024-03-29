package asad.com.gankkotlin.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import asad.com.gankkotlin.R
import asad.com.gankkotlin.adapter.SearchAdapter
import asad.com.gankkotlin.databinding.ActivitySearchBinding
import asad.com.gankkotlin.http.bean.TitleBean
import asad.com.gankkotlin.repository.Resource
import asad.com.gankkotlin.repository.entitiy.SearchKey
import asad.com.gankkotlin.ui.base.BaseActivity
import asad.com.gankkotlin.viewmodel.SearchViewModel
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ToastUtils
import com.githang.statusbar.StatusBarCompat
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagView
import java.util.*

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
class SearchActivity : BaseActivity<ActivitySearchBinding> () {



    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: SearchAdapter
    private var pageIndex = 1
    private var hasNext = true

    companion object {
        fun start (context: Context) {
            var intent = Intent(context,SearchActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun iniListener() {
        super.iniListener()
        binding.etKey.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (it.isNotEmpty()){
                        binding.ibDelete.visibility = View.VISIBLE
                    }else{
                        binding.ibDelete.visibility = View.GONE
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                s?.let {
                    if (it.isNotEmpty()){
                        binding.ibDelete.visibility = View.VISIBLE
                    }else{
                        binding.ibDelete.visibility = View.GONE
                    }
                }

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        binding.ibDelete.setOnClickListener {
            val index = binding.etKey.selectionStart
            binding.etKey.editableText.delete(index -1,index)
        }

        binding.ibSearch.setOnClickListener {
            if (binding.etKey.text.trim().isNotEmpty()){
                KeyboardUtils.hideSoftInput(it)
                doSearch()
            }
        }
        binding.ibBack.setOnClickListener {
            finish()
        }
        binding.flHisKey.setOnTagClickListener { view, position, parent ->
            binding.etKey.text = Editable.Factory.getInstance().newEditable(((view as TagView).tagView as TextView).text)
            binding.etKey.setSelection(binding.etKey.text.length)
            KeyboardUtils.hideSoftInput(binding.etKey)
            doSearch()
            true
        }
        binding.tvClear.setOnClickListener {
            viewModel.clearKey()
        }
    }

    override fun initPreLayout(savedInstanceState: Bundle?) {
        super.initPreLayout(savedInstanceState)
        StatusBarCompat.setStatusBarColor(this@SearchActivity,Color.WHITE,true)
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        adapter = SearchAdapter(this)
        adapter.setOnItemClickListener(object: SearchAdapter.OnItemCLickListener{
            override fun clickItem(item: TitleBean) {
                val url = item?.url
                if(url != null) {
                    WebActivity.start(this@SearchActivity,url)

                }else{
                    ToastUtils.showLong("连接为空")
                }
            }

        })
        binding.rvResult.layoutManager = LinearLayoutManager(this)
        binding.rvResult.adapter = adapter
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        viewModel.start()
        initKey()
        viewModel.getListData().observe(this, Observer {
            when(it?.status){
                Resource.LOADING -> {
                    if (pageIndex ==1){
                        binding.status.showLoading()
                    }
                }
                Resource.SUCCESS -> {
                    binding.status.showContent()
                    it.data?.results.run {
                        if (pageIndex >1){
                            this?.let { it1 -> adapter.add(it1) }
                        }else{
                            this?.let { it1 -> adapter.update(it1) }
                        }
                        if (this?.size!! < 15) {
                            hasNext = false
                        }
                    }
                }

                Resource.ERROR -> {
                    if (pageIndex == 1) {
                        binding.status.showError()
                    }else{
                        ToastUtils.showShort("加载出错")
                    }
                }

            }
        })
    }

    override fun onStop() {
        super.onStop()
        viewModel.stop()
    }

    private fun initKey () {
        viewModel.getKeydata().observe(this, Observer {
            it?.let {
                binding.flHisKey.adapter = object: TagAdapter<SearchKey>(it){
                    override fun getView(parent: FlowLayout?, position: Int, t: SearchKey?): View {
                        val key = LayoutInflater.from(this@SearchActivity).inflate(R.layout.layout_search_key,null) as TextView
                        key.text = t?.key
                        key.setOnClickListener {
                            binding.etKey.text = Editable.Factory.getInstance().newEditable(key.text)
                        }

                        return key
                    }

                }
            }
        })
    }


    //保存搜索关键字

    private fun saveKey(key: String) {
        val searchKey = SearchKey(key,Date().time)
        viewModel.addKey(searchKey)
    }

    private fun doSearch (){
        pageIndex = 1
        viewModel.loadData(binding.etKey.text.toString(),pageIndex)
        binding.rlHis.visibility = View.GONE
        binding.status.visibility = View.VISIBLE
        saveKey(binding.etKey.text.toString())
    }

}