package com.example.OfficeControl.subfragment;

import android.os.AsyncTask;
import android.widget.ImageView;
import com.example.OfficeControl.WeatherService;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.OfficeControl.fragment.R;
import com.example.OfficeControl.vo.Weather;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubFragment3 extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.weather, null);
		AddData addData = new AddData(view);
		addData.execute();
		return view;
	}

	private class AddData extends AsyncTask<Weather,Weather,Weather> {
		private View view;

		public AddData (View view){
			super();
			this.view=view;
		}


		@Override
		protected Weather doInBackground(Weather... params) {
			Weather weather = new Weather();
			try {
				weather = WeatherService.query("常熟");
				return weather;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(Weather weather) {
			setupView(view,weather);
		}
	}

	private void setupView(View view,Weather weather) {
		TextView weatherDate = (TextView) view.findViewById(R.id.weatherDate);
		TextView weatherDay = (TextView) view.findViewById(R.id.weatherDay);
		TextView weatherCurTemp = (TextView) view.findViewById(R.id.weatherCurTemp);
		ImageView weatherPic1 = (ImageView) view.findViewById(R.id.weatherPic1);
		ImageView weatherPic2 = (ImageView) view.findViewById(R.id.weatherPic2);
		TextView weatherCondition = (TextView) view.findViewById(R.id.weatherCondition);
		TextView weatherTemp = (TextView) view.findViewById(R.id.weatherTemp);
		TextView weatherWind = (TextView) view.findViewById(R.id.weatherWind);
		weatherDate.setText(weather.getDate());
		weatherDay.setText(weather.getDay());
		weatherCurTemp.setText(weather.getCurrTemp());
		weatherPic1.setImageBitmap(weather.getPic1());
		weatherPic2.setImageBitmap(weather.getPic2());
		weatherCondition.setText(weather.getCondition());
		weatherTemp.setText(weather.getTemp());
		weatherWind.setText(weather.getWind());

	}
}
