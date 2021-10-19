package com.movie.app.modules.trending

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movie.app.R
import com.movie.app.base.viewholder.BaseViewHolder
import com.movie.app.databinding.ItemRepositoryBinding
import com.movie.app.model.repositories.Repository

class TrendingRepositoryAdapter(private val context: Context) : RecyclerView.Adapter<BaseViewHolder>() {

    private var repositoryList: ArrayList<Repository?>? = arrayListOf()
    lateinit var itemRepositoryBinding: ItemRepositoryBinding

    fun addRepositories(repositories: List<Repository?>?) {
        repositoryList?.addAll(repositories!!)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding: ItemRepositoryBinding = ItemRepositoryBinding.inflate(layoutInflater, parent, false)
        return TrendingRepositoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val trendingRepositoryViewHolder = holder as TrendingRepositoryViewHolder
        val binding: ItemRepositoryBinding = trendingRepositoryViewHolder.itemRepositoryBinding
        val repository: Repository? = repositoryList?.get(position)
        binding.repository = repository
        this.itemRepositoryBinding = binding

        if (repository?.languageColor != null) {
            binding.languageColor.setImageDrawable(ColorDrawable(repository.languageColor.toColorInt()))
        }
        binding.languageText.text = repository?.language
        Glide.with(context)
            .load(repository?.builtBy?.get(0)?.avatar)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(binding.profileImage)

    }

    override fun getItemCount(): Int {
        return repositoryList?.size!!
    }

    class TrendingRepositoryViewHolder(val itemRepositoryBinding: ItemRepositoryBinding) : BaseViewHolder(itemRepositoryBinding.root)
}