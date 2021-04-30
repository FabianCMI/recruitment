
import com.example.maniaksearch.network.ApiListResults
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

private const val BASE_URL =
    "https://itunes.apple.com"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofit =
    Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(
        BASE_URL
    ).build()

interface ItunesApiService {
    // Use retrofit to make the get api call with @GET value = endpoint, @Query value = user query
    @GET("search")
    suspend fun getResFromApi(
        @Query("term") term: String,
        @QueryMap option: Map<String, String>,
    ) : ApiListResults
}

/**
 * Make a singleton for the 'api caller' object
 */
object ItunesApi {
    val retrofitService: ItunesApiService by lazy {
        retrofit.create(ItunesApiService::class.java)
    }
}
