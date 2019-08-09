package asad.com.gankkotlin.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import asad.com.gankkotlin.R
import asad.com.gankkotlin.databinding.ItemGirlsListBinding
import asad.com.gankkotlin.http.bean.TitleBean
import asad.com.gankkotlin.utils.BindingUtils
import asad.com.gankkotlin.utils.StringUtils

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.adapter
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-08 19:15
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/
class GirlsAdapter : RecyclerView.Adapter<GirlsAdapter.ViewHolder>{

    private var mContext: Context;
    private var onItemClickListener: OnItemClick?= null

    private var mData: ArrayList<TitleBean> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemGirlsListBinding>(LayoutInflater.from(mContext), R.layout.item_girls_list,parent,false)
        binding.root.setOnClickListener {
            onItemClickListener?.let {
                it.onItemClick(binding.root.tag as TitleBean)
            }
        }

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var layoutParams = holder.binding.root.layoutParams
        layoutParams.height = getHeight(position)
        holder.bindData(mData[position])
        holder.binding.root.tag = mData[position]
    }

    private fun getHeight (position: Int): Int {
        return (position %3) * 100 +600
    }


    constructor(mContext: Context) : super(){
        this.mContext = mContext
    }

    fun update (data: ArrayList<TitleBean>){
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    fun add(data: ArrayList<TitleBean>) {
        var oldSize = mData.size
        mData.addAll(data)
        notifyItemRangeChanged(oldSize-1,data.size)

    }

    fun setOnItemClick(listener: OnItemClick){
        this.onItemClickListener = listener
    }


    interface OnItemClick{
        fun onItemClick(bean: TitleBean)
    }





    class ViewHolder : BaseRvHolder {

        constructor(binding: ItemGirlsListBinding): super(binding.root){
            this.binding = binding

        }

        fun bindData(titleBean: TitleBean) {
            (binding as ItemGirlsListBinding).run {
                this.time.text = "发布日期"+StringUtils.getGankReadTime(titleBean.createdAt)+ "\n 作者:" + titleBean.who
                BindingUtils.bindArticleImg(this.img,titleBean.url)
            }
        }

    }

}