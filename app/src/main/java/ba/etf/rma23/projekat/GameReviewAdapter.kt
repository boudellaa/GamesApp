package ba.etf.rma23.projekat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView



class GameReviewAdapter(private val reviews: List<UserImpression>) : RecyclerView.Adapter<GameReviewAdapter.ReviewViewHolder>() {


    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usernameTextView: TextView = itemView.findViewById(R.id.username_textview)
        val ratingBar: RatingBar = itemView.findViewById(R.id.rating_bar)
        val reviewTextView: TextView = itemView.findViewById(R.id.review_textview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val currentReview = reviews[position]
        holder.usernameTextView.text = "\nUsername: ${currentReview.username}"
        if(currentReview is UserRating) {
            holder.reviewTextView.isVisible = false
            holder.ratingBar.rating = currentReview.rating.toFloat()
        }else if(currentReview is UserReview) {
            holder.reviewTextView.text = "\nComment: ${currentReview.review}"
            holder.ratingBar.isVisible = false
        }
    }

    override fun getItemCount() = reviews.size


}
