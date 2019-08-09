package asad.com.gankkotlin.adapter.mulitype

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import asad.com.gankkotlin.R
import asad.com.gankkotlin.adapter.BaseRvHolder
import asad.com.gankkotlin.databinding.ItemGankListManyIamgeBinding
import asad.com.gankkotlin.databinding.ItemGankListNoImageBinding
import asad.com.gankkotlin.databinding.ItemGankListOneImageBinding
import asad.com.gankkotlin.http.bean.TitleBean
import asad.com.gankkotlin.utils.BindingUtils
import asad.com.gankkotlin.utils.StringUtils
import kotlinx.android.synthetic.main.item_gank_search.view.*

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
class GankListAdapter: RecyclerView.Adapter<GankListAdapter.ViewHolder>{
    private var mContext: Context
    private var mData: ArrayList<IndexItem>
    private var onItemClickListener: OnItemClick? = null

    companion object{
        const val T_NO_IMG= 1
        const val T_ONE_IMG= 2
        const val T_MANY_IMG = 3
    }

    constructor(mContext: Context):super(){
        this.mContext = mContext
        mData = ArrayList()
    }

    fun  update(data: ArrayList<TitleBean>){
        this.mData.clear()
        this.mData.addAll(fixData(data))
        notifyDataSetChanged()
    }

    fun add(data: ArrayList<TitleBean>) {
        var oldSize = mData.size
        this.mData.addAll(fixData(data))
        notifyItemChanged(oldSize -1, data.size)
    }

    fun setItemClickListener(listener: OnItemClick){
        this.onItemClickListener = listener
    }


    private fun fixData(data: ArrayList<TitleBean>): ArrayList<IndexItem>{
        val result = ArrayList<IndexItem>()
        for (bean in data){
            if (bean.images == null || bean.images.isEmpty()) {
                result.add(IndexItem(T_NO_IMG,bean))
            }else if (bean.images.size == 1) {
                result.add(IndexItem(T_ONE_IMG,bean))
            }else{
                result.add(IndexItem(T_MANY_IMG,bean))
            }
        }
        return result
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): GankListAdapter.ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mContext)
        when(p1){
            T_ONE_IMG ->{
                val binding = DataBindingUtil.inflate<ItemGankListOneImageBinding>(layoutInflater, R.layout.item_gank_list_one_image,p0,false)
                binding.root.setOnClickListener{
                    onItemClickListener?.onItemClick(binding.root.tag as IndexItem)
                }
                return ViewHolder(binding)
            }
            T_MANY_IMG -> {
                val binding = DataBindingUtil.inflate<ItemGankListManyIamgeBinding>(layoutInflater,R.layout.item_gank_list_many_iamge,p0,false)
                binding.root.setOnClickListener {
                    onItemClickListener?.onItemClick(binding.root.tag as IndexItem)
                }

                return ViewHolder(binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ItemGankListNoImageBinding>(layoutInflater,R.layout.item_gank_list_no_image,p0,false)
                binding.root.setOnClickListener {
                    onItemClickListener?.onItemClick(binding.root.tag as IndexItem)
                }

                return ViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int = mData.size

    override fun getItemViewType(position: Int): Int  = mData[position].getType()

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        holder.bindData(position,mData[position].item!!)
        holder.binding.root.tag = mData[position]
    }


    class ViewHolder : BaseRvHolder {
        constructor(binding: ViewDataBinding): super(binding.root){
            this.binding = binding
        }

        fun bindData(position: Int,item: TitleBean) {

            when(binding) {
                is ItemGankListNoImageBinding -> {
                    (binding as ItemGankListNoImageBinding).let {
                        it.title.text = item.desc
                        it.tvAuthor.text = item.who
                        it.tvPupTime.text = StringUtils.getGankReadTime(item.publishedAt)
                    }
                }
                is ItemGankListOneImageBinding -> {
                    (binding as ItemGankListOneImageBinding).let {
                        it.tvTitle.text = item.desc
                        it.tvAuthor.text = item.who
                        it.tvPupTime.text = StringUtils.getGankReadTime(item.publishedAt)
                        BindingUtils.bindArticleImg(it.ivImg,item.images[0])
                    }
                }

                is ItemGankListManyIamgeBinding -> {
                    (binding as ItemGankListManyIamgeBinding).let {
                        it.title.text = item.desc
                        it.tvAuthor.text = item.who
                        it.tvPubTime.text = StringUtils.getGankReadTime(item.publishedAt)
                        BindingUtils.bindArticleImg(it.img1,item.images[0])
                        BindingUtils.bindArticleImg(it.img2,item.images[1])
                        if(item.images.size >= 3){
                            it.img3.visibility = View.VISIBLE
                            BindingUtils.bindArticleImg(it.img3,item.images[2])
                        }else{
                            it.img3.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }


    interface OnItemClick {
        fun onItemClick(item: IndexItem)
    }

}