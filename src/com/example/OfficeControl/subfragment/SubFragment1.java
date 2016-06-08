package com.example.OfficeControl.subfragment;

import android.os.AsyncTask;
import com.example.OfficeControl.GatherService;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.OfficeControl.vo.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SubFragment1 extends Fragment {
	private User user;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		TextView tv = new TextView(getActivity());
		user=(User)getActivity().getApplication();
		tv.setTextSize(20);
		tv.setTextColor(Color.parseColor("#000000"));
		tv.setBackgroundColor(Color.parseColor("#FFFFFF"));
		AddData addData = new AddData(tv);
		addData.execute();
		return tv;
	}

	private class AddData extends AsyncTask<String,String,String> {
		private TextView textView;

		public AddData (TextView textView){
			super();
			this.textView=textView;
		}


		@Override
		protected String doInBackground(String... params) {
			String data = GatherService.getInfo(user.getId());
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			try {
				list=Analysis(data);
				String result="";
				for(int i = 0;i<list.size();i++){
					result=result+"name:"+list.get(i).get("name")+"\noutTemp:"+list.get(i).get("outTemp")+"\ninTemp:"+list.get(i).get("inTemp")+"\nhumidity:"+list.get(i).get("humidity")+"\n";
				}
				return result;
			} catch (JSONException e) {
				e.printStackTrace();
				return e.toString();
			}

		}

		@Override
		protected void onPostExecute(String data) {
			setupView(textView,data);
		}
	}

	private void setupView(TextView textView, String data) {
		textView.setText(data);
		textView.setGravity(Gravity.CENTER);
	}

	private static ArrayList<HashMap<String, Object>> Analysis(String jsonStr)
			throws JSONException {
		/******************* 解析 ***********************/
		JSONArray jsonArray = null;
		// 初始化list数组对象
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		jsonArray = new JSONArray(jsonStr);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			// 初始化map数组对象
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("name", jsonObject.getString("name"));
			map.put("outTemp", jsonObject.getString("outTemp"));
			map.put("inTemp", jsonObject.getString("inTemp"));
			map.put("humidity", jsonObject.getString("humidity"));
			list.add(map);
		}
		return list;
	}
}
