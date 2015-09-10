package ilijan.hotmath;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by ilijan on 10.09.15..
 */
public interface StackExchangeService {
    @GET("/questions/featured?pagesize=5&order=desc&sort=votes&site=math")
    Call<Wrapper> listQuestions();
}
