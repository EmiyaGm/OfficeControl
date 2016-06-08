package com.example.OfficeControl;

/**
 * Created by gm on 2016/5/29.
 */
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.example.OfficeControl.fragment.R;
import com.example.OfficeControl.vo.User;
import com.example.OfficeControl.vo.UserInfo;

public class MainActivity extends Activity {

    TextView textFetchPassWord=null,textRegister=null;
    Button loginButton=null;
    ImageButton  listIndicatorButton=null, deleteButtonOfEdit=null;
    ListView loginList=null;
    EditText nameEdit=null, passwordEdit=null;
    private static boolean isVisible=false;         //ListView是否可见
    private static boolean isIndicatorUp=false;     //指示器的方向

    public static int currentSelectedPosition=-1;


    String[] from={"userName","deletButton"};
    int[] to={R.id.login_userName,R.id.login_deleteButton};
    ArrayList<HashMap<String,Object>> list=null;
    private User loginUser;



    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.login);
        textFetchPassWord=(TextView)findViewById(R.id.fetchPassword);
        textRegister=(TextView)findViewById(R.id.regist);
        loginButton=(Button)findViewById(R.id.LoginButton);
        listIndicatorButton=(ImageButton)findViewById(R.id.ListIndicator);
        loginList=(ListView)findViewById(R.id.loginList);
        list=new ArrayList<HashMap<String,Object>>();
        nameEdit=(EditText)findViewById(R.id.Num);
        passwordEdit=(EditText)findViewById(R.id.Password);
        deleteButtonOfEdit=(ImageButton)findViewById(R.id.delete_button_edit);
        nameEdit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(nameEdit.getText().toString().equals("")==false){
                    deleteButtonOfEdit.setVisibility(View.VISIBLE);
                }

            }
        });

        deleteButtonOfEdit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                nameEdit.setText("");
                currentSelectedPosition=-1;
                deleteButtonOfEdit.setVisibility(View.GONE);

            }
        });

        if(currentSelectedPosition==-1){
            nameEdit.setText("");
        }
        else{
            nameEdit.setText((String)list.get(currentSelectedPosition).get(from[0]));
        }

        MyLoginListAdapter adapter=new MyLoginListAdapter(this, list, R.layout.layout_list_item, from, to);
        loginList.setAdapter(adapter);
        loginList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                nameEdit.setText((String)list.get(arg2).get(from[0]));
                currentSelectedPosition=arg2;

                //相应完点击后List就消失，指示箭头反向！
                loginList.setVisibility(View.GONE);
                listIndicatorButton.setBackgroundResource(R.drawable.indicator_down);



            }


        });

        listIndicatorButton.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(isIndicatorUp){
                    isIndicatorUp=false;
                    isVisible=false;
                    listIndicatorButton.setBackgroundResource(R.drawable.indicator_down);
                    loginList.setVisibility(View.GONE);   //让ListView列表消失

                }
                else{
                    isIndicatorUp=true;
                    isVisible=true;
                    listIndicatorButton.setBackgroundResource(R.drawable.indicator_up);
                    loginList.setVisibility(View.VISIBLE);
                }
            }

        });

        loginButton.setOnClickListener(new OnClickListener(){

            @TargetApi(Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                try {
                    String name = new String (nameEdit.getText().toString().getBytes("ISO8859-1"),"UTF-8");
                    String password = new String (passwordEdit.getText().toString().getBytes("ISO8859-1"),"UTF-8");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String userId;
                            if(!(userId=LoginService.login(name,password)).equals("error")){
                                UserInfo user = new UserInfo(name,password,R.drawable.deletebutton);
                                addUser(user);
                                loginUser=(User)getApplication();
                                loginUser.setUsername(name);
                                loginUser.setId(Integer.parseInt(userId));
                                Intent intent=new Intent(MainActivity.this,MainTab.class);
                                startActivity(intent);
                                MainActivity.this.finish();
                            }else {
                                Looper.prepare();
                                Toast.makeText(MainActivity.this,R.string.error,Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        }
                    }).start();

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }

        });

    }



    //继承onTouchEvent方法，用于实现点击控件之外的部分使控件消失的功能

    private void addUser(UserInfo user){
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put(from[0], user.userName);
        map.put(from[1], user.deleteButtonRes);
        list.add(map);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if(event.getAction()==MotionEvent.ACTION_DOWN && isVisible){
            int[] location=new int[2];
            //调用getLocationInWindow方法获得某一控件在窗口中左上角的横纵坐标
            loginList.getLocationInWindow(location);
            //获得在屏幕上点击的点的坐标
            int x=(int)event.getX();
            int y=(int)event.getY();
            if(x<location[0]|| x>location[0]+loginList.getWidth() ||
                    y<location[1]||y>location[1]+loginList.getHeight()){
                isIndicatorUp=false;
                isVisible=false;
                listIndicatorButton.setBackgroundResource(R.drawable.indicator_down);
                loginList.setVisibility(View.GONE);   //让ListView列表消失，并且让游标向下指！

            }
        }
        return super.onTouchEvent(event);
    }





    /**
     * 为了便于在适配器中修改登录界面的Activity，这里把适配器作为
     * MainActivity的内部类，避免了使用Handler，简化代码
     * @author DragonGN
     *
     */



    public class MyLoginListAdapter extends BaseAdapter {

        protected Context context;
        protected ArrayList<HashMap<String, Object>> list;
        protected int itemLayout;
        protected String[] from;
        protected int[] to;

        public MyLoginListAdapter(Context context,
                                  ArrayList<HashMap<String, Object>> list, int itemLayout,
                                  String[] from, int[] to) {
            super();
            this.context = context;
            this.list = list;
            this.itemLayout = itemLayout;
            this.from = from;
            this.to = to;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        class ViewHolder {
            public TextView userName;
            public ImageButton deleteButton;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder holder = null;

			/*
			currentPosition=position;
			不能使用currentPosition，因为每绘制完一个Item就会更新currentPosition
			这样得到的currentPosition将始终是最后一个Item的position
			*/

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(itemLayout, null);
                holder = new ViewHolder();
                holder.userName = (TextView) convertView.findViewById(to[0]);
                holder.deleteButton = (ImageButton) convertView.findViewById(to[1]);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.userName.setText((String) list.get(position).get(from[0]));
            holder.deleteButton.setBackgroundResource((Integer) list.get(position).get(from[1]));
            holder.deleteButton.setOnClickListener(new ListOnClickListener(position));
            return convertView;
        }

        class ListOnClickListener implements OnClickListener {
            private int position;

            public ListOnClickListener(int position) {
                super();
                this.position = position;
            }

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                list.remove(position);
                //如果删除的就是当前显示的账号，那么将主界面当前显示的头像设置回初始头像
                if (position == currentSelectedPosition) {
                    nameEdit.setText("");
                    currentSelectedPosition = -1;
                } else if (position < currentSelectedPosition) {
                    currentSelectedPosition--;    //这里小于当前选择的position时需要进行减1操作
                }

                listIndicatorButton.setBackgroundResource(R.drawable.indicator_down);
                loginList.setVisibility(View.GONE);   //让ListView列表消失，并且让游标向下指！
                MyLoginListAdapter.this.notifyDataSetChanged();
            }

        }


    }

}