package com.example.cinecity.adapters

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinecity.R
import com.example.cinecity.databinding.ProgramItemBinding
import com.example.cinecity.models.Program
import com.example.cinecity.utilities.Constants
import com.example.cinecity.utilities.ImageLoader
import com.example.cinecity.utilities.TimeFormatter
import java.time.format.DateTimeFormatter
import kotlin.math.max

class ProgramAdapter(private val programs: List<Program>) :
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
                binding.programLBLTitle.text = name
                binding.programLBLReleaseDate.text =
                    releaseDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"))
                binding.programLBLDuration.text = TimeFormatter.formatTime(length)
                binding.programLBLGenres.text = genre.joinToString(", ")
                binding.programLBLActors.text = actors.joinToString(", ")
                binding.programLBLOverview.text = overview
                binding.programRBRating.rating = rating / 2
                ImageLoader.getInstance().loadImage(poster, binding.programIMGPoster)

                binding.programCVData.setOnClickListener {
                    val animatorSet = ArrayList<ObjectAnimator>()
                    if (isCollapsed) {
                        animatorSet.add(
                            ObjectAnimator.ofInt(
                                binding.programLBLActors,
                                "maxLines",
                                binding.programLBLActors.lineCount
                            ).setDuration(
                                (max((binding.programLBLActors.lineCount - Constants.Data.ACTORS_MIN_LINES).toDouble(), 0.0) * 50L).toLong()
                            )
                        )
                        animatorSet.add(
                            ObjectAnimator.ofInt(
                                binding.programLBLOverview,
                                "maxLines",
                                binding.programLBLOverview.lineCount
                            ).setDuration(
                                (max((binding.programLBLOverview.lineCount - Constants.Data.OVERVIEW_MIN_LINES).toDouble(), 0.0) * 50L).toLong()
                            )
                        )
                    } else {
                        animatorSet.add(
                            ObjectAnimator.ofInt(
                                binding.programLBLActors,
                                "maxLines",
                                Constants.Data.ACTORS_MIN_LINES
                            ).setDuration(
                                (max((binding.programLBLActors.lineCount - Constants.Data.ACTORS_MIN_LINES).toDouble(), 0.0) * 50L).toLong()
                            )
                        )
                        animatorSet.add(
                            ObjectAnimator.ofInt(
                                binding.programLBLOverview,
                                "maxLines",
                                Constants.Data.OVERVIEW_MIN_LINES
                            ).setDuration(
                                (max((binding.programLBLOverview.lineCount - Constants.Data.OVERVIEW_MIN_LINES).toDouble(), 0.0) * 50L).toLong()
                            )
                        )
                    }
                    toggleCollapse()
                    animatorSet.forEach { it.start() }
                }
            }
        }
    }

    inner class ProgramViewHolder(val binding: ProgramItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}
