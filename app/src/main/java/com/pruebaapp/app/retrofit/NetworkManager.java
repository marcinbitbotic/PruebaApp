package com.pruebaapp.app.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pruebaapp.app.model.UserModel;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Marcin Pogorzelski on 03/08/2017.
 */

public class NetworkManager {

	private static volatile NetworkManager instance;
	private final pruebaAPI api;

	private NetworkManager() {

		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

		OkHttpClient.Builder builder = new OkHttpClient().newBuilder();

		OkHttpClient client = builder
				.addInterceptor(interceptor).build();

		//Date Format is : 1982-09-13T04:00:00
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
				.create();

		api = new Retrofit.Builder()
				.baseUrl("http://hello-world.innocv.com/api/")
				.addConverterFactory(GsonConverterFactory.create(gson))
				.client(client)
				.build()
				.create(pruebaAPI.class);

	}

	public static NetworkManager getInstance() {
		if (instance == null) {
			synchronized (pruebaAPI.class) {
				if (instance == null) {
					instance = new NetworkManager();
				}
			}
		}
		return instance;
	}

	public Call<List<UserModel>> getAll() {
		return api.getAll();
	}

	public Call<UserModel> getUserByID(int id) {
		return api.getById(id);
	}

	public Call<ResponseBody> removeUser(int id) {
		return api.remove(id);
	}

	public Call<UserModel> editUser(UserModel user) {
		return api.update(user);
	}

	public Call<UserModel> createUser(UserModel user) {
		return api.create(user);
	}
}
