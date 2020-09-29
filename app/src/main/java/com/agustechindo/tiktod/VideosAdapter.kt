package com.agustechindo.tiktod

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.agustechindo.tiktod.databinding.ItemContainerVideoBinding
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class VideosAdapter(private val videoItems: List<VideoItem>) :
    RecyclerView.Adapter<VideosAdapter.VideoViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VideosAdapter.VideoViewHolder {
        val view: ItemContainerVideoBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_container_video,
                parent,
                false
            )

        return VideoViewHolder(view)
    }

    override fun getItemCount(): Int = videoItems.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VideosAdapter.VideoViewHolder, position: Int) {
        val url = videoItems[position].videoUrl
        val urlResource = holder.itemView.context.resources.getIdentifier(
            url,
            "raw",
            holder.itemView.context.packageName
        )

        val path = "android.resource://" + holder.itemView.context.packageName + "/" + urlResource
        holder.videoView.setVideoPath(path)

        holder.videoView.setOnPreparedListener { mp ->
            holder.progressBar.visibility = View.GONE
            mp.start()

            val videoRatio =
                mp.videoWidth / mp.videoHeight.toFloat()
            val screenRatio =
                holder.videoView.width / holder.videoView.height.toFloat()
            val scale = videoRatio / screenRatio

            if (scale >= 1f) {
                holder.videoView.scaleX = scale
            } else {
                holder.videoView.scaleY = 1f / scale
            }
        }

        holder.videoView.setOnCompletionListener { mp -> mp.start() }

        holder.username.text = "@" + videoItems[position].userName
        holder.caption.text = videoItems[position].caption
        holder.musicTitle.text = videoItems[position].musicTitle
        holder.musicTitle.isSelected = videoItems[position].musicBool.toBoolean()
        holder.likes.text = videoItems[position].likes
        holder.comments.text = videoItems[position].comments
        holder.forward.text = videoItems[position].forwards

        val userImg = videoItems[position].userImage
        val imgResources = holder.itemView.context.resources.getIdentifier(
            userImg,
            null,
            holder.itemView.context.packageName
        )

        Glide.with(holder.itemView.context).load(imgResources).into(holder.userImage)
        Glide.with(holder.itemView.context).load(imgResources).into(holder.musicImage)

        val isCheked = videoItems[position].isCheked.toBoolean()
        if (isCheked) {
            holder.checkedImage.visibility = View.VISIBLE
        } else {
            holder.checkedImage.visibility = View.GONE
        }
    }

    override fun onViewAttachedToWindow(holder: VideoViewHolder) {
        super.onViewAttachedToWindow(holder)
        val rotation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.rotate)
        rotation.interpolator = LinearInterpolator()
        holder.musicImage.startAnimation(rotation)
    }

    inner class VideoViewHolder(binding: ItemContainerVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val videoView = binding.videoView
        val username = binding.usernameText
        val caption = binding.captionText
        val musicTitle = binding.musicTitle
        val likes = binding.likeText
        val comments = binding.commentText
        val forward = binding.forwardText
        val userImage: CircleImageView = binding.profileImage
        val musicImage: CircleImageView = binding.imageMusic
        val progressBar = binding.progressBar
        val checkedImage = binding.checkedImg
    }
}