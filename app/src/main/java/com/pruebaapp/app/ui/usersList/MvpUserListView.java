package com.pruebaapp.app.ui.usersList;

import com.pruebaapp.app.model.UserModel;
import com.pruebaapp.app.ui.base.MvpView;

import java.util.List;

/**
 * Created by Marcin Pogorzelski on 04/08/2017.
 */

public interface MvpUserListView extends MvpView {

	void setupSwipeRefresh();

	void setupRecyclerView();

	void setupFloatingActionButton();

	void setData(List<UserModel> users);
}
