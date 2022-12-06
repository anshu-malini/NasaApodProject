package com.am.gsproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.am.gsproject.R
import com.am.gsproject.data.db.entities.ApodEntity
import com.am.gsproject.databinding.ItemHomeFragBinding
import com.am.gsproject.utils.MEDIA_TYPE_VIDEO
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import javax.inject.Inject

class HomeFragmentAdapter @Inject constructor(
    private val mContext: Context
) : RecyclerView.Adapter<HomeFragmentAdapter.MainViewHolder>() {
    var itemsList = mutableListOf<ApodEntity>()
    var onItemClick: ((String?, Int) -> Unit)? = null
    var onItemFavClick: ((String, Long) -> Unit)? = null


    fun setItemList(aList: MutableList<ApodEntity>) {
        this.itemsList = aList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHomeFragBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = itemsList[position]
        holder.binding.tvTitle.text = item.title
        holder.binding.tvDate.text = item.date
        holder.binding.tvDesc.text = item.explanation
        holder.binding.ivFav.apply {
            setColorFilter(mContext.getColor(R.color.home_item_fav_tint))
            if (item.isFav=="Y")
                this.setBackgroundResource(R.drawable.ic_fav_y)
            else
                this.setBackgroundResource(R.drawable.ic_fav_n)
        }
        var urlString = ""
        if (item.mediaType == MEDIA_TYPE_VIDEO) {
            holder.binding.imvVideoYT.visibility = View.VISIBLE
        } else {
            holder.binding.imvVideoYT.visibility = View.GONE
        }
        holder.binding.imvVideoYT.apply {
            visibility = if (item.mediaType == MEDIA_TYPE_VIDEO) View.VISIBLE else View.GONE
        }
        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)

        Glide.with(mContext).load(item.url).apply(requestOptions).into(holder.binding.imvApod)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    // class ViewHolder(val binding: ItemHomeFragBinding) : RecyclerView.ViewHolder(binding.root)

    inner class MainViewHolder(val binding: ItemHomeFragBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.imvVideoYT.setOnClickListener {
                onItemClick?.invoke(
                    itemsList[adapterPosition].url,
                    adapterPosition
                )
            }
            binding.ivFav.setOnClickListener {
                onItemFavClick?.invoke(
                    itemsList[adapterPosition].isFav,
                    itemsList[adapterPosition].apod_id
                )
            }
        }
    }
}