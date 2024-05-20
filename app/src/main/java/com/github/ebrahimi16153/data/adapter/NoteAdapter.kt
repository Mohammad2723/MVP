package com.github.ebrahimi16153.data.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.ebrahimi16153.R
import com.github.ebrahimi16153.data.model.NoteEntity
import com.github.ebrahimi16153.databinding.ItemNotesBinding
import com.github.ebrahimi16153.util.Constant
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NoteAdapter @Inject constructor(@ApplicationContext private val context: Context): RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private lateinit var binding: ItemNotesBinding
    private var movieList: List<NoteEntity> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(item = movieList[position])
        // for handel duplicated item
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = movieList.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ResourceAsColor")
        fun bindViews(item: NoteEntity) {
            binding.titleTxt.text = item.title
            binding.descTxt.text = item.description
            // category
            when(item.category){
                Constant.HOME -> binding.categoryImg.setImageResource(R.drawable.home)
                Constant.WORK -> binding.categoryImg.setImageResource(R.drawable.work)
                Constant.EDUCATION -> binding.categoryImg.setImageResource(R.drawable.education)
                Constant.HEALTH -> binding.categoryImg.setImageResource(R.drawable.healthcare)
            }
            // priority
            when(item.priority){
                Constant.HIGH -> binding.priorityColor.setBackgroundColor(ContextCompat.getColor(context,R.color.red))
                Constant.MEDIUM -> binding.priorityColor.setBackgroundColor(ContextCompat.getColor(context,R.color.green))
                Constant.LOW -> binding.priorityColor.setBackgroundColor(ContextCompat.getColor(context,R.color.aqua))
            }

            binding.root.setOnClickListener {

                onItemClickListener?.let {
                    it(item)
                }

            }
        }

    }


    // a differ class and function for dynamic adapter -> when data of adapter can change many times
    // whe must handel oldItems and new items, in fact we handel how adapter update new items

    fun setData(data:List<NoteEntity>){

        val movieListDiffer = MovieListDiffer(oldItems = movieList , newItems = data)
        val diffUtil = DiffUtil.calculateDiff(movieListDiffer)
        movieList = data
        diffUtil.dispatchUpdatesTo(this)

    }




    class MovieListDiffer(private val oldItems: List<NoteEntity>, private val newItems: List<NoteEntity>) :
        DiffUtil.Callback() {


        override fun getOldListSize() = oldItems.size

        override fun getNewListSize() = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition] === newItems[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition] === newItems[newItemPosition]

    }



    // onClickListener
    private var onItemClickListener:((NoteEntity) -> Unit)? = null

    fun seOnItemClickListener(listener:(NoteEntity) -> Unit){
        onItemClickListener = listener
    }

}