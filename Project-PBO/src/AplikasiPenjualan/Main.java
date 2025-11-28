package AplikasiPenjualan;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Produk> daftarProduk = new ArrayList<>();
        Laporan laporan = new Laporan();
        int kodeProduk = 1;
        int kodeTransaksi = 1;
        int pilih;

        do {
            System.out.println("\n=== APLIKASI PENJUALAN MINI ===");
            System.out.println("1. Tambah Produk");
            if (!daftarProduk.isEmpty()) {
                System.out.println("2. Input Transaksi");
                System.out.println("3. Lihat Laporan Harian");
                System.out.println("4. Lihat Daftar Produk");
                System.out.println("5. Keluar");
            } else {
                System.out.println("2. Keluar");
            }
            System.out.print("Pilih Menu: ");
            pilih = input.nextInt();

            if (daftarProduk.isEmpty()) {
                switch (pilih) {
                    case 1:
                        tambahProduk(input, daftarProduk, kodeProduk++);
                        break;
                    case 2:
                        System.out.println("Terima kasih!");
                        pilih = 5;
                        break;
                    default:
                        System.out.println("Menu tidak tersedia!");
                }
            } else {
                switch (pilih) {
                    case 1:
                        tambahProduk(input, daftarProduk, kodeProduk++);
                        break;

                    case 2:
                        inputTransaksi(input, daftarProduk, laporan, kodeTransaksi++);
                        break;

                    case 3:
                        laporan.cetakLaporan();
                        break;

                    case 4:
                        tampilProduk(daftarProduk);
                        break;

                    case 5:
                        System.out.println("Terima kasih telah menggunakan aplikasi!");
                        break;

                    default:
                        System.out.println("Menu tidak tersedia!");
                }
            }
        } while (pilih != 5);
    }

    public static void tambahProduk(Scanner input, ArrayList<Produk> daftarProduk, int kode) {
        input.nextLine();
        System.out.print("Nama Produk  : ");
        String nama = input.nextLine();
        System.out.print("Harga Produk : ");
        double harga = input.nextDouble();
        System.out.print("Jumlah Stok  : ");
        int stok = input.nextInt();

        daftarProduk.add(new Produk(kode, nama, harga, stok));
        System.out.println("Produk berhasil ditambahkan!");
    }

    public static void inputTransaksi(Scanner input, ArrayList<Produk> daftarProduk, Laporan laporan, int kodeTransaksi) {
        Transaksi transaksi = new Transaksi(kodeTransaksi);
        String lagi;

        do {
            System.out.println("\n=== DAFTAR PRODUK ===");
            for (Produk p : daftarProduk) {
                System.out.println(p.getkodeProduk() + ". " + p.getnamaProduk() +
                        " | Harga: " + p.getHarga() +
                        " | Stok: " + p.getjmlProduk());
            }

            System.out.print("Masukkan kode produk: ");
            int kode = input.nextInt();
            Produk produkDipilih = null;

            for (Produk p : daftarProduk) {
                if (p.getkodeProduk() == kode) {
                    produkDipilih = p;
                    break;
                }
            }

            if (produkDipilih == null) {
                System.out.println("Produk tidak ditemukan!");
                continue;
            }

            System.out.print("Masukkan jumlah beli: ");
            int jumlah = input.nextInt();
            transaksi.tambahItem(produkDipilih, jumlah);

            System.out.print("Tambah transaksi lagi? (y/n): ");
                lagi = input.next();
        } while (lagi.equalsIgnoreCase);

        System.out.println("\n" + transaksi.toString());
        laporan.tambahTransaksi(transaksi);
    }

    public static void tampilProduk(ArrayList<Produk> daftarProduk) {
        System.out.println("\n=== DAFTAR PRODUK ===");
        for (Produk p : daftarProduk) {
            System.out.println(p);
        }
    }
}
