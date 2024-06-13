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

public class PlaceList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PlaceAdapter placeAdapter;
    private TextView headerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placelist);

        headerText = findViewById(R.id.header_text); // Fetch the TextView
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        placeAdapter = new PlaceAdapter();
        recyclerView.setAdapter(placeAdapter);

        double latitude1 = getIntent().getDoubleExtra("latitude1", 0.0);
        double longitude1 = getIntent().getDoubleExtra("longitude1", 0.0);

        if (getIntent().hasExtra("latitude2") && getIntent().hasExtra("longitude2")) {
            double latitude2 = getIntent().getDoubleExtra("latitude2", 0.0);
            double longitude2 = getIntent().getDoubleExtra("longitude2", 0.0);
            double[] midpoint = calculateMidpoint(latitude1, longitude1, latitude2, longitude2);
            updateHeaderText(midpoint[0], midpoint[1]);
            fetchData(midpoint[0], midpoint[1]);
        } else {
            updateHeaderText(latitude1, longitude1);
            fetchData(latitude1, longitude1);
        }
    }

    private void updateHeaderText(double latitude, double longitude) {
        // Here, you can format the text to include the location.
        // For demonstration, I'm just displaying the coordinates.
        String locationText = "Current Location: (" + latitude + ", " + longitude + ")";
        headerText.setText(locationText);
    }

    private double[] calculateMidpoint(double lat1, double lon1, double lat2, double lon2) {
        double midLat = (lat1 + lat2) / 2.0;
        double midLon = (lon1 + lon2) / 2.0;
        return new double[]{midLat, midLon};
    }

    private void fetchData(double latitude, double longitude) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://your-json-api-url.com/") // API url
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlaceApiService service = retrofit.create(PlaceApiService.class);
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
                double latitude1 = getIntent().getDoubleExtra("latitude1", 0.0);
                double longitude1 = getIntent().getDoubleExtra("longitude1", 0.0);
                double latitude2 = getIntent().getDoubleExtra("latitude2", 0.0);
                double longitude2 = getIntent().getDoubleExtra("longitude2", 0.0);
                double[] midpoint = calculateMidpoint(latitude1, longitude1, latitude2, longitude2);
                Log.e("Error", "Failed to fetch data for midpoint latitude: " + midpoint[0] + ", longitude: " + midpoint[1] + "\n" + t.getMessage());

                // API 호출에 실패한 경우 기본값 표시
                List<Post> defaultData = new ArrayList<>();
                defaultData.add(new Post("천하일미", "일식", "031-756-6292", "경기 성남시 중원구 광명로 15 1층 천하일미", "https://huni1045.modoo.at/"));
                defaultData.add(new Post("우라멘 모란본점", "일본식라면", "0507-1374-0420", "경기 성남시 중원구 광명로 4 105호", "https://www.instagram.com/woo__ramen"));
                defaultData.add(new Post("멀멀", "요리주점", "0507-1476-3111", "경기 성남시 중원구 둔촌대로101번길 16-18 2층", "https://www.instagram.com/murmur_____baaaar"));
                defaultData.add(new Post("부산의밤", "한식", "0507-1355-6258", "경기 성남시 중원구 제일로63번길 27 1층", "https://www.instagram.com/busanbaminatanadda"));
                defaultData.add(new Post("사람사는이야기 모란점", "이자카야", "0507-1371-0442", "경기 성남시 중원구 제일로35번길 55 2층", "http://instagram.com/sasai_moran"));
                defaultData.add(new Post("목구멍 모란점", "육류", "0507-1459-9395", "경기 성남시 중원구 성남대로1140번길 9", "http://example.com"));
                placeAdapter.setPlaceList(defaultData);
            }
        });
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
