package com.hardi.cakelist.ui.cakelist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hardi.cakelist.R
import com.hardi.cakelist.data.model.Cake
import com.hardi.cakelist.databinding.CakeItemLayoutBinding

class CakeListAdapter(
    private val cakeList: ArrayList<Cake>
) : RecyclerView.Adapter<CakeListAdapter.DataViewHolder>() {
    class DataViewHolder(private val binding: CakeItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cake: Cake) {
            binding.textCakeTitle.text = cake.title
            Glide.with(binding.cakeImage.context)
                .load(cake.image)
                .error(R.drawable.ic_launcher_background)
                .into(binding.cakeImage)
            itemView.setOnClickListener {
                val builder: AlertDialog.Builder = AlertDialog.Builder(itemView.context)
                builder
                    .setMessage(cake.desc)
                    .setTitle(cake.title)

                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = DataViewHolder(
        CakeItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )


    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        return holder.bind(cakeList[position])
    }

    override fun getItemCount(): Int {
        return cakeList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addData(list: List<Cake>) {
        cakeList.clear()
        cakeList.addAll(list)
        this.notifyDataSetChanged()
    }
}