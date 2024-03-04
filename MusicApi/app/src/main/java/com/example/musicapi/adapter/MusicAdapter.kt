package com.example.musicapi.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.SeekBar
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapi.databinding.ItemLayoutBinding
import com.example.musicapi.model.Data
import com.example.musicapi.model.MyData
import com.squareup.picasso.Picasso

class MusicAdapter(var context: Context, var artistList:MutableList<Data>, ):RecyclerView.Adapter<MusicAdapter.MyViewHolder>() {

    private var mediaPlayer: MediaPlayer? = null
    private var currentPlayingPosition: Int = -1
    private var isSeekbarTracking: Boolean = false
    private val handler = Handler()


    class MyViewHolder(var binding:ItemLayoutBinding) :RecyclerView.ViewHolder(binding.root){
        val playButton: ImageView = binding.play
        val stopButton: ImageView = binding.stop
//        val seekBar: SeekBar = binding.seekbar
//        val progressBar: ProgressBar = binding.progressBar2

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding = ItemLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return artistList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder,position: Int) {
       var music = artistList[position]
        holder.binding.name.text = music.artist.name
        holder.binding.title.text = music.title
        Picasso.get().load(music.artist.picture).into(holder.binding.imageview)
        holder.binding.next.setOnClickListener {
            playNextSong()
        }

        holder.binding.pre.setOnClickListener {
            playPreviousSong()
        }

        // Handling play button click
        holder.binding.play.setOnClickListener {
//            mediaPlayer!!.start()
            handlePlayPause(position, music.preview, holder)
            togglePlaypause()
        }

        holder.binding.stop.setOnClickListener {
            stopMediaPlayer()
        }

        // Update UI based on current playback position
        if (position == currentPlayingPosition) {
            holder.playButton.visibility = View.GONE
            holder.stopButton.visibility = View.VISIBLE
        } else {
            holder.playButton.visibility = View.VISIBLE
            holder.stopButton.visibility = View.GONE

        }
        // Update progress bar
//        updateProgressBar(holder)
    }

    private fun playPreviousSong() {
        val previousPosition = if (currentPlayingPosition == 0) itemCount - 1 else currentPlayingPosition - 1
        if (previousPosition != currentPlayingPosition) {
            val previousMusic = artistList[previousPosition]
            stopMediaPlayer()
            initializeMediaPlayer(previousMusic.preview)
            startMediaPlayer()
            currentPlayingPosition = previousPosition
            notifyDataSetChanged()
        }
    }

    private fun playNextSong() {
        val nextPosition = (currentPlayingPosition + 1) % itemCount
        if (nextPosition != currentPlayingPosition) {
            val nextMusic = artistList[nextPosition]
            stopMediaPlayer()
            initializeMediaPlayer(nextMusic.preview)
            startMediaPlayer()
            currentPlayingPosition = nextPosition
            notifyDataSetChanged()
        }
    }


    private fun handlePlayPause(position: Int, songUrl: String, holder: MyViewHolder) {
        if (position == currentPlayingPosition) {
            if (mediaPlayer?.isPlaying == true) {
                pauseMediaPlayer()
            } else {
                startMediaPlayer()
            }
        } else {
            stopMediaPlayer()
            currentPlayingPosition = position
            initializeMediaPlayer(songUrl)
            startMediaPlayer()
        }
        notifyDataSetChanged()
    }
    private fun stopMediaPlayer() {
        mediaPlayer?.stop()
        mediaPlayer?.reset()
        mediaPlayer?.release()
        mediaPlayer = null
        currentPlayingPosition = -1
        notifyDataSetChanged()
    }

    private fun togglePlaypause(){
        if (mediaPlayer==null){

        }else{
            if (mediaPlayer!!.isPlaying) {
                pauseMediaPlayer()
            }else{
                startMediaPlayer()
            }
        }
    }


    private fun initializeMediaPlayer(songUrl: String) {
        releaseMediaPlayer()
        mediaPlayer= MediaPlayer()
        mediaPlayer?.setAudioAttributes(
            AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
        )
        mediaPlayer?.setDataSource(songUrl)
        mediaPlayer?.prepareAsync()

        mediaPlayer?.setOnPreparedListener {
            startMediaPlayer()
        }
    }

    private fun startMediaPlayer() {
        mediaPlayer?.start()
    }
    private fun pauseMediaPlayer() {
        if (currentPlayingPosition != -1){
            notifyItemChanged(currentPlayingPosition)
            currentPlayingPosition=-1
        }
    }
//    private fun updateProgressBar(holder: MyViewHolder) {
//        handler.post(object : Runnable {
//            override fun run() {
//                mediaPlayer?.let { player ->
//                    if (player.isPlaying) {
//                        val currentPosition = player.currentPosition
//                        holder.progressBar.progress = currentPosition
//                    }
//                }
//
//                // Update progress every second
//                handler.postDelayed(this, 200)
//            }
//        })
//    }

    private fun releaseMediaPlayer() {
//            mediaPlayer?.stop()
//            mediaPlayer?.reset()
        mediaPlayer?.release()
        mediaPlayer=null
    }
}

