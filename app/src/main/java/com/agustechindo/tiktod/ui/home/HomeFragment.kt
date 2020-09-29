package com.agustechindo.tiktod.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.agustechindo.tiktod.R
import com.agustechindo.tiktod.VideoItem
import com.agustechindo.tiktod.VideosAdapter
import com.agustechindo.tiktod.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        val videoItems = ArrayList<VideoItem>()
        videoItems.addAll(getListVideoItems())

        binding.videosViewPager.adapter = VideosAdapter(videoItems)

        return binding.root
    }

    private fun getListVideoItems(): ArrayList<VideoItem> {
        val videoUrl = resources.getStringArray(R.array.data_videourl)
        val username = resources.getStringArray(R.array.data_username)
        val caption = resources.getStringArray(R.array.data_caption)
        val music = resources.getStringArray(R.array.data_music)
        val likes = resources.getStringArray(R.array.data_likes)
        val comments = resources.getStringArray(R.array.data_comments)
        val forward = resources.getStringArray(R.array.data_forward)
        val avatar = resources.getStringArray(R.array.data_avatar)
        val musicImg = resources.getStringArray(R.array.data_music)
        val musicBool = resources.getStringArray(R.array.data_music_bool)
        val isChecked = resources.getStringArray(R.array.data_isCheked)

        val listVideo = ArrayList<VideoItem>()
        for (position in username.indices) {
            val videoItem = VideoItem(
                videoUrl[position],
                username[position],
                caption[position],
                music[position],
                likes[position],
                comments[position],
                forward[position],
                avatar[position],
                musicImg[position],
                musicBool[position],
                isChecked[position]
            )
            listVideo.add(videoItem)
        }
        return  listVideo
    }
}
