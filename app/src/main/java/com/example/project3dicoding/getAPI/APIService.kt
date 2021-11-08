import com.example.project3dicoding.getAPI.DetailUserResponse
import com.example.project3dicoding.getAPI.FollowResponse
import com.example.project3dicoding.GithubUserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    fun getUserSearch(
        @Query("q") q: String
    ): Call<GithubUserResponse>

    @GET("users/{login}")
    fun getDetailUser(
        @Path("login") login: String
    ): Call<DetailUserResponse>

    @GET("users/{login}/followers")
    fun getFollower(
        @Path("login") login: String
    ): Call<List<FollowResponse>>

    @GET("users/{login}/following")
    fun getFollowing(
        @Path("login") login: String
    ): Call<List<FollowResponse>>
}