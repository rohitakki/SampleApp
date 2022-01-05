package com.trending.app.modules.trending

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.kwabenaberko.newsapilib.models.Article
import com.trending.app.base.viewholder.BaseViewHolder
import com.trending.app.databinding.ItemNewsHeadingBinding
import com.trending.app.utils.DateTimeUtil

class ArticleAdapter(private val context: Context) : ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(DiffCallback()) {

    private lateinit var itemNewsBinding: ItemNewsHeadingBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding: ItemNewsHeadingBinding = ItemNewsHeadingBinding.inflate(layoutInflater, parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val binding: ItemNewsHeadingBinding = holder.itemNewsHeadingBinding
        val article: Article? = currentList[position]

        binding.articlePublishedAt = DateTimeUtil.getTimeAgo(article?.publishedAt!!)
        binding.article = article
        this.itemNewsBinding = binding

        Glide.with(context)
            .load(article.urlToImage)
            .centerCrop()
            .into(binding.articleImage)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    private class DiffCallback : DiffUtil.ItemCallback<Article>() {

        override fun areItemsTheSame(oldItem: Article, newItem: Article) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Article, newItem: Article) =
            oldItem.title == newItem.title
    }

    class ArticleViewHolder(val itemNewsHeadingBinding: ItemNewsHeadingBinding) : BaseViewHolder(itemNewsHeadingBinding.root)
}