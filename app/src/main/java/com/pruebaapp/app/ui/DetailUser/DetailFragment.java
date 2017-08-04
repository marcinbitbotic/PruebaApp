package com.pruebaapp.app.ui.DetailUser;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pruebaapp.app.R;
import com.pruebaapp.app.model.UserModel;
import com.pruebaapp.app.utils.DateUtils;

import java.util.Date;

/**
 * Created by Marcin Pogorzelski on 03/08/2017.
 */

public class DetailFragment extends Fragment implements MvpDetailUserView {

	DetailUserPresenter<MvpDetailUserView> presenter;
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
	public void onDetach() {
		super.onDetach();
		presenter.onDetach();

	}

	public DetailFragment() {
		presenter = new DetailUserPresenter<MvpDetailUserView>();
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		presenter.onAttach(DetailFragment.this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.frag_detail, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (id > 0) {
			presenter.loadData();
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
								presenter.removeUser(id);
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

	@Override
	public void handleClick(){

		String name = editNombre.getText().toString().trim();
		String fecha = editFecha.getText().toString().trim();

		if(validData(name,fecha)){

			UserModel user = new UserModel();
			user.setName(name);
			user.setBirthDate(DateUtils.parseDateFromString(fecha));

			if (id > 0) {
				presenter.editUser(user);
			}else {
				presenter.createUser(user);
			}
		}
	}

	@Override
	public void setData(UserModel users) {

		if(users!=null){
			editNombre.setText(users.getName());
			labelId.setText(""+users.getId());
			editFecha.setText(DateUtils.DateToString(users.getBirthDate()));
		}
	}

	@Override
	public void onBackPressed() {
		getActivity().onBackPressed();
	}

	@Override
	public int getUserID() {
		return id;
	}

	@Override
	public void onSuccessMessage(String message) {
		Toast.makeText(getActivity(),message, Toast.LENGTH_LONG).show();
	}

	@Override
	public void showLoading() {
		//Not implemented
	}

	@Override
	public void hideLoading() {
		//Not implemented
	}

	@Override
	public void onError(String message) {
		Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean validData(String name,String fecha){

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

	public void setId(int id) {
		this.id = id;
	}
}
