package asad.com.gankkotlin.adapter.mulitype

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import asad.com.gankkotlin.App
import asad.com.gankkotlin.R
import asad.com.gankkotlin.adapter.BaseRvHolder
import asad.com.gankkotlin.databinding.ItemIndexArticleBinding
import asad.com.gankkotlin.databinding.ItemIndexArticleEndBinding
import asad.com.gankkotlin.databinding.ItemIndexArticleTitleBinding
import asad.com.gankkotlin.databinding.ItemIndexGiftBinding
import asad.com.gankkotlin.http.bean.TitleBean
import asad.com.gankkotlin.http.response.gank.ResultBean
import asad.com.gankkotlin.utils.BindingUtils

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.adapter.mulitype
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-08 16:48
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/

class IndexAdapter : RecyclerView.Adapter<IndexAdapter.ViewHolder>{
    companion object{
        const val T_TITLE =1
        const val T_CONTENT=2
        const val T_END= 3
        const val T_IMAGE= 4
    }

    private var  mContext: Context

    private var  onItemClickListener:OnItemClick? = null

    private var bindIdMap: HashMap<Int,Int> = HashMap()

    private var mData: ArrayList<IndexItem> = ArrayList()
    constructor(mContext: Context) : super(){
        this.mContext = mContext
    }


    fun update (orgData: ResultBean) {
        fixData(orgData)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): IndexAdapter.ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mContext)

        when(p1){
            T_IMAGE -> {
                val binding = DataBindingUtil.inflate<ItemIndexGiftBinding>(layoutInflater, R.layout.item_index_gift, p0, false)
                bindIdMap.put(T_IMAGE, 1)
                binding.root.setOnClickListener {
                    onItemClickListener?.onItemClick(binding.root.tag as IndexItem)
                }
                return ViewHolder(binding)
            }
            T_TITLE -> {
                val binding = DataBindingUtil.inflate<ItemIndexArticleTitleBinding>(layoutInflater, R.layout.item_index_article_title, p0, false)
                bindIdMap.put(T_TITLE,1)
                binding.root.setOnClickListener {
                    onItemClickListener?.onItemClick(binding.root.tag as IndexItem)
                }
                return ViewHolder(binding)
            }
            T_END -> {
                val binding = DataBindingUtil.inflate<ItemIndexArticleEndBinding>(layoutInflater, R.layout.item_index_article_end, p0, false)
                bindIdMap.put(T_END,1)
                binding.root.setOnClickListener {
                    onItemClickListener?.onItemClick(binding.root.tag as IndexItem)
                }
                return ViewHolder(binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ItemIndexArticleBinding>(layoutInflater, R.layout.item_index_article, p0, false)
                bindIdMap.put(T_CONTENT, 1)
                binding.root.setOnClickListener {
                    onItemClickListener?.onItemClick(binding.root.tag as IndexItem)
                }
                return ViewHolder(binding)
            }
        }
    }
    fun registerOnItemClickListener(listener: OnItemClick){
        this.onItemClickListener = listener

    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(p0: IndexAdapter.ViewHolder, p1: Int) {
        var variableId  = bindIdMap[getItemViewType(p1)]
        var item = mData[p1].item

        if (item != null && variableId != null) {
            p0.bind(variableId,item)
            p0.binding.root.tag = mData[p1]
        }
    }

    override fun getItemViewType(position: Int): Int {
        return mData[position].getType()
    }

    private fun fixData(orgData: ResultBean){
        mData.clear()
        orgData.gift?.let {
            addGift(it)
        }
        orgData.android?.let {
            addItems(it)
        }
        orgData.iOS?.let {
            addItems(it)
        }
        orgData.app?.let {
            addItems(it)
        }

        orgData.recommond?.let {
            addItems(it)
        }
    }

    private fun addItems (item: List<TitleBean>){
        for (i in item.indices){
            when (i) {
                0 -> {
                    mData.add(IndexItem(T_TITLE,item[i]))
                }
                item.size -1 -> {
                    mData.add(IndexItem(T_END,item[i]))
                }
                else ->{
                    mData.add(IndexItem(T_CONTENT,item[i]))
                }
            }
        }
    }


    private fun addGift (items: List<TitleBean>) {
        for (item in items) {
            mData.add(IndexItem(T_IMAGE,item))
        }
    }


    class ViewHolder : BaseRvHolder {
        constructor(binding: ViewDataBinding) : super(binding.root){
            this.binding = binding
        }

        override fun bind(variable: Int, value: Any) {
            super.bind(variable, value)
            bindArticleImg(value as TitleBean)
        }

        fun bindArticleImg(titleBean: TitleBean){
            val images = titleBean.images
            var imgUrl = ""

            images?.let {
                if (it.isEmpty()){
                    imgUrl = it[0]
                }
            }

            when (binding) {
                is ItemIndexArticleTitleBinding -> {
                    BindingUtils.bindArticleImg((binding as ItemIndexArticleTitleBinding).ivImg, imgUrl)
                    setTitleIcon((binding as ItemIndexArticleTitleBinding).tvGroupTitle, titleBean.type)
                }
                is ItemIndexArticleBinding -> {
                    BindingUtils.bindArticleImg((binding as ItemIndexArticleBinding).ivImg, imgUrl)
                }
                is ItemIndexArticleEndBinding -> {
                    BindingUtils.bindArticleImg((binding as ItemIndexArticleEndBinding).ivImg, imgUrl)
                }
                is ItemIndexGiftBinding ->{
                    BindingUtils.bindGift((binding as ItemIndexGiftBinding).ivGift,titleBean.url)
                }
            }
        }

        private fun setTitleIcon(tvGroupTitle: TextView, type: Any){
            var drTag = R.mipmap.ic_default_group
            when(type) {
                "Android" -> {
                    drTag = R.mipmap.ic_android
                }
                "iOS" -> {
                    drTag = R.mipmap.ic_ios
                }
                "App" ->{
                    drTag = R.mipmap.ic_app
                }

            }

            var dr = App.INSTANCE.resources.getDrawable(drTag)

            dr.setBounds(0,0,dr.minimumWidth+10,dr.minimumHeight+10)
            tvGroupTitle.setCompoundDrawables(dr,null,null,null)
        }

    }

    interface OnItemClick {
        fun onItemClick(item: IndexItem);
    }


}