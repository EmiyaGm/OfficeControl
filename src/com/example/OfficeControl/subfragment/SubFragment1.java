package com.example.OfficeControl.subfragment;

import android.os.AsyncTask;
import android.widget.ListView;
import com.example.OfficeControl.GatherService;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.OfficeControl.fragment.R;
import com.example.OfficeControl.vo.Gather;
import com.example.OfficeControl.vo.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubFragment1 extends Fragment {
	private User user;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gather, null);
		user=(User)getActivity().getApplication();
		AddData addData = new AddData(view);
		addData.execute();
		return view;
	}

	private class AddData extends AsyncTask<List<Gather>,List<Gather>,List<Gather>> {
		private View view;

		public AddData (View view){
			super();
			this.view=view;
		}


		@Override
		protected List<Gather> doInBackground(List<Gather>... params) {
			String data = GatherService.getInfo(user.getId());
			ArrayList<Gather> list = new ArrayList<Gather>();
			try {
				list=Analysis(data);
				return list;
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}

		}

		@Override
		protected void onPostExecute(List<Gather> data) {
			setupView(view,data);
		}
	}

	private void setupView(View view, List<Gather> data) {
		ListView listView = (ListView) view.findViewById(R.id.gatherListView);
		listView.setAdapter(new gatherAdapter(getActivity().getApplicationContext(),data));

	}

	private static ArrayList<Gather> Analysis(String jsonStr)
			throws JSONException {
		/******************* 解析 ***********************/
		JSONArray jsonArray = null;
		ArrayList<Gather> list = new ArrayList<Gather>();
		jsonArray = new JSONArray(jsonStr);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			Gather gather = new Gather();
			gather.setName(jsonObject.getString("name"));
			gather.setOutTemp(jsonObject.getString("outTemp"));
			gather.setInTemp(jsonObject.getString("inTemp"));
			list.add(gather);
		}
		return list;
	}
}
