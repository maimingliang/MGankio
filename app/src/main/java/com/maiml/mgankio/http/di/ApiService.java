package com.maiml.mgankio.http.di;




import com.maiml.mgankio.base.BaseHttpResult;
import com.maiml.mgankio.bean.GankIoBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author maimingliang
*/
public interface ApiService {

    @GET("random/data/{category}/{num}")
    Observable<BaseHttpResult<List<GankIoBean>>> findList(@Path("category") String category, @Path("num") int num);


    @GET("data/{category}/{num}/{page}")
    Observable<BaseHttpResult<List<GankIoBean>>> findCategoryList(@Path("category") String category, @Path("num") int num, @Path("page") int page);
}
