package com.pruebaapp.app.ui.base;

/**
 * Created by Marcin Pogorzelski on 04/08/2017.
 */

import android.content.Context;

/**
 * Base interface that any class that wants to act as a View in the MVP (Model View Presenter)
 * pattern must implement. Generally this interface will be extended by a more specific interface
 * that then usually will be implemented by an Activity or Fragment.
 */

public interface MvpView {

	void showLoading();

	void hideLoading();

	void onError(String message);

	 Context getContext();


}
