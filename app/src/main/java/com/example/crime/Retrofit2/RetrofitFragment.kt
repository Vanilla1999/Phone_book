package com.example.crime.Retrofit2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.crime.R
import kotlinx.android.synthetic.main.retrofit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RetrofitFragment : Fragment(R.layout.retrofit) {
    val networkService: NetworkService = NetworkService.instance
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        networkService
            .getJSONApi()
            .getuser(5)
            .enqueue(
                object : Callback<GitHubuser> {
                    override fun onFailure(call: Call<GitHubuser>, t: Throwable) {
                        textView3.append("Error occurred while getting request!")
                        t.stackTrace
                    }

                    override fun onResponse(call: Call<GitHubuser>, response: Response<GitHubuser>) {
                        val post = response.body()
                        if (post != null) {
                            textView3.append("${post.getId()}" + "\n")
                            textView3.append("${post.getUserId()}" + "\n")
                            textView3.append(post.getTitle() + "\n")
                            textView3.append(post.getBody() + "\n")
                        }
                    }

                })
        networkService
            .getJSONApi()
            .getuser1()
            .enqueue(
                object : Callback<List<GitHubuser>> {
                    override fun onResponse(
                        call: Call<List<GitHubuser>>,
                        response: Response<List<GitHubuser>>
                    ) {
                        val post=response.body()
                        post?.forEach { textView2.append("${it.getUserId()}")
                            textView2.append("${it.getId()}")
                            textView2.append(it.getTitle())
                            textView2.append(it.getBody()) }
                    }

                    override fun onFailure(call: Call<List<GitHubuser>>, t: Throwable) {
                        textView2.append("Error occurred while getting request!")
                        t.stackTrace
                    }

                })
    }
}