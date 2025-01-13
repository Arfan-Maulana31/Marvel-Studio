package com.universitaskuningan.yamaha_dealer_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.universitaskuningan.yamaha_dealer_app.databinding.ActivityMainBinding;
import com.universitaskuningan.yamaha_dealer_app.databinding.ListItemBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private ArrayList<ItemModel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the ViewBinding object
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupBottomNavigation();
        // Inisialisasi RecyclerView menggunakan ViewBinding
        binding.recyclerView.setHasFixedSize(true);

        // Mengatur LayoutManager
        recyclerViewLayoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(recyclerViewLayoutManager);

        // Menambahkan garis pembatas
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // Mengisi data
        data = new ArrayList<>();
        for (int i = 0; i < Myitem.motorTypes.length; i++) {
            // Mengambil nama motor dari string resource
            String motorName = Myitem.motorTypes[i];
            int motorImage = Myitem.motorImages[i];

            // Menambahkan motor dan deskripsinya
            data.add(new ItemModel(motorName, motorImage));
        }

        // Inisialisasi Adapter
        adapter = new AdapterRecycleView(data);
        binding.recyclerView.setAdapter(adapter);


    }



    // Tambahkan metode untuk memfilter data
// Logika untuk memfilter data berdasarkan input pencarian
    private void filterData(String query) {
        ArrayList<ItemModel> filteredList = new ArrayList<>();

        // Periksa setiap item apakah cocok dengan query
        for (ItemModel item : data) {
            if (item.getMotorType().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }

        // Update RecyclerView dengan hasil pencarian
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "Tidak ada hasil ditemukan", Toast.LENGTH_SHORT).show();
        }

        // Set data baru ke adapter dan refresh RecyclerView
        adapter = new AdapterRecycleView(filteredList);
        binding.recyclerView.setAdapter(adapter);
    }


    @SuppressLint("NonConstantResourceId")

    private void setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_profile) {
                // Buka ProfileActivity
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                return true;
            }
            return false;
        });
    }


    // Menambahkan logika untuk mengirimkan data ke activity_detail saat item di RecyclerView diklik
    private class AdapterRecycleView extends RecyclerView.Adapter<ViewHolder> {
        private ArrayList<ItemModel> itemModels;

        public AdapterRecycleView(ArrayList<ItemModel> itemModels) {
            this.itemModels = itemModels;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ListItemBinding binding = ListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ItemModel item = itemModels.get(position);
            holder.bind(item, currentItem -> {
                // Mengirimkan data motor ke activity_detail
                Intent intent = new Intent(holder.itemView.getContext(), activity_detail.class);
                intent.putExtra("MOTOR_NAME", currentItem.getMotorType());
                intent.putExtra("MOTOR_IMAGE", currentItem.getMotorImage());
                intent.putExtra("MOTOR_DESCRIPTION", getMotorDescription(currentItem.getMotorType())); // Deskripsi motor
                intent.putExtra("MOTOR_SPECIFICATIONS", getMotorSpecifications(currentItem.getMotorType())); // Spesifikasi motor
                holder.itemView.getContext().startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return itemModels.size();
        }

        // Mendapatkan deskripsi motor
        private String getMotorDescription(String motorType) {
            switch (motorType) {
                case "Avengers: Endgame (2019)":
                    return "Kesimpulan dari Infinity Saga yang membawa akhir emosional bagi beberapa karakter ikonik MCU.";
                case "Avengers: Infinity War (2018)":
                    return "Pertarungan epik Avengers melawan Thanos untuk mencegah kehancuran alam semesta.";
                case "Black Panther (2018)":
                    return "Film pahlawan Afrika pertama di MCU, dengan tema budaya dan tradisi.";
                case "Captain America: The Winter Soldier (2014)":
                    return "Film ini menggabungkan aksi superhero dengan elemen thriller politik.";
                case "Guardians of the Galaxy (2014)":
                    return "Film yang membawa elemen komedi dan petualangan luar angkasa ke MCU.";

                default:
                    return "Deskripsi tidak tersedia.";
            }
        }

        // Mendapatkan spesifikasi motor
        private String getMotorSpecifications(String motorType) {
            switch (motorType) {
                case "Avengers: Endgame (2019)":
                    return "Setelah kekalahan di Infinity War, para Avengers yang tersisa bekerja sama untuk membalikkan efek jentikan Thanos. Mereka melakukan perjalanan waktu untuk mengumpulkan Infinity Stones dan menghidupkan kembali mereka yang hilang.";
                case "Avengers: Infinity War (2018)":
                    return "Thanos mengumpulkan Infinity Stones untuk menggunakan kekuatan mereka dan melenyapkan setengah populasi alam semesta. Para Avengers dan Guardians of the Galaxy bersatu untuk menghentikannya, tetapi rencana mereka menemui rintangan besar.";
                case "Black Panther (2018)":
                    return "Setelah kematian ayahnya, T'Challa kembali ke Wakanda untuk mengambil alih takhta sebagai raja dan Black Panther. Namun, ia harus menghadapi Erik Killmonger, musuh kuat yang ingin merebut kekuasaan dan membawa perubahan drastis ke Wakanda.";
                case "Captain America: The Winter Soldier (2014)":
                    return "Steve Rogers alias Captain America bekerja dengan Black Widow dan Falcon untuk mengungkap konspirasi dalam S.H.I.E.L.D. Mereka menghadapi musuh misterius yang dikenal sebagai Winter Soldier, yang ternyata adalah sahabat lamanya, Bucky Barnes.";
                case "Guardians of the Galaxy (2014)":
                    return "Petualangan Peter Quill (Star-Lord) bersama kelompok makhluk luar angkasa yang unik: Gamora, Drax, Rocket Raccoon, dan Groot. Mereka bergabung untuk melindungi Orb, artefak kuat yang diincar Ronan the Accuser.";
                default:
                    return "Spesifikasi tidak tersedia.";
            }
        }
    }

    // ViewHolder sebagai kelas terpisah
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ListItemBinding binding;

        public ViewHolder(@NonNull ListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ItemModel item, ViewHolderClickListener clickListener) {
            binding.motorType.setText(item.getMotorType());
            binding.motorImage.setImageResource(item.getMotorImage());

            // Klik untuk membuka activity_detail
            binding.getRoot().setOnClickListener(v -> clickListener.onClick(item));
        }
    }

    // Listener untuk menangani klik item
    public interface ViewHolderClickListener {
        void onClick(ItemModel item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;  // Prevent memory leaks
    }
}
