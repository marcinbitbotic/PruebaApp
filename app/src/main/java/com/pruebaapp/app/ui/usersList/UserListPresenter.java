package com.pruebaapp.app.ui.usersList;

import com.pruebaapp.app.model.UserModel;
import com.pruebaapp.app.retrofit.NetworkManager;
import com.pruebaapp.app.ui.base.BasePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Marcin Pogorzelski on 04/08/2017.
 */

public class UserListPresenter<V extends MvpUserListView> extends BasePresenter<V> implements MvpUserListPresenter<V> {

	@Override
	public void loadData() {

		getMvpView().showLoading();

		Call<List<UserModel>> request = NetworkManager.getInstance().getAll();

		request.enqueue(new Callback<List<UserModel>>() {

			@Override
			public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {

				getMvpView().setData(response.body());
				getMvpView().hideLoading();
			}

			@Override
			public void onFailure(Call<List<UserModel>> call, Throwable t) {
				getMvpView().hideLoading();
				getMvpView().onError(t.getMessage());
			}
		});

	}
}
