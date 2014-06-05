package com.tubingbing.endlesslistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Auto Load More ListView
 * @author bbtu
 *
 */
public class AutoLoadMoreListView extends ListView implements AbsListView.OnScrollListener, View.OnClickListener{
	
	/**
	 * loading status
	 */
	private volatile LoadingStatus loadingStatus = LoadingStatus.NORMAL;
	private OnLoadMoreListener listener;
	private View loadingFooterView;
	private View errorFooterView;
	
	public AutoLoadMoreListView(Context context) {
		super(context);
		setOnScrollListener(this);
	}
	
	public AutoLoadMoreListView(Context context, AttributeSet arrs){
		super(context, arrs);
		setOnScrollListener(this);
	}

	public AutoLoadMoreListView(Context context, AttributeSet arrs, int defStyle){
		super(context, arrs, defStyle);
		setOnScrollListener(this);
	}
	
	/**
	 * loading finish
	 */
	public void onLoadingFinish(){
		addMyFooterView(LoadingStatus.NORMAL);
	}
	
	/**
	 * loading error
	 */
	public void onLoadingError(){
		addMyFooterView(LoadingStatus.ERROR);
	}
	
	public void onLoadingNoMore(){
		addMyFooterView(LoadingStatus.END);
	}
	
	/**
	 * @param loadingResId loading footer view
	 * @param errorResId load error footer view
	 */
	public void setFooterView(int loadingResId, int errorResId){
		LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.loadingFooterView = inflater.inflate(loadingResId, null);
		this.errorFooterView = inflater.inflate(errorResId, null);
		this.errorFooterView.setOnClickListener(this);
		addMyFooterView(LoadingStatus.NORMAL);
	}
	
	private void addMyFooterView(LoadingStatus status) {
		
		// remove the old footer view
		if(getFooterViewsCount() > 0){
			switch (loadingStatus) {
			case NORMAL:
			case LOADING:
				removeFooterView(loadingFooterView);
				break;
			case ERROR:
				removeFooterView(errorFooterView);
				break;
			default:
				break;
			}
		}
		
		// add the new footer view
		this.loadingStatus = status;
		switch (loadingStatus) {
		case NORMAL:
		case LOADING:
			addFooterView(loadingFooterView);
			break;
		case ERROR:
			addFooterView(errorFooterView);
			break;
		case EMPTY:
			// TODO
			break;
		case END:
			// TODO
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
		if(getAdapter() == null){
			return;
		}
		
		int itemCount = getAdapter().getCount();
		// no item
		if(itemCount <= 1){
			addMyFooterView(LoadingStatus.EMPTY);
			return;
		}
		
		int lastItemIndex = firstVisibleItem + visibleItemCount;
		boolean isScrollToEnd = lastItemIndex >= totalItemCount;
		boolean isNorlmalLoadingStatus = (loadingStatus == LoadingStatus.NORMAL);
		if(isScrollToEnd && isNorlmalLoadingStatus){
			addMyFooterView(LoadingStatus.LOADING);
			listener.onLoadMore();
		}
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {}
	
	public void setOnLoadMoreListener(OnLoadMoreListener listener){
		this.listener = listener;
	}
	
	public interface OnLoadMoreListener{
		public void onLoadMore();
		public void onErrorFooterViewClick();
	}

	public enum LoadingStatus{
		NORMAL, LOADING, ERROR, EMPTY, END
	}
	
	@Override
	public void onClick(View v) {
		addMyFooterView(LoadingStatus.LOADING);
		listener.onErrorFooterViewClick();
		listener.onLoadMore();
	}
}
