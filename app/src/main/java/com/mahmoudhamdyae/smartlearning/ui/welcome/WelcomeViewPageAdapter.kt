package com.mahmoudhamdyae.smartlearning.ui.welcome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudhamdyae.smartlearning.R
import com.mahmoudhamdyae.smartlearning.databinding.OnboardingItemBinding
import com.mahmoudhamdyae.smartlearning.ui.welcome.WelcomeFragment.Companion.MAX_STEP

class WelcomeViewPageAdapter : RecyclerView.Adapter<PagerVH2>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH2 {
        return PagerVH2(
            OnboardingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = MAX_STEP

    override fun onBindViewHolder(holder: PagerVH2, position: Int) = holder.itemView.run {
        with(holder) {
            if (position == 0) {
                bindingDesign.introTitle.text = context.getString(R.string.title1)
                bindingDesign.introDescription.text = context.getString(R.string.description1)
                bindingDesign.introImage.setImageResource(R.drawable.school)
            }
            if (position == 1) {
                bindingDesign.introTitle.text = context.getString(R.string.title2)
                bindingDesign.introDescription.text = context.getString(R.string.description2)
                bindingDesign.introImage.setImageResource(R.drawable.school)
            }
            if (position == 2) {
                bindingDesign.introTitle.text = context.getString(R.string.title3)
                bindingDesign.introDescription.text = context.getString(R.string.description3)
                bindingDesign.introImage.setImageResource(R.drawable.school)
            }
        }
    }
}

class PagerVH2 (val bindingDesign: OnboardingItemBinding) : RecyclerView.ViewHolder(bindingDesign.root)