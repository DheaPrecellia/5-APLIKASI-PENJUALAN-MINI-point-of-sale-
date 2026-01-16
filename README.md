**Aplikasi Penjualan Mini (_Point of Sales_)**

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-000000?style=for-the-badge&logo=mysql&logoColor=white)

Project ini disusun untuk memenuhi Tugas Akhir Mata Kuliah Pemrograman Berorientasi Objek

## Deskripsi Project

Aplikasi ini adalah sistem kasir sederhana berbasis desktop yang dibuat menggunakan bahasa pemrograman Java. Sistem ini dirancang untuk membantu UMKM atau toko kecil dalam mengelola inventaris barang, mencatat transaksi penjualan secara _real-time_, dan menghasilkan laporan keuangan otomatis.
Penerapan konsep Object-Oriented Programming (OOP) dan integrasi dalam database menjadi fokus utama dalam pengembangan aplikasi ini.

## Fitur Unggulan

Berikut adalah fitur-fitur yang tersedia dalam aplikasi:

### 1. Manajemen Produk
* **CRUD Produk:** Tambah, Edit, Hapus, dan Lihat data barang.
* Pencatatan stok dan harga satuan.

### 2. Transaksi
* Input belanjaan pelanggan dengan cepat.
* Kalkulasi total harga otomatis.
* Penyimpanan riwayat transaksi ke database.

### 3. Laporan & Reporting
* **Laporan Harian:** Rekapitulasi penjualan per hari.
* **Laporan Mingguan:** Rekapitulasi penjualan setiap minggunya.
* Fitur ini memudahkan pemilik toko memantau performa penjualan.

### 4. Integrasi Database
* Menggunakan MySQL untuk penyimpanan data yang aman dan persisten.
* Koneksi menggunakan JDBC Driver.

---

## Screenshots
*(Tampilan antarmuka aplikasi)*

| Menu Utama | Form Transaksi |
| :---: | :---: |
| ![Menu Utama](<img width="781" height="693" alt="image" src="https://github.com/user-attachments/assets/bed3cfe7-ba1f-4a9f-9ba7-9347ac9e0017" />
) | ![Transaksi](<img width="781" height="693" alt="image" src="https://github.com/user-attachments/assets/7f464ea6-a90d-40ea-8b69-fe47d3307e3a" />
) |

---

## Cara Instalasi & Menjalankan

1.  **Clone Repository**
    ```bash
    git clone [https://github.com/DheaPrecellia/5-APLIKASI-PENJUALAN-MINI-point-of-sale-.git](https://github.com/DheaPrecellia/5-APLIKASI-PENJUALAN-MINI-point-of-sale-.git)
    ```
2.  **Import Database**
    * Buat database baru di phpMyAdmin/MySQL dengan nama `db_pos` (sesuaikan dengan kode).
    * Import file SQL (jika ada) atau sesuaikan tabel dengan Entity produk dan transaksi.
3.  **Konfigurasi Database**
    * Buka file `src/DatabaseConnection.java`.
    * Sesuaikan `url`, `user`, dan `password` database lokal Anda.
4.  **Run Application**
    * Buka project di IntelliJ IDEA / NetBeans.
    * Jalankan file `Main.java`.

---

## Identitas Pengembang

Project ini dikembangkan oleh:

* **Nama**:
  a. Gunesti Ririn Wahyuci   (2400018255)
  b. Rahmatika Intan Nugroho (2400018268)
  c. Dhea Precellia          (2400018273)
  d. Hilda Fildhah Izza F    (2400018284)
* **Kelas**: E
* **Program Studi**: Informatika
* **Universitas**: Universitas Ahmad Dahlan

---
Copyright © 2026 - Dilindungi Hak Cipta.
