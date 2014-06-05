package com.tubingbing.endlesslistview.sample;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EndlessAdapter extends BaseAdapter{

	private Context mContext;
	private List<String> mData;
	public EndlessAdapter(Context context, List<String> data){
		mContext = context;
		mData = data;
	}
	
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView tv = new TextView(mContext);
		tv.setTextSize(25);
		tv.setText(mData.get(position));
		return tv;
	}

	public void addNewData(List<String> data){
		mData.addAll(data);
		notifyDataSetChanged();
	}
	
}
