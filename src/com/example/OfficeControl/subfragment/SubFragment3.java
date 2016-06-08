package com.example.OfficeControl.subfragment;

import android.os.AsyncTask;
import com.example.OfficeControl.WeatherService;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.OfficeControl.vo.Weather;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubFragment3 extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		TextView tv = new TextView(getActivity());
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
			List<Weather> weathers = new ArrayList<Weather>();
			try {
				weathers = WeatherService.query("苏州");
				String result = "温度："+weathers.get(0).getTemp()+"\n天气状况："+weathers.get(0).getCondition()+"\n风速："+weathers.get(0).getWind();
				return result;
			} catch (IOException e) {
				e.printStackTrace();
				return e.toString();
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

	private void setupView(TextView textView,String data) {
		textView.setText(data);
		textView.setGravity(Gravity.CENTER);
	}
}
