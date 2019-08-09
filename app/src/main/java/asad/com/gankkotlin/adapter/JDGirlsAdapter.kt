package asad.com.gankkotlin.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import asad.com.gankkotlin.R
import asad.com.gankkotlin.databinding.ItemJdGirlBinding
import asad.com.gankkotlin.http.response.Comment
import asad.com.gankkotlin.utils.BindingUtils

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.adapter
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-09 10:29
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/
class JDGirlsAdapter : RecyclerView.Adapter<JDGirlsAdapter.ViewHolder> {


    private val mContext: Context
    private var onItemClickLinstener:OnItemClick? = null
    private var mData: ArrayList<Comment>

    constructor(mContext: Context): super(){
        this.mContext = mContext
        mData = ArrayList()
    }

    fun setOnItemClickListener(listener: OnItemClick){
        this.onItemClickLinstener = listener
    }

    fun update(data: ArrayList<Comment>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    fun  add(data: ArrayList<Comment>){
        var oldSize = mData.size
        mData.addAll(data)
        notifyItemRangeChanged(oldSize,data.size)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(mData[position])
        holder.itemView.tag = mData[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, positon: Int): ViewHolder {

        val binding = DataBindingUtil.inflate<ItemJdGirlBinding>(LayoutInflater.from(mContext), R.layout.item_jd_girl,parent,false)
        val holder = ViewHolder(binding)
        holder.itemView.setOnClickListener {
            onItemClickLinstener?.let {
                it.onItemClick(holder.itemView.tag as Comment)
            }
        }

        return holder

    }



    class ViewHolder : BaseRvHolder {
        constructor(binding: ViewDataBinding) :super(binding.root) {
            this.binding = binding
        }

        fun  bindData(item: Comment){
            (binding as ItemJdGirlBinding).apply {
                this.author.text = "作者: ${item.comment_author}"
                this.picNum.text = "含${item.pics.size} 图"
                BindingUtils.bindArticleImg(this.img,item.pics[0])
            }
        }
    }


    interface  OnItemClick{
        fun onItemClick(item:Comment)
    }
}