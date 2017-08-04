package com.pruebaapp.app.ui.DetailUser;

import com.pruebaapp.app.model.UserModel;
import com.pruebaapp.app.ui.base.MvpPresenter;
import com.pruebaapp.app.ui.usersList.MvpUserListView;

/**
 * Created by Marcin Pogorzelski on 04/08/2017.
 */

public interface MvpDetailUserPresenter <V extends MvpDetailUserView> extends MvpPresenter<V> {

	void loadData();

	void createUser(UserModel user);

	void editUser(UserModel user);

	void removeUser(int id);
}
