package com.example.OfficeControl.fragment;

import com.example.OfficeControl.commom.T;
import com.example.OfficeControl.fragment.R;
import com.example.OfficeControl.subfragment.SubFragment1;
import com.example.OfficeControl.subfragment.SubFragment2;
import com.example.OfficeControl.subfragment.SubFragment3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import android.util.DisplayMetrics;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class FragmentMessage extends Fragment implements View.OnClickListener {

	private static String TAG = FragmentMessage.class.getName();

	private LinearLayout lin_sub1, lin_sub2, lin_sub3;

	private Fragment subFragment1;
	private Fragment subFragment2;
	private Fragment subFragment3;

	private ImageView mTabline;
	private int mScreen1_3;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		Bundle bundle = getArguments();
		if (null != bundle) {
			//
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_message1, null);

		initView(view);
		setLinstener();
		initData();
		return view;
	}

	private void initView(View view) {

		lin_sub1 = (LinearLayout) view.findViewById(R.id.lin_sub1);
		lin_sub2 = (LinearLayout) view.findViewById(R.id.lin_sub2);
		lin_sub3 = (LinearLayout) view.findViewById(R.id.lin_sub3);
		mTabline = (ImageView) view.findViewById(R.id.imv_tabline);

	}

	protected void initData() {
		// Display display = getWindow().getWindowManager().getDefaultDisplay();
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);
		mScreen1_3 = outMetrics.widthPixels / 3;
		LayoutParams lp = (LayoutParams) mTabline.getLayoutParams();
		lp.width = mScreen1_3;
		mTabline.setLayoutParams(lp);
		//初次显示设置
		//setSubFragment(0);
		setSubFragment(0);
		setmTabline(0);

	}

	protected void setLinstener() {
		lin_sub1.setOnClickListener(this);
		lin_sub2.setOnClickListener(this);
		lin_sub3.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.lin_sub1:
				//	setSubFragment(0);
				showSubFragment(0);
				setmTabline(0);
				break;
			case R.id.lin_sub2:
				//	setSubFragment(1);
				showSubFragment(1);
				setmTabline(1);
				break;
			case R.id.lin_sub3:
				//	setSubFragment(2);
				showSubFragment(2);
				setmTabline(2);
				break;
			default:
				break;
		}

	}

	public void setmTabline(int i) {

		LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) mTabline
				.getLayoutParams();
		lp.leftMargin = i * mScreen1_3;
		mTabline.setLayoutParams(lp);

	}

	/*
	 * 显示subFragment,subFragment会重复onCreateView
	 */
	public void setSubFragment(int i) {

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();

		if (0 == i) {
			subFragment1 = (subFragment1 == null ? new SubFragment1()
					: subFragment1);
			transaction.replace(R.id.id_content, subFragment1);
			// transaction.addToBackStack(null);
			transaction.commit();

		} else if (1 == i) {
			subFragment2 = (subFragment2 == null ? new SubFragment2()
					: subFragment2);
			transaction.replace(R.id.id_content, subFragment2);
			// transaction.addToBackStack(null);
			transaction.commit();
		} else if (2 == i) {
			subFragment3 = (subFragment3 == null ? new SubFragment3()
					: subFragment3);
			transaction.replace(R.id.id_content, subFragment3);
			// transaction.addToBackStack(null);
			transaction.commit();
		}

	}

	/*
	 * 显示subFragment,subFragment不会重复onCreateView
	 */
	public void showSubFragment(int i) {

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		hideSubFragment(transaction);
		if (0 == i) {
			subFragment1 = (subFragment1 == null ? new SubFragment1()
					: subFragment1);
			if(!subFragment1.isAdded()){
				transaction.add(R.id.id_content, subFragment1);
			}
			// transaction.addToBackStack(null);
			transaction.show(subFragment1);
			transaction.commit();

		} else if (1 == i) {
			subFragment2 = (subFragment2 == null ? new SubFragment2()
					: subFragment2);
			if(!subFragment2.isAdded()){
				transaction.add(R.id.id_content, subFragment2);
			}
			// transaction.addToBackStack(null);
			transaction.show(subFragment2);
			transaction.commit();
		} else if (2 == i) {
			subFragment3 = (subFragment3 == null ? new SubFragment3()
					: subFragment3);
			if(!subFragment3.isAdded()){
				transaction.add(R.id.id_content, subFragment3);
			}
			// transaction.addToBackStack(null);
			transaction.show(subFragment3);
			transaction.commit();
		}

	}

	public void hideSubFragment(FragmentTransaction transaction) {

		if (subFragment1 != null) {
			transaction.hide(subFragment1);
		}
		if (subFragment2 != null) {
			transaction.hide(subFragment2);
		}
		if (subFragment3 != null) {
			transaction.hide(subFragment3);
		}

	}

}

