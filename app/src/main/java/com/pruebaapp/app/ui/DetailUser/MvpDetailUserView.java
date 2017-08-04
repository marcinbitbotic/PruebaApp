package com.pruebaapp.app.ui.DetailUser;

import com.pruebaapp.app.model.UserModel;
import com.pruebaapp.app.ui.base.MvpView;

/**
 * Created by Marcin Pogorzelski on 04/08/2017.
 */

public interface MvpDetailUserView extends MvpView {

	void setData(UserModel users);

	void onBackPressed();

	int getUserID();

	void onSuccessMessage(String message);

	boolean validData(String name,String fecha);

	void handleClick();
}
