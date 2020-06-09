package com.zhuangxueyan.imitationwechatui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PagerFragment extends Fragment {
    private int flag = 0;
    private String imgUrl = "https://api.uomg.com/api/rand.avatar?sort=%E5%8A%A8%E6%BC%AB%E5%A5%B3&format=json";
    private String url = "https://api.sc2h.cn/api/yiyan.php?data=0";
    private List<String> msgs = new ArrayList<>();
    private ListViewAdapter adapter = new ListViewAdapter();
    private List<Bitmap> bitmaps = new ArrayList<>();
    private List<String> names = new ArrayList<>();



    public static PagerFragment getInstance(int flag){
        PagerFragment fragment = new PagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("FLAG",flag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle bundle = getArguments();
        this.flag = bundle.getInt("FLAG");

        Collections.addAll(names,"朵库萝","赫萝","高町奈叶","泉此方","奈落","基拉","1","2","3","4","5","6");

        for (int i = 0; i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    try {
                        Response response = client.newCall(request).execute();
                        String msg = null;
                        msg = response.body().string();
//                        Log.e("test", "run: " + msg );
                        msgs.add(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });

                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(imgUrl)
                            .build();
                    try {
                        Response response = client.newCall(request).execute();
                        String imgJson = response.body().string();
                        JSONObject jsonObject = new JSONObject(imgJson);
                        String Rurl = jsonObject.getString("imgurl");
//                        Log.e("test", "img: "+Rurl);

                        Bitmap bitmap = null;
                        URL url1 = new URL(Rurl);
                        HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
                        conn.setConnectTimeout(3000);
                        conn.setUseCaches(false);
                        conn.setDoInput(true);
                        conn.connect();
                        InputStream stream = conn.getInputStream();
                        bitmap = BitmapFactory.decodeStream(stream);
                        bitmaps.add(bitmap);

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }


                }

            }).start();
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });





    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        switch (flag){
            case 0:
                view = inflater.inflate(R.layout.chat,container,false);
                initFlag1(view);
                break;
            case 1:
                view = inflater.inflate(R.layout.address_book,container,false);
                break;
            case 2:
                view = inflater.inflate(R.layout.find,container,false);
                break;
            case 3:
                view = inflater.inflate(R.layout.me,container,false);
                break;
        }
        return view;
    }

    private void initFlag1(View view){
        ListView listView = view.findViewById(R.id.chat_list);
        listView.setAdapter(adapter);
    }

    class ListViewAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return msgs.size();
        }

        @Override
        public Object getItem(int position) {
            return msgs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null){
                View view;
                convertView = getLayoutInflater().inflate(R.layout.chat_item,parent,false);
                view = convertView;
                viewHolder = new ViewHolder();
                viewHolder.msg = view.findViewById(R.id.chat_user_msg);
                viewHolder.imageView = view.findViewById(R.id.chat_user_img);
                viewHolder.name = view.findViewById(R.id.chat_user_name);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.msg.setText(msgs.get(position));
            viewHolder.imageView.setImageBitmap(bitmaps.get(position));
            viewHolder.name.setText(names.get(position));
            return convertView;
        }
    }
    class ViewHolder{
        TextView msg;
        ImageView imageView;
        TextView name;
    }
}
