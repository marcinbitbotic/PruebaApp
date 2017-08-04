package com.pruebaapp.app.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pruebaapp.app.R;
import com.pruebaapp.app.adapter.UserModelAdapter;
import com.pruebaapp.app.components.ClearableEditText;
import com.pruebaapp.app.model.UserModel;
import com.pruebaapp.app.retrofit.NetworkManager;
import com.pruebaapp.app.ui.activity.MainActivity;
import com.pruebaapp.app.ui.contract.DataManagerInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Marcin Pogorzelski on 03/08/2017.
 */

public class ListFragment extends Fragment implements DataManagerInterface {

	private RecyclerView recyclerView;
	private ProgressBar progressBar;
	private UserModelAdapter adapter;
	private SwipeRefreshLayout swipeRefresh;
	private ClearableEditText searchBarName;
	private FloatingActionButton floatingActionButton;


	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.frag_list, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
		swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
		progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
		floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
		searchBarName = (ClearableEditText) view.findViewById(R.id.search_bar_name);

		searchBarName.addTextChangedListener(textWatcher);

		setupSwipeRefresh();
		setupRecyclerView();
		setupFloatingActionButton();
	}

	private TextWatcher textWatcher = new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

		}

		@Override
		public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

		}

		@Override
		public void afterTextChanged(Editable editable) {
			if(adapter!=null)
				adapter.getFilter().filter(editable.toString());
		}
	};

	@Override
	public void fetchData() {

		Call<List<UserModel>> request = NetworkManager.getInstance().getAll();

		request.enqueue(new Callback<List<UserModel>>() {

			@Override
			public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {

				adapter = new UserModelAdapter(response.body(), getActivity());
				adapter.getFilter().filter(searchBarName.getText().toString());
				adapter.setOnItemClickListener(onItemClickListener);

				recyclerView.setAdapter(adapter);
				progressBar.setVisibility(View.GONE);
			}

			@Override
			public void onFailure(Call<List<UserModel>> call, Throwable t) {
				progressBar.setVisibility(View.GONE);
				Toast.makeText(getActivity(), getString(R.string.error_load_data), Toast.LENGTH_LONG).show();
			}
		});
	}

	public void setupSwipeRefresh() {
		swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

			@Override
			public void onRefresh() {
				swipeRefresh.setRefreshing(false);
				progressBar.setVisibility(View.VISIBLE);
				fetchData();
			}
		});
	}

	private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			 UserModel user = adapter.getItem(position);
			((MainActivity)getActivity()).switchContent(DetailFragment.newInstance(user.getId()),DetailFragment.class.getCanonicalName());
		}
	};

	public void setupFloatingActionButton(){

		floatingActionButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				((MainActivity)getActivity()).switchContent(new DetailFragment(),DetailFragment.class.getCanonicalName());
			}
		});
	}

	public void setupRecyclerView() {

		recyclerView.setHasFixedSize(true);
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(layoutManager);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		progressBar.setVisibility(View.VISIBLE);
		fetchData();
	}

}
