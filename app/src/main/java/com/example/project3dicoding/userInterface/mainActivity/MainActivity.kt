package com.example.project3dicoding.userInterface.mainActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project3dicoding.userInterface.adapter.FavoriteUserAdapter
import com.example.project3dicoding.*
import com.example.project3dicoding.databinding.ActivityMainBinding
import com.example.project3dicoding.userInterface.adapter.GithubUserAdapter
import com.example.project3dicoding.userInterface.adapter.ListGithubMain
import com.example.project3dicoding.userInterface.detailActivity.DetailUserActivity
import com.example.project3dicoding.userInterface.favoriteActivity.FavoriteActivity
import com.example.project3dicoding.userInterface.setting.SettingActivity

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoading(false)

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        mainViewModel.responseBody.observe(this) { user ->
            setGithubData(user)
        }

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }

        mainViewModel.isError.observe(this){
            if(it)
                showError()
        }

        searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.findUser(query)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun setGithubData(item: List<ItemsItem>) {
        val data = ArrayList<ListGithubMain>()
        for (i in item){
            data.add(ListGithubMain(i.id, i.login, i.url, i.avatarUrl))
        }
        binding.rvListGithub.layoutManager = LinearLayoutManager(this)
        val listGithubAdapter = GithubUserAdapter(data)
        binding.rvListGithub.adapter = listGithubAdapter

        listGithubAdapter.setOnItemClickCallback(object : GithubUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: String) {
                val moveWithDataIntent = Intent(this@MainActivity, DetailUserActivity::class.java)
                moveWithDataIntent.putExtra("data", data)
                startActivity(moveWithDataIntent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(){
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_favorite -> {
                val moveIntent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.action_setting -> {
                val moveIntent = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(moveIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

