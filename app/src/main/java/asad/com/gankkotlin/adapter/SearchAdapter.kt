package asad.com.gankkotlin.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import asad.com.gankkotlin.R
import asad.com.gankkotlin.databinding.ItemGankSearchBinding
import asad.com.gankkotlin.http.bean.TitleBean
import asad.com.gankkotlin.utils.StringUtils

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.adapter
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-09 20:41
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/
class SearchAdapter : RecyclerView.Adapter<BaseRvHolder> {
    private var mData: ArrayList<TitleBean> = ArrayList()

    private var mContext: Context
    private var listener: OnItemCLickListener? = null

    constructor(mContext: Context) : super() {
        this.mContext = mContext
    }

    fun add(data: ArrayList<TitleBean>) {
        val oldSize = mData.size
        mData.addAll(data)
        notifyItemRangeInserted(oldSize,data.size)
    }

    fun update(data: ArrayList<TitleBean>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemCLickListener){
        this.listener  = listener
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BaseRvHolder {

        val binding:ItemGankSearchBinding = DataBindingUtil.inflate<ItemGankSearchBinding>(LayoutInflater.from(mContext), R.layout.item_gank_search,p0,false)
        val holder = BaseRvHolder(binding.root)
        holder.binding = binding
        holder.binding.root.setOnClickListener {
            listener?.let{
                it?.clickItem(holder.itemView.tag as TitleBean)
            }
        }
        return holder

    }


    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(p0: BaseRvHolder, p1: Int) {
        p0.bind(1,mData[p1])
        p0.itemView.tag = mData[p1]
        (p0.binding as ItemGankSearchBinding).time.text = StringUtils.getGankReadTime(mData[p1].publishedAt)
    }

    interface  OnItemCLickListener{
        fun clickItem(item: TitleBean)
    }


}