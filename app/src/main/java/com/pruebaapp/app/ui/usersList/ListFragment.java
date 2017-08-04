package com.pruebaapp.app.ui.usersList;

import android.content.Context;
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
import com.pruebaapp.app.ui.activity.MainActivity;
import com.pruebaapp.app.ui.DetailUser.DetailFragment;

import java.util.List;

/**
 * Created by Marcin Pogorzelski on 03/08/2017.
 */

public class ListFragment extends Fragment implements MvpUserListView {

	UserListPresenter<MvpUserListView> presenter;

	private RecyclerView recyclerView;
	private ProgressBar progressBar;
	private UserModelAdapter adapter;
	private SwipeRefreshLayout swipeRefresh;
	private ClearableEditText searchBarName;
	private FloatingActionButton floatingActionButton;

	public ListFragment() {
		presenter = new UserListPresenter<MvpUserListView>();
	}

	@Override
	public void onDetach() {
		super.onDetach();
		presenter.onDetach();
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		presenter.onAttach(ListFragment.this);
	}

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
	public void setupSwipeRefresh() {
		swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

			@Override
			public void onRefresh() {
				swipeRefresh.setRefreshing(false);
				presenter.loadData();
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

	@Override
	public void setData(List<UserModel> users) {
		adapter = new UserModelAdapter(users, getActivity());
		adapter.getFilter().filter(searchBarName.getText().toString());
		adapter.setOnItemClickListener(onItemClickListener);
		recyclerView.setAdapter(adapter);
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
		presenter.loadData();
	}

	@Override
	public void showLoading() {
		progressBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideLoading() {
		progressBar.setVisibility(View.GONE);
	}

	@Override
	public void onError(String message) {
		Toast.makeText(getActivity(), getString(R.string.error_load_data), Toast.LENGTH_LONG).show();
	}
}
