package com.example.myapplication

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerContactAdapter(
    private val context: Context,
    private val contactList: ArrayList<ContactModel>,
    private var onItemClick: ((Int) -> Unit)? = null // Callback for item click event
) : RecyclerView.Adapter<RecyclerContactAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.contact_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = contactList[position]

        // Bind data to views in the ViewHolder
        holder.bind(currentItem)

        // Animation for the root view in ViewHolder
        val rootView: View = holder.itemView.findViewById(R.id.rootView)

        // Create alpha animation from 0f to 1f with duration 400ms
        val alphaAnimator = ObjectAnimator.ofFloat(rootView, View.ALPHA, 0f, 1f).apply {
            duration = 400 // Duration in milliseconds
        }

        // Create translation animation from 500f to 0f along the x-axis with duration 400ms
        val translationAnimator =
            ObjectAnimator.ofFloat(rootView, View.TRANSLATION_X, 500f, 0f).apply {
                duration = 400 // Duration in milliseconds
            }

        // Create an animation set to play both animations together
        val animationSet = AnimatorSet().apply {
            playTogether(alphaAnimator, translationAnimator)
        }

        // Start the animation set
        animationSet.start()

        // Handle item clicks
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(position)
        }
    }

    // Setter for item click callback
    fun setOnItemClickListener(itemClickListener: (Int) -> Unit) {
        onItemClick = itemClickListener
    }

    fun getContactAtPosition(position: Int): ContactModel {
        return contactList[position]
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.contact_image)
        private val nameTextView: TextView = itemView.findViewById(R.id.contact_name)
        private val phoneTextView: TextView = itemView.findViewById(R.id.contact_phone)

        fun bind(contact: ContactModel) {
            // Bind data to views in the ViewHolder
            imageView.setImageResource(contact.image)
            nameTextView.text = contact.name
            phoneTextView.text = contact.phone
        }
    }

}
