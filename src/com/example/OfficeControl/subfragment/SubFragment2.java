package com.example.OfficeControl.subfragment;


import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.example.OfficeControl.ControllerService;
import com.example.OfficeControl.fragment.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.OfficeControl.vo.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SubFragment2 extends Fragment {
	private User user;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.controller, null);
		user=(User)getActivity().getApplication();
		AddData addData = new AddData(view);
		addData.execute();
		view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						Map<String,String> map = new HashMap<String, String>();
						Spinner name =(Spinner)view.findViewById(R.id.name);
						map.put("name",name.getSelectedItem().toString());
						Spinner temp =(Spinner)view.findViewById(R.id.temp);
						map.put("temp",temp.getSelectedItem().toString());
						Spinner kind =(Spinner)view.findViewById(R.id.coldorhot);
						if(kind.getSelectedItem().toString().equals("制热"))
						{
							map.put("kind","1");
						} else{
							map.put("kind","0");
						}
						Spinner status =(Spinner)view.findViewById(R.id.onoroff);
						if(status.getSelectedItem().toString().equals("开")){
							map.put("status","1");
						}else {
							map.put("status","0");
						}

						try {
							submitDataByDoGet(map,"http://182.254.218.192/OfficeControl/updatectrl.servlet");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}).start();

			}
		});
		return view;
	}

	/**
	 * 使用get请求以普通方式提交数据
	 * @param map 传递进来的数据，以map的形式进行了封装
	 * @param path 要求服务器servlet的地址
	 * @return 返回的boolean类型的参数
	 * @throws Exception
	 */
	public Boolean submitDataByDoGet(Map<String, String> map, String path) throws Exception {
		// 拼凑出请求地址
		StringBuilder sb = new StringBuilder(path);
		sb.append("?");
		for (Map.Entry<String, String> entry : map.entrySet()) {
			sb.append(entry.getKey()).append("=").append(entry.getValue());
			sb.append("&");
		}
		sb.deleteCharAt(sb.length() - 1);
		String str = sb.toString();
		System.out.println(str);
		URL Url = new URL(str);
		HttpURLConnection HttpConn = (HttpURLConnection) Url.openConnection();
		HttpConn.setRequestMethod("GET");
		HttpConn.setReadTimeout(5000);
		// GET方式的请求不用设置什么DoOutPut()之类的吗？
		if (HttpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			return true;
		}
		return false;
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
			map.put("temp", jsonObject.getString("temp"));
			map.put("kind", jsonObject.getString("kind"));
			map.put("status", jsonObject.getString("status"));
			list.add(map);
		}
		return list;
	}

	private class AddData extends AsyncTask<String[],String,String[]> {
		private View view;

		public AddData(View view){
			super();
			this.view=view;
		}


		@Override
		protected String[] doInBackground(String[]... params) {
			String data = ControllerService.getInfo(user.getId());
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			try {
				list=Analysis(data);
				String[] result = new String[list.size()];
				for(int i = 0;i<list.size();i++){
					result[i]=list.get(i).get("name").toString();
				}
				return result;
			} catch (JSONException e) {
				e.printStackTrace();
				String[] error={e.toString()};
				return error;
			}

		}

		@Override
		protected void onPostExecute(String[] result) {
			changeSpinner(view,result);
		}
	}
	private void changeSpinner(View view,String[] result) {

		Spinner name = (Spinner)view.findViewById(R.id.name);
		String[] names=result;
		ArrayAdapter adapter=new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_spinner_item,names);
		name.setAdapter(adapter);
	}


}
