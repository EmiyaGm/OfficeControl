package com.example.OfficeControl.fragment;






import android.os.*;
import android.widget.TextView;
import com.example.OfficeControl.InformationService;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.OfficeControl.vo.User;
import org.json.JSONException;
import org.json.JSONObject;

public class FragmentMy extends Fragment {
	private User user;
	private TextView textView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my, null);
		user=(User)getActivity().getApplication();
		AddData addData = new AddData(view);
		addData.execute();
		return view;
	}

	private void setupView(View view,String data) {

		textView=(TextView)view.findViewById(R.id.textView1);
		textView.setText(parseJson(data.toString()));

	}
	private String parseJson(String strResult) {
		try {
			JSONObject jsonObj = new JSONObject(strResult);
			String address = jsonObj.getString("address");
			String name = jsonObj.getString("name");
			String phone = jsonObj.getString("phone");
			user.setId(jsonObj.getInt("id"));
			return ( "姓名：" + name + "\n电话：" + phone+"\n地址："+address);
		} catch (JSONException e) {
			System.out.println("Json parse error");
			e.printStackTrace();
			return e.toString();
		}
	}
	private class AddData extends AsyncTask<String,String,String>{
		private View view;

		public AddData (View view){
			super();
			this.view=view;
		}


		@Override
		protected String doInBackground(String... params) {
			String data = InformationService.getInfo(user.getUsername());
			return data;
		}

		@Override
		protected void onPostExecute(String data) {
			setupView(view,data);
		}
	}

}

