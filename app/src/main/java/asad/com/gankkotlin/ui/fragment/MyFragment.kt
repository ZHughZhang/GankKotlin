package asad.com.gankkotlin.ui.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import asad.com.gankkotlin.R
import asad.com.gankkotlin.adapter.ItemTouchHelperCallBack
import asad.com.gankkotlin.adapter.MeAdapter
import asad.com.gankkotlin.databinding.FragmentMeBinding
import asad.com.gankkotlin.http.bean.BeanType
import asad.com.gankkotlin.http.bean.PersonCenterBean
import asad.com.gankkotlin.ui.activity.WebActivity
import asad.com.gankkotlin.ui.base.BaseFragment
import com.blankj.utilcode.util.SPUtils
import java.util.*
import kotlin.collections.ArrayList

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

class MyFragment : BaseFragment<FragmentMeBinding> () {
    private lateinit var adapter: MeAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper

    private val mData: ArrayList<PersonCenterBean> by lazy {
        ArrayList<PersonCenterBean>().apply {
            this.add(PersonCenterBean("我的GitHUb").apply {
                iconId = R.mipmap.github
                url = "https://github.com/wintonBy"
            })
            this.add(PersonCenterBean("我的CSDN").apply {
                iconId = R.mipmap.csdn
                url = "https://blog.csdn.net/wenwen091100304"
            })
            this.add(PersonCenterBean("我的BLOG").apply {
                iconId = R.mipmap.blog
                url = "https://wintonby.github.io"
            })

        }
    }


    companion object {
        fun newInstance(params: Bundle?) :MyFragment {
            val frag = MyFragment()
            params?.apply {
                frag.arguments = this
            }

            return frag
        }
    }



    override fun getLayoutID(): Int {
        return R.layout.fragment_me
    }

    override fun initView() {
        super.initView()
        binding.rv.layoutManager = GridLayoutManager(context!!,3)
    }

    override fun initListener() {
        super.initListener()
    }

    override fun initData() {
        super.initData()
        adapter = MeAdapter(context!!)
        adapter.setOnItemClick(object: MeAdapter.OnItemClick{
            override fun onItemClick(item: PersonCenterBean) {
                when(item.type){
                    BeanType.URL ->{
                        WebActivity.start(context!!,item.url)

                    }
                    BeanType.ACTIVITY -> {
                        startActivity(item.intent)
                    }

                }
            }

        })

        adapter.setOnItemLongClick(object : MeAdapter.OnItemLongClick{
            override fun onItemLongClick(viewHolder: MeAdapter.ViewHolder): Boolean {
                itemTouchHelper.startDrag(viewHolder)
                return  true
            }

        })

        fixDataOrderByAcache()
        adapter.update(mData)
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallBack(adapter))
        itemTouchHelper.attachToRecyclerView(binding.rv)
        binding.rv.adapter = adapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveDataOrder()
    }

    override fun onDestroy() {
        super.onDestroy()
        saveDataOrder()
    }

    /**
     * 保存个人中心模块的顺序
     * */
    private fun saveDataOrder() {
        val data = adapter.getData()
        for (i in data.indices){
            SPUtils.getInstance().put(data[i].title,i)
        }
    }

    private fun fixDataOrderByAcache(){
        for (i in mData.indices){
            mData[i].order = SPUtils.getInstance().getInt(mData[i].title)
        }
        Collections.sort(mData)
    }

}