package ru.netology.nmedia.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.dto.Post
import java.io.IOException


class PostRepositoryImpl : PostRepository {
    override fun getAll(callBack: CallBack<List<Post>>) {
        PostsApi.retrofitService.getAll().enqueue(object : Callback<List<Post>> {
            override fun onFailure(call: Call<List<Post>>, e: Throwable) {
                callBack.error(IOException(e))
            }

            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    callBack.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                } else {
                    callBack.error(RuntimeException("Unsuccessful response from the server"))
                }
            }
        })
    }

    override fun likeById(id: Long, callBack: CallBack<Post>) {
        PostsApi.retrofitService.likeById(id).enqueue(object : Callback<Post> {
            override fun onFailure(call: Call<Post>, e: Throwable) {
                callBack.error(IOException(e))
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    callBack.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                } else {
                    callBack.error(RuntimeException("Unsuccessful response from the server"))
                }
            }
        })
    }

    override fun unlikeById(id: Long, callBack: CallBack<Post>) {
        PostsApi.retrofitService.dislikeById(id).enqueue(object : Callback<Post> {
            override fun onFailure(call: Call<Post>, e: Throwable) {
                callBack.error(IOException(e))
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    callBack.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                } else {
                    callBack.error(RuntimeException("Unsuccessful response from the server"))
                }
            }
        })
    }


    override fun save(post: Post, callBack: CallBack<Post>) {
        PostsApi.retrofitService.save(post).enqueue(object : Callback<Post> {
            override fun onFailure(call: Call<Post>, e: Throwable) {
                callBack.error(IOException(e))
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    callBack.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                } else {
                    callBack.error(RuntimeException("Unsuccessful response from the server"))
                }
            }
        })
    }

    override fun removeById(id: Long, callBack: CallBack<Unit>) {
        PostsApi.retrofitService.removeById(id).enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, e: Throwable) {
                callBack.error(IOException(e))
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    callBack.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                } else {
                    callBack.error(RuntimeException("Unsuccessful response from the server"))
                }
            }
        })
    }
}
