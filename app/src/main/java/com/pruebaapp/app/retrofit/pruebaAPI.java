package com.pruebaapp.app.retrofit;

import com.pruebaapp.app.model.UserModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Marcin Pogorzelski on 03/08/2017.
 */

public interface pruebaAPI {

	@GET("user/getall")
	Call<List<UserModel>> getAll();

	@GET("user/get/{id}")
	Call<UserModel> getById(@Path("id") int id);

	@POST("user/create")
	Call<UserModel> create(@Body UserModel user);

	@POST("user/update")
	Call<UserModel> update(@Body UserModel user);

	@GET("user/remove/{id}")
	Call<ResponseBody> remove(@Path("id") int id);




}
