package com.tubingbing.endlesslistview.sample;

import java.util.ArrayList;
import java.util.List;

import com.tubingbing.endlesslistview.AutoLoadMoreListView;
import com.tubingbing.endlesslistview.R;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	private Context mContext;
	private int currentPage = 1;
	private AutoLoadMoreListView lv;
	private EndlessAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;
		List<String> data = getPageData();
		lv = (AutoLoadMoreListView) findViewById(R.id.lv);
		lv.setOnLoadMoreListener(new AutoLoadMoreListView.OnLoadMoreListener() {
			
			@Override
			public void onLoadMore() {
				Log.i("INFO", "load more...");
				new Handler(){}.postDelayed(new Runnable() {
					@Override
					public void run() {
						if(isNetwork(mContext)){ 
							if(currentPage == 6){ // test no item to load...
								lv.onLoadingNoMore();
							}else{
								// get the new data...
								List<String> data = getPageData();
								lv.onLoadingFinish();
								adapter.addNewData(data);
							}
						}else{ // test load error occur...
							lv.onLoadingError();
						}
					}
				}, 5000);
			}
			
			@Override
			public void onErrorFooterViewClick() {
				Log.i("INFO", "erro footer view onclick...");
			}
		});
		View emptyView = findViewById(R.id.empty);
		lv.setEmptyView(emptyView);
		lv.setFooterView(R.layout.loading_view, R.layout.loading_error_view);
		adapter = new EndlessAdapter(this, data);
		lv.setAdapter(adapter);
		
	}

	private List<String> getPageData() {
		List<String> data = new ArrayList<String>();
		data.add("第" + currentPage + "页 0");
		data.add("第" + currentPage + "页 1");
		data.add("第" + currentPage + "页 2");
		data.add("第" + currentPage + "页 3");
		data.add("第" + currentPage + "页 4");
		data.add("第" + currentPage + "页 5");
		data.add("第" + currentPage + "页 6");
		data.add("第" + currentPage + "页 7");
		data.add("第" + currentPage + "页 8");
		data.add("第" + currentPage + "页 9");
		data.add("第" + currentPage + "页 10");
		data.add("第" + currentPage + "页 11");
		data.add("第" + currentPage + "页 12");
		data.add("第" + currentPage + "页 13");
		currentPage++;
		return data;
	}

	public boolean isNetwork(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null) {
			return false;
		}
		if (connectivityManager.getActiveNetworkInfo() == null) {
			return false;
		}
		return connectivityManager.getActiveNetworkInfo().isAvailable();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
