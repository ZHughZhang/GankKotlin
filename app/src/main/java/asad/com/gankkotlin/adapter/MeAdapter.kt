package asad.com.gankkotlin.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import asad.com.gankkotlin.App
import asad.com.gankkotlin.R
import asad.com.gankkotlin.databinding.ItemJdGirlBinding
import asad.com.gankkotlin.databinding.ItemMeCenterBinding
import asad.com.gankkotlin.http.bean.PersonCenterBean
import asad.com.gankkotlin.http.response.Comment
import asad.com.gankkotlin.utils.BindingUtils
import java.util.*
import kotlin.collections.ArrayList

/**
 * @ProjectName: GankKotlin
 * @Package: asad.com.gankkotlin.adapter
 * @ClassName: zlq
 * @Autor: Asia
 * @CreateDate: 2019-07-09 11:04
 * @UpdateDate: (更新时间)
 * @UpdateUser: (更新人)
 * @UpdateRemark: (更新说明)
 * @Description: (java类作用描述)
 * @Version: $VERSION
 * @Desc
 **/
class MeAdapter : RecyclerView.Adapter<MeAdapter.ViewHolder> {


    private val mContext: Context
    private var mData: ArrayList<PersonCenterBean> = ArrayList()

    private var onItemClickListener: OnItemClick? = null

    private var onItemLongClickListener: OnItemLongClick? = null


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemMeCenterBinding>(
            LayoutInflater.from(mContext),
            R.layout.item_me_center, parent, false
        )
        binding.root.setOnClickListener {
            onItemClickListener?.let {
                it.onItemClick(binding.root.tag as PersonCenterBean)
            }
        }
        val holder: ViewHolder = ViewHolder(binding)

        binding.root.setOnLongClickListener {
            var result = false
            onItemLongClickListener?.let {
                it.onItemLongClick(holder)
            }
            result
        }


        return holder

    }
    fun setOnItemClick(listener: OnItemClick){
        this.onItemClickListener = listener
    }
    fun setOnItemLongClick(listene: OnItemLongClick){
        this.onItemLongClickListener = listene
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(mData[position])
        holder.binding.root.tag = mData[position]
    }


    constructor(context: Context) : super() {
        this.mContext = context
    }


    fun update(data: ArrayList<PersonCenterBean>) {

        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()

    }

    fun getData(): ArrayList<PersonCenterBean> {
        return mData
    }


    class ViewHolder : BaseRvHolder, ItemTouchListener {
        override fun onItemSelect() {

            (itemView as CardView).apply {
                this.setCardBackgroundColor(ContextCompat.getColor(App.INSTANCE, R.color.divider_line))
            }

        }

        override fun onItemClear() {
            (itemView as CardView).apply {
                this.setCardBackgroundColor(Color.TRANSPARENT)
            }
        }


        constructor(binding: ViewDataBinding) : super(binding.root) {
            this.binding = binding
        }

        fun bindData(item: PersonCenterBean) {
            (binding as ItemMeCenterBinding).let {
                it.tvTitle.text = item.title
                if (item.iconId != -1) {
                    it.ivIcon.setImageResource(item.iconId)
                }
            }
        }


    }


    interface OnItemClick {
        fun onItemClick(item: PersonCenterBean)
    }

    interface OnItemLongClick {
        fun onItemLongClick(viewHolder: ViewHolder): Boolean
    }

}

interface ItemTouchListener {
    fun onItemSelect()
    fun onItemClear()
}

class ItemTouchHelperCallBack : ItemTouchHelper.Callback {
    private var mAdapter: MeAdapter

    constructor(meAdapter: MeAdapter) : super() {
        this.mAdapter = meAdapter
    }

    override fun getMovementFlags(p0: RecyclerView, p1: RecyclerView.ViewHolder): Int {
        return makeMovementFlags(
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.DOWN or ItemTouchHelper.UP,
            0
        )
    }

    override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
        var oldPosition = p1.adapterPosition
        var newPosition: Int = p2.adapterPosition
        Collections.swap(mAdapter.getData(), oldPosition, newPosition)
        mAdapter.notifyItemMoved(oldPosition, newPosition)
        return true
    }

    override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {

    }


    override fun isLongPressDragEnabled(): Boolean {
        return super.isLongPressDragEnabled()
    }


    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        viewHolder?.let {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                if (viewHolder is ItemTouchListener) {
                    viewHolder.onItemSelect()
                }


                super.onSelectedChanged(viewHolder, actionState)
            }
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        if (viewHolder is ItemTouchListener) {
            viewHolder.onItemClear()
        }
    }

}
