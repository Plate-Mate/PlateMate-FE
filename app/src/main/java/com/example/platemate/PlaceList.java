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
import retrofit2.http.Body;
import retrofit2.http.POST;

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

        fetchData(37.5606326, 126.9433486); // 예시 경도 및 위도

    }

    private void fetchData(double latitude, double longitude) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://your-json-api-url.com/") // API url
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApiService service = retrofit.create(RetrofitApiService.class);
        LocationRequest locationRequest = new LocationRequest(latitude, longitude);
        Call<PlaceResponse> call = service.getNearbyPlaces(locationRequest);

        call.enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(@NonNull Call<PlaceResponse> call, @NonNull Response<PlaceResponse> response) {
                if (response.isSuccessful()) {
                    PlaceResponse placeResponse = response.body();
                    if (placeResponse != null) {
                        placeAdapter.setPlaceList(placeResponse.getDocuments());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<PlaceResponse> call, @NonNull Throwable t) {
                Log.e("Error", "Failed to fetch data: " + t.getMessage());
                double latitude = getIntent().getDoubleExtra("latitude", 0.0); // Assuming you're passing the latitude as an extra
                double longitude = getIntent().getDoubleExtra("longitude", 0.0); // Assuming you're passing the longitude as an extra
                Log.e("Error", "Failed to fetch data for latitude: " + latitude + ", longitude: " + longitude + "\n" + t.getMessage());
                // API 호출에 실패한 경우 기본값 표시
                List<Post> defaultData = new ArrayList<>();
                defaultData.add(new Post("Default Name", "Default Address", "Default Info","Default Address","http://example.com"));
                placeAdapter.setPlaceList(defaultData);
            }
        });
    }

    public interface RetrofitApiService {
        @POST("/your-endpoint") // Replace "/your-endpoint" with the actual API endpoint
        Call<PlaceResponse> getNearbyPlaces(@Body LocationRequest locationRequest);
    }

    public static class LocationRequest {
        private double latitude;
        private double longitude;

        public LocationRequest(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

    public static class PlaceResponse {
        private List<Post> documents;

        public List<Post> getDocuments() {
            return documents;
        }

        public void setDocuments(List<Post> documents) {
            this.documents = documents;
        }
    }

    public static class Post {

        private String place_name;
        private String category_name;
        private String phone;
        private String road_address_name;
        private String place_url;

        // Constructor (optional)
        public Post(String place_name, String category_name, String phone, String road_address_name, String place_url) {
            this.place_name = place_name;
            this.category_name = category_name;
            this.phone = phone;
            this.road_address_name = road_address_name;
            this.place_url = place_url;
        }

        // Getters and Setters
        public String getPlace_name() {
            return place_name;
        }

        public void setPlace_name(String place_name) {
            this.place_name = place_name;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRoad_address_name() {
            return road_address_name;
        }

        public void setRoad_address_name(String road_address_name) {
            this.road_address_name = road_address_name;
        }

        public String getPlace_url() {
            return place_url;
        }

        public void setPlace_url(String place_url) {
            this.place_url = place_url;
        }

        // a method to format the display of the post information
        public String getFormattedInfo() {
            return place_name + "\n" + category_name + "\n" + phone + "\n" + road_address_name + "\n" + place_url;
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
                holder.placeName.setText(place.getPlace_name());
                holder.placeCategory.setText(place.getCategory_name());
                holder.placePhone.setText(place.getPhone());
                holder.placeAddress.setText(place.getRoad_address_name());
                holder.placeUrl.setText(place.getPlace_url());
            } else {
                // Handle the case where place is null (e.g., set default text)
                Log.w("PlaceAdapter", "Place data is null at position: " + position);
                holder.placeName.setText("Place information not available");
                // You can set similar defaults for other fields
            }
        }

        @Override
        public int getItemCount() {
            return placeList.size();
        }

        static class PlaceViewHolder extends RecyclerView.ViewHolder {

            TextView placeName;
            TextView placeCategory;
            TextView placePhone;
            TextView placeAddress;
            TextView placeUrl;

            public PlaceViewHolder(@NonNull View itemView) {
                super(itemView);
                placeName = itemView.findViewById(R.id.place_name);
                placeCategory = itemView.findViewById(R.id.place_category);
                placePhone = itemView.findViewById(R.id.place_phone);
                placeAddress = itemView.findViewById(R.id.place_address);
                placeUrl = itemView.findViewById(R.id.place_url);
            }
        }
    }
}
