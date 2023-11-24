package com.sinug.projectimplementationnavapi.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sinug.projectimplementationnavapi.FollowerFollowingFragment
import com.sinug.projectimplementationnavapi.R
import com.sinug.projectimplementationnavapi.data.response.DetailUserResponse
import com.sinug.projectimplementationnavapi.data.retrofit.ApiConfig
import com.sinug.projectimplementationnavapi.databinding.ActivityDetailUserBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserActivity : AppCompatActivity(){
    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("nama")
        val tabPagerAdapter = TabPagerAdapter(this, username.toString())
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)

        viewPager.adapter = tabPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            val bundle = Bundle()
            bundle.putString(FollowerFollowingFragment.ARG_USERNAME, username)
            tab.text = when (position) {
                0 -> "Follower"
                1 -> "Following"
                else -> null
            }
        }.attach()


        loadDataProfile()
    }


    private fun loadDataProfile(){
        val pG = binding.progressBar
        pG.visibility = View.VISIBLE
        val data = intent.getStringExtra("nama")
        val client = ApiConfig.getApiService()
        val response = data?.let { client.getDetailUser(it) }
        response?.enqueue(object: Callback<DetailUserResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ){
                if(response.isSuccessful) {
                    pG.visibility = View.GONE
                    val requestOptions = RequestOptions().transform(CircleCrop())
                    val responseBody = response.body()
                    if (responseBody != null) {

                        Glide.with(binding.root.context)
                            .load(responseBody.avatarUrl)
                            .apply(requestOptions)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(binding.ivProfileDetail)

                        binding.tvNama.text = responseBody.name.toString()
                        binding.tvUsername.text = responseBody.login.toString()
                        binding.tvFollower.text = "Follower " + responseBody.followers.toString()
                        binding.tvFollowing.text = "Following " + responseBody.following.toString()
                    }
                }else{
                    Log.e(MainActivity.TAG,"onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable){
                Log.e(MainActivity.TAG, "onFailure: ${t.message}")
            }
        })
    }
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}

