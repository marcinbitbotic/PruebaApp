package com.pruebaapp.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.pruebaapp.app.R;
import com.pruebaapp.app.model.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcin Pogorzelski on 03/08/2017.
 */

public class UserModelAdapter extends RecyclerView.Adapter<UserModelAdapter.UserViewHolder> implements Filterable {

	private List<UserModel> userList;
	private List<UserModel> orginalList;
	private Context context;
	private AdapterView.OnItemClickListener onItemClickListener;

	public UserModelAdapter(List<UserModel> userList, Context context) {
		this.userList = userList;
		this.orginalList=userList;
		this.context = context;
	}

	public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		final View view = (View) LayoutInflater
				.from(parent.getContext())
				.inflate(R.layout.user_item, parent, false);

		return new UserViewHolder(view);
	}

	public void onBindViewHolder(UserViewHolder holder, int position) {
		UserModel user = userList.get(position);
		holder.bindData(user);
	}

	public UserModel getItem(int position) {
		return userList.get(position);
	}

	@Override
	public int getItemCount() {
		return userList.size();
	}


	@Override
	public Filter getFilter() {

		return new Filter() {

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				List<UserModel> filteredList = null;
				filteredList = new ArrayList<UserModel>();

				final FilterResults results = new FilterResults();


				if(constraint.toString().isEmpty())
				{
					filteredList = orginalList;

				}else{
					for (final UserModel user : orginalList) {
						if (user.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
							filteredList.add(user);
						}
					}
				}



				results.values = filteredList;
				results.count = filteredList.size();
				return results;
			}

			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				userList = (ArrayList<UserModel>) results.values;
				notifyDataSetChanged();
			}
		};
	}

	class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		protected TextView name;


		public UserViewHolder(final View itemView) {
			super(itemView);
			this.name = (TextView) itemView.findViewById(R.id.txt_name);
			itemView.setOnClickListener(this);
		}

		public void bindData(UserModel user) {
			name.setText(user.getName());
		}

		@Override
		public void onClick(View view) {
			if (onItemClickListener != null)
				onItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());
		}
	}

}
