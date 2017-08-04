package com.pruebaapp.app.ui.base;

/**
 * Created by Marcin Pogorzelski on 04/08/2017.
 */

import android.content.Context;

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * onAttach() and onDetach(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 */
public class BasePresenter <V extends MvpView> implements MvpPresenter<V> {

	private V mvpView;

	public BasePresenter() {
	}

	@Override
	public void onAttach(V mvpView) {
		this.mvpView = mvpView;
	}

	@Override
	public void onDetach() {
		mvpView = null;
	}

	@Override
	public void handleApiError(String error) {
		getMvpView().onError(error);
	}

	public boolean isViewAttached() {
		return mvpView != null;
	}

	public V getMvpView() {
		return mvpView;
	}

	public void checkViewAttached() {
		if (!isViewAttached()) throw new MvpViewNotAttachedException();
	}

	public static class MvpViewNotAttachedException extends RuntimeException {
		public MvpViewNotAttachedException() {
			super("Please call Presenter.onAttach(MvpView) before" +
					" requesting data to the Presenter");
		}
	}
}
