package com.example.restoapp.features;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.restoapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {
//    private DatabaseReference database;
//    private RecyclerView rvView;
//    private RecyclerView.Adapter adapter;
//    private RecyclerView.LayoutManager layoutManager;
//    private ArrayList<Barang> daftarBarang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

//        rvView = (RecyclerView) findViewById(R.id.rcyMenu);
//        rvView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);
//        rvView.setLayoutManager(layoutManager);
//
//        /**
//         * Inisialisasi dan mengambil Firebase Database Reference
//         */
//        database = FirebaseDatabase.getInstance().getReference();
//        database.child("barang").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                /**
//                 * Saat ada data baru, masukkan datanya ke ArrayList
//                 */
//                daftarBarang = new ArrayList<>();
//                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
//                    /**
//                     * Mapping data pada DataSnapshot ke dalam object Barang
//                     * Dan juga menyimpan primary key pada object Barang
//                     * untuk keperluan Edit dan Delete data
//                     */
//                    Barang barang = noteDataSnapshot.getValue(Barang.class);
//                    barang.setKey(noteDataSnapshot.getKey());
//
//                    /**
//                     * Menambahkan object Barang yang sudah dimapping
//                     * ke dalam ArrayList
//                     */
//                    daftarBarang.add(barang);
//                }
//
//                /**
//                 * Inisialisasi adapter dan data barang dalam bentuk ArrayList
//                 * dan mengeset Adapter ke dalam RecyclerView
//                 */
//                adapter = new AdapterBarangRecyclerView(daftarBarang, FirebaseDBReadActivity.this);
//                rvView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                /**
//                 * Kode ini akan dipanggil ketika ada error dan
//                 * pengambilan data gagal dan memprint error nya
//                 * ke LogCat
//                 */
//                System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
//            }
//        });

    }
}