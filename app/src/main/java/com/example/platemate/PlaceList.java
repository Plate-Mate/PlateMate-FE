package com.example.platemate;

import android.graphics.Typeface;
import android.os.Bundle;
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

public class PlaceList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placelist);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Place> placeList = new ArrayList<>();
        placeList.add(new Place("음식점1", "주소", "기타 정보"));
        placeList.add(new Place("음식점2", "주소", "기타 정보"));
        placeList.add(new Place("음식점3", "주소", "기타 정보"));
        placeList.add(new Place("음식점1", "주소", "기타 정보"));
        placeList.add(new Place("음식점2", "주소", "기타 정보"));
        placeList.add(new Place("음식점3", "주소", "기타 정보"));

        PlaceAdapter placeAdapter = new PlaceAdapter(placeList);
        recyclerView.setAdapter(placeAdapter);
    }

    static class Place {
        private String name;
        private String address;
        private String info;

        public Place(String name, String address, String info) {
            this.name = name;
            this.address = address;
            this.info = info;
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

        public String getInfo() {
            return info;
        }
    }

    static class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

        private List<Place> placeList;

        public PlaceAdapter(List<Place> placeList) {
            this.placeList = placeList;
        }

        @NonNull
        @Override
        public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false);
            return new PlaceViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
            Place place = placeList.get(position);
            holder.placeName.setText(place.getName());
            holder.placeAddress.setText(place.getAddress());
            holder.placeInfo.setText(place.getInfo());
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
