package com.pruebaapp.app.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pruebaapp.app.R;
import com.pruebaapp.app.model.UserModel;
import com.pruebaapp.app.retrofit.NetworkManager;
import com.pruebaapp.app.ui.contract.DataManagerInterface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Marcin Pogorzelski on 03/08/2017.
 */

public class DetailFragment extends Fragment implements DataManagerInterface {

	private Button btnEdit;
	private Button btnCreate;
	private Button btnRemove;
	private TextView labelId;
	private EditText editNombre;
	private EditText editFecha;
	private int id;
	private UserModel editUser;

	public static DetailFragment newInstance(int id) {
		DetailFragment fargment = new DetailFragment();
		fargment.setId(id);
		return fargment;
	}

	@Override
	public void fetchData() {
		Call<UserModel> request = NetworkManager.getInstance().getUserByID(id);

		request.enqueue(new Callback<UserModel>() {

			@Override
			public void onResponse(Call<UserModel> call, Response<UserModel> response) {

				editUser = response.body();
				editNombre.setText(editUser.getName());
				labelId.setText("" + editUser.getId());
				editFecha.setText(editUser.getBirthDate().toString());
			}

			@Override
			public void onFailure(Call<UserModel> call, Throwable t) {

			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frag_detail, container, false);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (id > 0)
			fetchData();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		labelId = (TextView) view.findViewById(R.id.txt_id);
		editNombre = (EditText) view.findViewById(R.id.ed_name);
		editFecha = (EditText) view.findViewById(R.id.ed_fecha);
		btnEdit = (Button) view.findViewById(R.id.btn_edit);
		btnRemove = (Button) view.findViewById(R.id.btn_remove);
		btnCreate = (Button) view.findViewById(R.id.btn_create);

		btnEdit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String name = editNombre.getText().toString().trim();
				String fecha = editFecha.getText().toString().trim();

				//UserModel editUser = new UserModel();
				//editUser.setId(id);
				editUser.setName(name);
				//editUser.getBirthDate();
				editUser(editUser);
			}
		});

		btnRemove.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				removeUser(id);
			}

		});

		if (id > 0) {

			btnCreate.setVisibility(View.GONE);
			btnEdit.setVisibility(View.VISIBLE);
			btnRemove.setVisibility(View.VISIBLE);

		} else {

			btnCreate.setVisibility(View.VISIBLE);
			btnEdit.setVisibility(View.GONE);
			btnRemove.setVisibility(View.GONE);

		}

	}

	private void editUser(UserModel user){

		Call<UserModel> call = NetworkManager.getInstance().editUser(user);

		call.enqueue(new Callback<UserModel>() {

			@Override
			public void onResponse(Call<UserModel> call, Response<UserModel> response) {
				Toast.makeText(getActivity(), "Usuario se ha Modificado", Toast.LENGTH_LONG).show();
				getActivity().onBackPressed();
			}

			@Override
			public void onFailure(Call<UserModel> call, Throwable t) {
				Toast.makeText(getActivity(), "Error al modificar usuario", Toast.LENGTH_LONG).show();

			}
		});

	}

	private void removeUser(int id) {

		Call<ResponseBody> call = NetworkManager.getInstance().removeUser(id);

		call.enqueue(new Callback<ResponseBody>() {

			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
				Toast.makeText(getActivity(), "Usuario eliminado", Toast.LENGTH_LONG).show();
				getActivity().onBackPressed();
			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {
				Toast.makeText(getActivity(), "Error al eliminar usuario", Toast.LENGTH_LONG).show();

			}
		});
	}

	public void setId(int id) {
		this.id = id;
	}
}
