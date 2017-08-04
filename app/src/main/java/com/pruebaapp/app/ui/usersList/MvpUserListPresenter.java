package com.pruebaapp.app.ui.usersList;

import com.pruebaapp.app.ui.base.MvpPresenter;

/**
 * Created by Marcin Pogorzelski on 04/08/2017.
 */

public interface MvpUserListPresenter <V extends MvpUserListView> extends MvpPresenter<V> {

	void loadData();
}
