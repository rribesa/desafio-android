package com.picpay.desafio.android.user.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.user.model.User
import com.picpay.desafio.android.user.ui.adapter.UserListAdapter
import com.picpay.desafio.android.user.viewmodel.UserViewModel
import com.picpay.desafio.android.user.viewmodel.status.UserStatus
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val adapter: UserListAdapter = UserListAdapter()
    private val viewModel: UserViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeStates()
        viewModel.fetch()
    }

    private fun observeStates() {
        viewModel.state.observe(this, Observer { state ->
            state?.let {
                when (it) {
                    is UserStatus.UserError -> showError()
                    is UserStatus.UserSuccess -> showUserList(it.users)
                }
            }
        })
    }

    private fun showUserList(users: List<User>) {
        binding.userListProgressBar.visibility = View.GONE
        adapter.users = users
    }

    private fun showError() {
        val message = getString(R.string.error)
        binding.userListProgressBar.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
            .show()
    }

    override fun onResume() {
        super.onResume()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.visibility = View.VISIBLE
    }
}