package com.example.platemate;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class PlaceList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PlaceAdapter placeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placelist);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        placeAdapter = new PlaceAdapter();
        recyclerView.setAdapter(placeAdapter);

        fetchData();
    }

    private void fetchData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://your-json-api-url.com/") // JSON API의 URL을 넣으세요
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApiService service = retrofit.create(RetrofitApiService.class);
        Call<List<Post>> call = service.getData("your-parameter"); // 파라미터에 따라 요청할 데이터를 넣으세요

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    List<Post> data = response.body();
                    if (data != null) {
                        placeAdapter.setPlaceList(data);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
                Log.e("Error", "Failed to fetch data: " + t.getMessage());
                // API 호출에 실패한 경우 기본값 표시
                List<Post> defaultData = new ArrayList<>();
                defaultData.add(new Post("Default Name", "Default Address", "Default Info"));
                placeAdapter.setPlaceList(defaultData);
            }
        });
    }
    public interface RetrofitApiService {
        @GET("/your-endpoint") // Replace "/your-endpoint" with the actual API endpoint
        Call<List<Post>> getData(@Query("your-parameter") String parameter); // Replace "your-parameter" with the actual parameter name
    }

    public class Post {

        private String name;
        private String address;
        private String info; // Additional details about the place

        // Getters for name, address, and info
        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

        public String getInfo() {
            return info;
        }

        // Setters for name, address, and info (optional)
        public void setName(String name) {
            this.name = name;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        // Constructor (optional)
        public Post(String name, String address, String info) {
            this.name = name;
            this.address = address;
            this.info = info;
        }

        // Additional methods (optional)
        // For example, a method to format the display of the post information
        public String getFormattedInfo() {
            return name + "\n" + address + "\n" + info;
        }
    }


    static class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

        private List<Post> placeList;

        public PlaceAdapter() {
            placeList = new ArrayList<>();
        }

        public void setPlaceList(List<Post> placeList) {
            this.placeList = placeList;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false);
            return new PlaceViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
            Post place = placeList.get(position);

            // Check if place is not null before accessing its properties
            if (place != null) {
                holder.placeName.setText(place.getName());
                holder.placeAddress.setText(place.getAddress());
                holder.placeInfo.setText(place.getInfo());
            } else {
                // Handle the case where place is null (e.g., set default text)
                Log.w("PlaceAdapter", "Place data is null at position: " + position);
                holder.placeName.setText("Place information not available");
                // You can set similar defaults for address and info
            }
        }


        @Override
        public int getItemCount() {
            return placeList.size();
        }

        static class PlaceViewHolder extends RecyclerView.ViewHolder {

            TextView placeName;
            TextView placeAddress;
            TextView placeInfo;

            public PlaceViewHolder(@NonNull View itemView) {
                super(itemView);
                placeName = itemView.findViewById(R.id.place_name);
                placeAddress = itemView.findViewById(R.id.place_address);
                placeInfo = itemView.findViewById(R.id.place_info);
            }
        }
    }
}
