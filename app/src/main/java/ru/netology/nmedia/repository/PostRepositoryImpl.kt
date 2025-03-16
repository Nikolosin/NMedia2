package ru.netology.nmedia.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.internal.EMPTY_REQUEST
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepositoryImpl.Companion.BASE_URL
import ru.netology.nmedia.repository.PostRepositoryImpl.Companion.jsonType
import java.io.IOException
import java.util.concurrent.TimeUnit


class PostRepositoryImpl: PostRepository {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()
    private val gson = Gson()
    private val typeToken = object : TypeToken<List<Post>>() {}

    companion object {
        private const val BASE_URL = "http://10.0.2.2:9999"
        private val jsonType = "application/json".toMediaType()
    }

    override fun getAll(callBack: CallBack<List<Post>>) {
        val request: Request = Request.Builder()
            .url("${BASE_URL}/api/slow/posts")
            .build()

        client.newCall(request)
            .enqueue(
                object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callBack.error(e)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val body = response.body?.string() ?: throw RuntimeException("body is null")
                        callBack.onSuccess(gson.fromJson(body, typeToken.type))
                    }
                }
            )
    }


    override fun likeById(id: Long, callBack: CallBack<Post>) {
        val request: Request = Request.Builder()
            .url("${BASE_URL}/api/slow/posts/$id/likes")
            .post(EMPTY_REQUEST)
            .build()

        makeRequest(
            request,
            callBack
        )
        {
            gson.fromJson(it, Post::class.java)
        }
    }

    override fun unlikeById(id: Long, callBack: CallBack<Post>) {

        val request: Request = Request.Builder()
            .url("${BASE_URL}/api/slow/posts/$id/likes")
            .delete()
            .build()


        makeRequest(
            request,
            callBack
        )
        {
            gson.fromJson(it, Post::class.java)
        }
    }



    override fun save(post: Post, callBack: CallBack<Post>) {
        val request: Request = Request.Builder()
            .post(gson.toJson(post).toRequestBody(jsonType))
            .url("$BASE_URL/api/slow/posts")
            .build()

        makeRequest(
            request,
            callBack
        )
        {
            gson.fromJson(it, Post::class.java)
        }
    }

    override fun removeById(id: Long, callBack: CallBack<Unit>) {
        val request: Request = Request.Builder()
            .delete()
            .url("${BASE_URL}/api/slow/posts/$id")
            .build()

        makeRequest(
            request,
            callBack
        )
        {}
    }

    private fun <T> makeRequest(
        request: Request,
        callback: CallBack<T>,
        parse: (String) -> T) {
        client.newCall(request)
            .enqueue(
                object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback.error(e)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val body = response.body?.string() ?: throw RuntimeException("body is null")
                        callback.onSuccess(parse(body))
                    }
                }
            )
    }
}
