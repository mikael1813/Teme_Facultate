package com.example.project_kotlin_ma

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.examen25inventar.R

class CustomAdapter(private val mList: ArrayList<ItemsViewModel>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener
    private lateinit var mListener2: onItemLongClickListener

    private var IDList = ArrayList<Int>()

    fun append(id: Int) {
        IDList.add(id)
    }

    fun remove(id: Int) {
        IDList.remove(id)
    }

    fun getIDByPoz(poz: Int): Int {
        if (poz > IDList.size) {
            return -1
        }
        return IDList.get(poz)
    }

    fun setListID(list: ArrayList<Int>) {
        IDList = list
    }

    interface onItemClickListener {

        fun onItemClick(position: Int)

    }

    interface onItemLongClickListener {

        fun onItemLongClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {

        mListener = listener

    }

    fun setOnItemLongClickListener(listener: onItemLongClickListener) {
        mListener2 = listener
    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view, mListener, mListener2)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        holder.imageView.setImageResource(ItemsViewModel.image)

        // sets the text to the textview from our itemHolder class
        holder.textView.text = ItemsViewModel.text

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    fun deleteItem(position: Int) {
        mList.removeAt(position)
    }

    fun addItem(item: ItemsViewModel) {
        mList.add(item)
    }

    fun updateItem(pos: Int, item: ItemsViewModel) {
        mList.set(pos, item)
    }

    // Holds the views for adding it to image and text
    class ViewHolder(
        ItemView: View,
        listener: onItemClickListener,
        listener2: onItemLongClickListener
    ) :
        RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.textView)

        init {

            ItemView.setOnClickListener {

                listener.onItemClick(adapterPosition)

            }

            ItemView.setOnLongClickListener {

                listener2.onItemLongClick(adapterPosition)

                return@setOnLongClickListener true

            }

        }
    }
}
