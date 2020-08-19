package com.picpay.desafio.android.user.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ListItemUserBinding
import com.picpay.desafio.android.user.model.User
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class UserListItemViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    private val binding = ListItemUserBinding.bind(itemView)
    fun bind(user: User) {
        binding.name.text = user.name
        binding.username.text = user.username
        binding.progressBar.visibility = View.VISIBLE
        Picasso.get()
            .load(user.img)
            .error(R.drawable.ic_round_account_circle)
            .into(binding.picture, object : Callback {
                override fun onSuccess() {
                    binding.progressBar.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    binding.progressBar.visibility = View.GONE
                }
            })
    }
}