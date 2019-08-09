package asad.com.gankkotlin.ui.fragment

import android.animation.ValueAnimator
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.TextView
import asad.com.gankkotlin.R
import asad.com.gankkotlin.adapter.IndexVPAdapter
import asad.com.gankkotlin.databinding.FragmentIndexBinding
import asad.com.gankkotlin.ui.activity.MainActivity
import asad.com.gankkotlin.ui.activity.ScanResultActivity
import asad.com.gankkotlin.ui.activity.SearchActivity
import asad.com.gankkotlin.ui.base.BaseFragment
import asad.com.gankkotlin.viewmodel.IndexViewModel
import com.zxing.activity.ScanActivity

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

class IndexFragment  : BaseFragment<FragmentIndexBinding>() {


    private lateinit var viewModel: IndexViewModel
    private lateinit var adapter:IndexVPAdapter



    private val fragments by lazy {

        ArrayList<Fragment>().apply {
            this.add(GirlsFragment.newInstance(Bundle().apply {
                this.putString(GirlsFragment.CATEGORY,"福利")
                this.putBoolean(GirlsFragment.SHOW_TITLE,false)
            }))
            this.add(TodayFragment.newInstance(null))
            this.add(GankListFragment.newInstance(Bundle().apply {
                this.putString(GankListFragment.CATEGORY,"Android")
            }))
            this.add(GankListFragment.newInstance(Bundle().apply {
                this.putString(GankListFragment.CATEGORY,"iOS") }))
            this.add(GankListFragment.newInstance(Bundle().apply {
                this.putString(GankListFragment.CATEGORY,"App")
            }))
        }
    }


    override fun initListener() {
        super.initListener()
        binding.ibScan.setOnClickListener{
            ScanActivity.start(activity,MainActivity.REQ_SCAN,true)
        }
        binding.tvReSearch.setOnClickListener {
            binding.tvReSearch.isClickable = false
            SearchActivity.start(context!!)
            binding.tvReSearch.isClickable = true
        }
    }

    override fun initData() {
        super.initData()
        viewModel = ViewModelProviders.of(this).get(IndexViewModel::class.java)
        adapter = IndexVPAdapter(fragmentManager,fragments)
        binding.vp.adapter  = adapter
        binding.vp.offscreenPageLimit = fragments.size
        binding.tabIndex.setupWithViewPager(binding.vp)
        initTap()
        viewModel.start()
    }

    private fun initTap (){
        binding.tabIndex.isTabIndicatorFullWidth
        binding.tabIndex.run {
            this.getTabAt(0)?.customView = getTab(0).apply { findViewById<TextView>(R.id.tab_title).text = "福利" }
            this.getTabAt(1)?.customView = getTab(1).apply { findViewById<TextView>(R.id.tab_title).text = "今日推荐" }
            this.getTabAt(2)?.customView = getTab(2).apply { findViewById<TextView>(R.id.tab_title).text = "Android" }
            this.getTabAt(3)?.customView = getTab(3).apply { findViewById<TextView>(R.id.tab_title).text = "iOS" }
            this.getTabAt(4)?.customView = getTab(4).apply { findViewById<TextView>(R.id.tab_title).text = "App" }
        }

        binding.tabIndex.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.customView!!.findViewById<TextView>(R.id.tab_title).run {
                    setTextColor(ContextCompat.getColor(context!!,android.R.color.black))
                    ValueAnimator.ofFloat(18F,14F).apply {
                        duration = 200
                        addUpdateListener {
                            setTextSize(TypedValue.COMPLEX_UNIT_DIP,(it.animatedValue as Float))
                        }
                    }.start()
                }

            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.customView!!.findViewById<TextView>(R.id.tab_title).run {
                    this.setTextColor(ContextCompat.getColor(activity!!,R.color.tab_selected_text_color))
                    ValueAnimator.ofFloat(18F,14F).apply {
                        duration = 200
                        addUpdateListener {
                            setTextSize(TypedValue.COMPLEX_UNIT_DIP,(it.animatedValue as Float))
                        }
                    }.start()
                }
            }

        })
        binding.tabIndex.getTabAt(1)!!.select()
    }

    companion object{
        fun newInstance(param: Bundle?): IndexFragment{

            var frag = IndexFragment()
            param?.apply {
                frag.arguments = this
            }
            return frag
        }
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_index
    }


    private fun getTab (post: Int) = LayoutInflater.from(context!!).inflate(R.layout.layout_tab,null)


}