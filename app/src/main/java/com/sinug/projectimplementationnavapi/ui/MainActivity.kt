package com.sinug.projectimplementationnavapi.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sinug.projectimplementationnavapi.R
import com.sinug.projectimplementationnavapi.data.response.GithubResponse
import com.sinug.projectimplementationnavapi.data.response.ItemsItem
import com.sinug.projectimplementationnavapi.data.retrofit.ApiConfig
import com.sinug.projectimplementationnavapi.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var searchQuery: String = ""
    companion object{
        internal val TAG = "MainActivity"
        //private const val USERNAME = "sinugrepo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.hide()

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener {textView,actionId, event ->
            searchBar.text = searchView.text
            searchQuery = searchBar.text.toString()
            searchView.hide()
            findUsernameGithub(searchQuery)
            //Toast.makeText(this@MainActivity, searchView.text, Toast.LENGTH_SHORT).show()
            false

            }
        }
        //findUsernameGithub()
        val layoutManager = LinearLayoutManager(this)
        binding.rvUsername.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this,layoutManager.orientation)
        binding.rvUsername.addItemDecoration(itemDecoration)

    }

    private fun findUsernameGithub(username: String){
        showLoading(true)
        searchQuery = username
        val usernameList = generateRandomUsernames()
        val client = ApiConfig.getApiService()
        if (searchQuery.isEmpty()){
        for (username in usernameList) {
            val response = client.getProfile(username)
            response.enqueue(object: Callback<GithubResponse> {
                override fun onResponse(
                    call: Call<GithubResponse>,
                    response: Response<GithubResponse>
                ){
                    showLoading(false)
                    if(response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            setUsernameData(responseBody.items)
                        }
                    }else{
                        Log.e(TAG,"onFailure: ${response.message()}")
                    }
                }
                override fun onFailure(call: Call<GithubResponse>, t: Throwable){
                    showLoading(false)
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
        }
        }else{
                val response = client.getProfile(searchQuery)
                response.enqueue(object: Callback<GithubResponse> {
                    override fun onResponse(
                        call: Call<GithubResponse>,
                        response: Response<GithubResponse>
                    ){
                        showLoading(false)
                        if(response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                setUsernameData(responseBody.items)
                            }
                        }else{
                            Log.e(TAG,"onFailure: ${response.message()}")
                        }
                    }
                    override fun onFailure(call: Call<GithubResponse>, t: Throwable){
                        showLoading(false)
                        Log.e(TAG, "onFailure: ${t.message}")
                    }
                })
            }
        }
    @SuppressLint("NotifyDataSetChanged")
    private fun setUsernameData(username: List<ItemsItem?>?) {
        val adapter = UsernameAdapter(object : UsernameAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                val intentToDetail = Intent(this@MainActivity, DetailUserActivity::class.java)
                intentToDetail.putExtra("nama", data.login)
                startActivity(intentToDetail)
            }
        })
        adapter.notifyDataSetChanged()
        adapter.submitList(username)
        binding.rvUsername.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun generateRandomUsernames(): List<String> {

        val random = Random()
        val usernameList = mutableListOf<String>()

        for (i in 1..2) {
            val randomChar = ('A'.toInt() + random.nextInt(26)).toChar()
            val username = "rizki$randomChar"
            usernameList.add(username)
        }

        return usernameList
    }
}