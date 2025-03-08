package com.example.cinecity.adapters

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinecity.PostProgramActivity
import com.example.cinecity.databinding.ProgramItemBinding
import com.example.cinecity.models.Program
import com.example.cinecity.utilities.Constants
import com.example.cinecity.utilities.ImageLoader
import kotlin.math.max

class ProgramAdapter(private var programs: List<Program>) :
    RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewHolder {
        val binding = ProgramItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProgramViewHolder(binding)
    }

    override fun getItemCount(): Int = programs.size

    fun getItem(position: Int) = programs[position]

    override fun onBindViewHolder(holder: ProgramViewHolder, position: Int) {
        with(holder) {
            with(getItem(position)) {
                // Basic info
                binding.programLBLTitle.text = name
                //api doesn't pass a duration field
                //binding.programLBLDuration.text = TimeFormatter.formatTime(length)
                binding.programLBLOverview.text = overview
                binding.programRBRating.rating = rating / 2
                ImageLoader.getInstance().loadImage(poster, binding.programIMGPoster)
                binding.programLBLReleaseDate.text = release_date


                binding.movieIMGAdd.setOnClickListener {
                    val context = holder.itemView.context
                    val bundle = Bundle().apply {
                        putString("poster", poster)
                        putString("name", name)
                        putInt("length", length)
                        putString("overview", overview)
                        putString("release date",release_date)
                    }

                    val intent = Intent(context, PostProgramActivity::class.java).apply {
                        putExtras(bundle)
                    }
                    context.startActivity(intent)
                }


                // Expand/Collapse just for "overview" now
                binding.programCVData.setOnClickListener {
                    val animatorSet = ArrayList<ObjectAnimator>()

                    if (isCollapsed) {
                        // Expand the overview
                        animatorSet.add(
                            ObjectAnimator.ofInt(
                                binding.programLBLOverview,
                                "maxLines",
                                binding.programLBLOverview.lineCount
                            ).setDuration(
                                (max((binding.programLBLOverview.lineCount -
                                        Constants.Data.OVERVIEW_MIN_LINES).toDouble(), 0.0) * 50L).toLong()
                            )
                        )
                    } else {
                        // Collapse the overview
                        animatorSet.add(
                            ObjectAnimator.ofInt(
                                binding.programLBLOverview,
                                "maxLines",
                                Constants.Data.OVERVIEW_MIN_LINES
                            ).setDuration(
                                (max((binding.programLBLOverview.lineCount -
                                        Constants.Data.OVERVIEW_MIN_LINES).toDouble(), 0.0) * 50L).toLong()
                            )
                        )
                    }

                    toggleCollapse()
                    animatorSet.forEach { it.start() }
                }
            }
        }
    }

    // Updates the list of programs and refreshes the adapter
    fun updatePrograms(newPrograms: List<Program>) {
        programs = newPrograms
        notifyDataSetChanged()
    }

    inner class ProgramViewHolder(val binding: ProgramItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}
