package com.trending.app.modules.trending

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.trending.app.base.viewholder.BaseViewHolder
import com.trending.app.databinding.ItemRepositoryBinding
import com.trending.app.model.repositories.Repository
import com.trending.app.utils.Animations

class TrendingRepositoryAdapter(private val context: Context) : RecyclerView.Adapter<BaseViewHolder>() {

    private var repositoryList: ArrayList<Repository?>? = arrayListOf()
    private lateinit var itemRepositoryBinding: ItemRepositoryBinding

    fun addRepositories(repositories: List<Repository?>?) {
        if (repositoryList != null) {
            repositoryList?.clear()
        }
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
            .into(binding.profileImage)

        if (repository?.isExpanded!!) {
            binding.secondaryLayout.visibility = View.VISIBLE
        } else {
            binding.secondaryLayout.visibility = View.GONE
        }
        binding.mainLayout.setOnClickListener {
            val expanded = toggleLayout(!repository.isExpanded, binding.secondaryLayout)
            repository.isExpanded = expanded
        }
    }

    override fun getItemCount(): Int {
        return repositoryList?.size!!
    }

    private fun toggleLayout(isExpanded: Boolean, layoutExpand: LinearLayout): Boolean {
        if (isExpanded) {
            Animations.expand(layoutExpand)
        } else {
            Animations.collapse(layoutExpand)
        }
        return isExpanded
    }

    class TrendingRepositoryViewHolder(val itemRepositoryBinding: ItemRepositoryBinding) : BaseViewHolder(itemRepositoryBinding.root)
}