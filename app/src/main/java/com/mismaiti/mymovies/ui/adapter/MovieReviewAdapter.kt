package com.mismaiti.mymovies.ui.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mismaiti.mymovies.R
import com.mismaiti.mymovies.api.dao.MovieReviewDao
import com.mismaiti.mymovies.databinding.ItemMovieReviewBinding
import com.mismaiti.mymovies.util.set

class MovieReviewAdapter(private val context:Context?, private val listMovieReview: List<MovieReviewDao>):
    RecyclerView.Adapter<MovieCommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCommentViewHolder {
        val binding = ItemMovieReviewBinding.inflate(LayoutInflater.from(context), parent, false)
        return MovieCommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieCommentViewHolder, position: Int) {
        holder.onBind(listMovieReview[position])
    }

    override fun getItemCount(): Int = listMovieReview.size
}

class MovieCommentViewHolder(private val binding: ItemMovieReviewBinding)
    : RecyclerView.ViewHolder(binding.root) {

        fun onBind(movieReviewDao: MovieReviewDao) {
            with(binding) {
                movieReviewDao.apply {
                    tvReviewAuthor.set(root.context.getString(
                        R.string.username, authorDetail?.username))

                    tvReviewContent.set(
                        Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT).toString())
                }
            }
        }
}