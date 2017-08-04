package com.pruebaapp.app.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.pruebaapp.app.utils.DateUtils;

import java.util.Date;

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

				UserModel editUser = response.body();

				if(editUser!=null){
					editNombre.setText(editUser.getName());
					labelId.setText(""+editUser.getId());
					editFecha.setText(DateUtils.DateToString(editUser.getBirthDate()));
				}

			}

			@Override
			public void onFailure(Call<UserModel> call, Throwable t) {
				Toast.makeText(getActivity(), getString(R.string.error_load_data), Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.frag_detail, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (id > 0) {
			fetchData();
		}else{
			String current = DateUtils.DateToString(new Date());
			editFecha.setText(current);
		}

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		labelId = (TextView) view.findViewById(R.id.txt_id);
		editNombre = (EditText) view.findViewById(R.id.ed_name);
		editFecha = (EditText) view.findViewById(R.id.ed_date);
		btnEdit = (Button) view.findViewById(R.id.btn_edit);
		btnRemove = (Button) view.findViewById(R.id.btn_remove);
		btnCreate = (Button) view.findViewById(R.id.btn_create);

		if (id > 0) {

			btnCreate.setVisibility(View.GONE);
			btnEdit.setVisibility(View.VISIBLE);
			btnRemove.setVisibility(View.VISIBLE);

		} else {

			btnCreate.setVisibility(View.VISIBLE);
			btnEdit.setVisibility(View.GONE);
			btnRemove.setVisibility(View.GONE);

		}

		btnEdit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {handleClick();}
		});
		btnRemove.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which){
							case DialogInterface.BUTTON_POSITIVE:
								removeUser(id);
								break;

							case DialogInterface.BUTTON_NEGATIVE:

								break;
						}
					}
				};

				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setMessage("Estas seguro que quieres eliminar este usuario?").setPositiveButton("Si", dialogClickListener)
						.setNegativeButton("No", dialogClickListener).show();

			}

		});
		btnCreate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {handleClick();
			}
		});
	}

	private void handleClick(){

		String name = editNombre.getText().toString().trim();
		String fecha = editFecha.getText().toString().trim();

		if(validData(name,fecha)){

			UserModel user = new UserModel();
			user.setName(name);
			user.setBirthDate(DateUtils.parseDateFromString(fecha));

			if (id > 0) {
				editUser(user);
			}else {
				createUser(user);
			}
		}
	}

	private boolean validData(String name,String fecha){

		boolean isValid = true;

		if(name.isEmpty()){
			editNombre.setError(getString(R.string.empty_field));
			isValid = false;
		}

		if (fecha.isEmpty()) {
			editFecha.setError(getString(R.string.empty_field));
			isValid = false;
		}

		Date d = DateUtils.parseDateFromString(fecha);

		if (d == null) {
			editFecha.setError(getString(R.string.format_error));
			isValid = false;
		}

		return isValid;
	}

	private void createUser(UserModel user){

		Call<UserModel> call = NetworkManager.getInstance().createUser(user);

		call.enqueue(new Callback<UserModel>() {

			@Override
			public void onResponse(Call<UserModel> call, Response<UserModel> response) {
				Toast.makeText(getActivity(), R.string.frag_detail_create_success, Toast.LENGTH_LONG).show();
				getActivity().onBackPressed();
			}

			@Override
			public void onFailure(Call<UserModel> call, Throwable t) {
				Toast.makeText(getActivity(), R.string.frag_detail_create_error, Toast.LENGTH_LONG).show();

			}
		});

	}

	private void editUser(UserModel user){

		Call<UserModel> call = NetworkManager.getInstance().editUser(user);

		call.enqueue(new Callback<UserModel>() {

			@Override
			public void onResponse(Call<UserModel> call, Response<UserModel> response) {
				Toast.makeText(getActivity(), R.string.frag_detail_edit_success, Toast.LENGTH_LONG).show();
				getActivity().onBackPressed();
			}

			@Override
			public void onFailure(Call<UserModel> call, Throwable t) {
				Toast.makeText(getActivity(), R.string.frag_detail_edit_error, Toast.LENGTH_LONG).show();

			}
		});

	}

	private void removeUser(int id) {

		Call<ResponseBody> call = NetworkManager.getInstance().removeUser(id);

		call.enqueue(new Callback<ResponseBody>() {

			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
				Toast.makeText(getActivity(), R.string.frag_detail_remove_success, Toast.LENGTH_LONG).show();
				getActivity().onBackPressed();
			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {
				Toast.makeText(getActivity(), R.string.frag_detail_remove_error, Toast.LENGTH_LONG).show();

			}
		});
	}

	public void setId(int id) {
		this.id = id;
	}
}
