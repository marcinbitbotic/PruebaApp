package com.pruebaapp.app.ui.DetailUser;

import android.content.Context;
import android.widget.Toast;

import com.pruebaapp.app.R;
import com.pruebaapp.app.model.UserModel;
import com.pruebaapp.app.retrofit.NetworkManager;
import com.pruebaapp.app.ui.base.BasePresenter;
import com.pruebaapp.app.utils.DateUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Marcin Pogorzelski on 04/08/2017.
 */

public class DetailUserPresenter <V extends MvpDetailUserView> extends BasePresenter<V> implements MvpDetailUserPresenter<V> {

	@Override
	public void loadData() {

		Call<UserModel> request = NetworkManager.getInstance().getUserByID(getMvpView().getUserID());

		request.enqueue(new Callback<UserModel>() {

			@Override
			public void onResponse(Call<UserModel> call, Response<UserModel> response) {

				UserModel editUser = response.body();
				getMvpView().setData(editUser);
			}

			@Override
			public void onFailure(Call<UserModel> call, Throwable t) {
				getMvpView().onError(getMvpView().getContext().getString(R.string.error_load_data));
			}
		});

	}

	@Override
	public void createUser(UserModel user) {

		Call<UserModel> call = NetworkManager.getInstance().createUser(user);

		call.enqueue(new Callback<UserModel>() {

			@Override
			public void onResponse(Call<UserModel> call, Response<UserModel> response) {
				getMvpView().onSuccessMessage(getMvpView().getContext().getString(R.string.frag_detail_create_success));
				getMvpView().onBackPressed();
			}

			@Override
			public void onFailure(Call<UserModel> call, Throwable t) {
				getMvpView().onError(getMvpView().getContext().getString(R.string.frag_detail_create_error));
			}

		});
	}

	@Override
	public void editUser(UserModel user) {

		Call<UserModel> call = NetworkManager.getInstance().editUser(user);

		call.enqueue(new Callback<UserModel>() {

			@Override
			public void onResponse(Call<UserModel> call, Response<UserModel> response) {
				getMvpView().onSuccessMessage(getMvpView().getContext().getString(R.string.frag_detail_edit_success));
				getMvpView().onBackPressed();
			}

			@Override
			public void onFailure(Call<UserModel> call, Throwable t) {
				getMvpView().onError(getMvpView().getContext().getString(R.string.frag_detail_edit_error));
			}
		});

	}

	@Override
	public void removeUser(int id) {

		Call<ResponseBody> call = NetworkManager.getInstance().removeUser(id);

		call.enqueue(new Callback<ResponseBody>() {

			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
				getMvpView().onSuccessMessage(getMvpView().getContext().getString(R.string.frag_detail_remove_success));
				getMvpView().onBackPressed();
			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {
				getMvpView().onError(getMvpView().getContext().getString(R.string.frag_detail_remove_error));
			}
		});

	}
}
