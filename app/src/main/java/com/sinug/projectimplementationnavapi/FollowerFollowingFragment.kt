package com.sinug.projectimplementationnavapi

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sinug.projectimplementationnavapi.data.response.DetailUserResponse
import com.sinug.projectimplementationnavapi.data.retrofit.ApiConfig
import com.sinug.projectimplementationnavapi.databinding.FragmentFollowerFollowingBinding
import com.sinug.projectimplementationnavapi.ui.FollowerAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowerFollowingFragment : Fragment() {

    private lateinit var binding: FragmentFollowerFollowingBinding
    private lateinit var adapter: FollowerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowerFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }



    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFolWing.layoutManager = layoutManager

        adapter = FollowerAdapter()
        binding.rvFolWing.adapter = adapter
        val username = null


        val usernameGit = arguments?.getString(ARG_USERNAME, username)
        val tabPosition = arguments?.getInt(ARG_SECTION_NUMBER, 0) ?: 0
        when (tabPosition) {
            0 -> usernameGit?.let { loadGitHubFollower(it) }
            1 -> usernameGit?.let { loadGitHubFollowing(it) }
            else -> throw IllegalArgumentException("Invalid tab position: $tabPosition")
        }
    }
    private fun loadGitHubFollower(username: String) {
        val pG = binding.progressBar
        pG.visibility = View.VISIBLE
        val client = ApiConfig.getApiService()
        val responseCall = client.getFollower(username)
        responseCall.enqueue(object : Callback<List<DetailUserResponse>> {
            override fun onResponse(
                call: Call<List<DetailUserResponse>>,
                response: Response<List<DetailUserResponse>>
            ) {
                if (response.isSuccessful) {
                    pG.visibility = View.GONE
                    val users = response.body()
                    if (users != null && users.isNotEmpty()) {
                        adapter.setData(users)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Tidak ada following user",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Gagal memuat data: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<DetailUserResponse>>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Gagal memuat data: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun loadGitHubFollowing(username: String) {
        val pG = binding.progressBar
        pG.visibility = View.VISIBLE
        val client = ApiConfig.getApiService()
        val responseCall = client.getFollowing(username)
        responseCall.enqueue(object : Callback<List<DetailUserResponse>> {
            override fun onResponse(
                call: Call<List<DetailUserResponse>>,
                response: Response<List<DetailUserResponse>>
            ) {
                if (response.isSuccessful) {
                    pG.visibility = View.GONE
                    val users = response.body()
                    if (users != null && users.isNotEmpty()) {
                        adapter.setData(users)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Tidak ada following user",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Gagal memuat data: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<DetailUserResponse>>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Gagal memuat data: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
//    binding.sectionLabel.text = "Username " + index
//    val username = arguments?.getString(ARG_USERNAME)
//    val client = ApiConfig.getApiService()
//    val response = client.getFollowing(username.toString())
//    response.enqueue(object: Callback <List<DetailUserResponse>> {
//        override fun onResponse(
//            call: Call<List<DetailUserResponse>>,
//            response: Response<List<DetailUserResponse>>
//        ){
////                   showLoading(false)
//            if(response.isSuccessful) {
//                val responseBody = response.body()
//                if (responseBody != null) {
//                    binding.sectionLabel.text = responseBody.toString()
//                    val adapter = FollowerAdapter(responseBody)
//                    binding.rvFolWing.adapter = adapter
//                }
//            }else{
//                Log.e(DetailUserActivity.TAG,"onFailure: ${response.message()}")
//            }
//        }
//
//        override fun onFailure(call: Call<List<DetailUserResponse>>, t: Throwable){
//            Log.e(DetailUserActivity.TAG, "onFailure: ${t.message}")
//        }
//    })
//    private fun findUsernameGithub(){
//        val username = arguments?.getString(ARG_USERNAME)
//        val client = ApiConfig.getApiService()
//        val response = client.getFollowing(username.toString())
//        response.enqueue(object: Callback <List<DetailUserResponse>> {
//            override fun onResponse(
//                call: Call<List<DetailUserResponse>>,
//                response: Response<List<DetailUserResponse>>
//            ){
////                showLoading(false)
//                if(response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null) {
//                        binding.sectionLabel.text = responseBody.toString()
//                        val adapter = FollowerAdapter(responseBody)
//                        binding.rvFolWing.adapter = adapter
//                    }
//                }else{
//                    Log.e(DetailUserActivity.TAG,"onFailure: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<List<DetailUserResponse>>, t: Throwable){
//                Log.e(DetailUserActivity.TAG, "onFailure: ${t.message}")
//            }
//        })
//    }

//    private fun showLoading(isLoading: Boolean){
//        if (isLoading){
//            binding.progressBar.visibility = View.VISIBLE
//        } else {
//            binding.progressBar.visibility = View.GONE
//        }
//    }
//    @SuppressLint("NotifyDataSetChanged")
//    private fun getFollowerList(responseBody: List<DetailUserResponse>) {
//        val adapter = FollowerAdapter()
//        adapter.notifyDataSetChanged()
//        adapter.submitList(responseBody)
//        binding.rvFolWing.adapter = adapter
//    }
    companion object {

    /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FollowerFollowingFragment.
         */
        // TODO: Rename and change types and number of parameters
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_USERNAME = "username"
    }
}

