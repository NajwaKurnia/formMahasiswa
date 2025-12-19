package com.najwa.myapplication

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.najwa.myapplication.model.Mahasiswa
import com.najwa.myapplication.model.adapter.MahasiswaAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var etNim: EditText
    private lateinit var etNama: EditText
    private lateinit var btnSimpan: Button
    private lateinit var rvMahasiswa: RecyclerView

    private val db = FirebaseFirestore.getInstance()
    private val listMahasiswa = ArrayList<Mahasiswa>()
    private lateinit var adapter: MahasiswaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNim = findViewById(R.id.etNim)
        etNama = findViewById(R.id.etNama)
        btnSimpan = findViewById(R.id.btnSimpan)
        rvMahasiswa = findViewById(R.id.rvMahasiswa)

        adapter = MahasiswaAdapter(listMahasiswa)
        rvMahasiswa.layoutManager = LinearLayoutManager(this)
        rvMahasiswa.adapter = adapter

        btnSimpan.setOnClickListener {
            simpanData()
        }

        tampilkanData()
    }

    private fun simpanData() {
        val nim = etNim.text.toString()
        val nama = etNama.text.toString()

        if (nim.isEmpty() || nama.isEmpty()) {
            Toast.makeText(this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        }

        val mahasiswa = Mahasiswa(nim, nama)

        db.collection("mahasiswa")
            .add(mahasiswa)
            .addOnSuccessListener {
                Toast.makeText(this, "Data tersimpan", Toast.LENGTH_SHORT).show()
                etNim.text.clear()
                etNama.text.clear()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal simpan data: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun tampilkanData() {
        db.collection("mahasiswa")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    listMahasiswa.clear()
                    for (doc in snapshot.documents) {
                        val mhs = doc.toObject(Mahasiswa::class.java)
                        if (mhs != null) {
                            listMahasiswa.add(mhs)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
            }
    }
}
