package com.agt.marvel.data.network

import android.util.Log
import com.agt.marvel.core.RetrofitHelper
import com.agt.marvel.data.model.MarvelCharacter
import com.agt.marvel.data.model.datamarvel
import com.agt.marvel.data.model.series.ModelResponseSerie
import com.agt.marvel.data.model.series.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.math.BigInteger
import java.security.MessageDigest

class DataService {

    private val retrofit = RetrofitHelper.getRetrofit()
    var ts: String? = "11111111111"

    val privatekey: String = "919c857399855486312e08209dfc8973146b8bb0"
    val publickey: String = "748c8ee0357e0a1cd5d4c2490b317fad"
    var apikey: String? = md5("$ts$privatekey$publickey")

    suspend fun getAllMarvelCharacter(): List<MarvelCharacter> {
        return withContext(Dispatchers.IO) {


            val response: Response<datamarvel> = retrofit.create(DataApiClient::class.java)
                .getAllMarvelCharacter(ts, publickey, apikey)
            tolog(" response " + response)
            val c: MutableList<MarvelCharacter> = ArrayList()

            val datam: datamarvel = response.body()!!
            tolog(" response datam" + datam.toString())





            for (i in 0 until datam.data.results.size) {

                c.add(MarvelCharacter(datam.data.results[i]))
            }







            c
        }
    }


    suspend fun getDataSerie(id: String): List<Result> {
        return withContext(Dispatchers.IO) {
            val c: MutableList<Result> = ArrayList()
            val response: Response<ModelResponseSerie> =
                retrofit.create(DataApiClient::class.java).getSerieInfo(id, ts, publickey, apikey)
            tolog(" response serie $id       " + response)

            val datamseries: ModelResponseSerie? = response.body()

            if (datamseries != null) {
                tolog(" response datam" + datamseries.toString())
                for (z in 0 until datamseries.data.results.size) {
                    c.add(datamseries.data.results[z])
                }
            }

            c
        }
    }


    fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }


    fun tolog(msg: String) {
        Log.d("TEST", " data service  + $msg")
    }
}
