package AplikasiPenjualan;

import java.util.ArrayList;
import java.util.Date;

public class Transaksi {
    private int kodeTransaksi;
    private Date tanggalTransaksi;
    private ArrayList<Produk> daftarProduk;
    private ArrayList<Integer> qty;
    private double totalHarga;

    public Transaksi(int kodeTransaksi) {
        this.kodeTransaksi = kodeTransaksi;
        this.tanggalTransaksi = new Date();
        this.daftarProduk = new ArrayList<>();
        this.qty = new ArrayList<>();
        this.totalHarga = 0;
    }

    public void tambahItem(Produk produk, int jumlah) {
        if (produk.kurangiStok(jumlah)) {
            daftarProduk.add(produk);
            qty.add(jumlah);
            totalHarga += produk.getHarga() * jumlah;
        } else {
            System.out.println("Stok produk " + produk.getnamaProduk() + " tidak mencukupi!");
        }
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public Date getTanggalTransaksi() {
        return tanggalTransaksi;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Transaksi #").append(kodeTransaksi).append(" ===\n");
        sb.append("Tanggal : ").append(tanggalTransaksi).append("\n");
        sb.append("Daftar Barang:\n");
        for (int i = 0; i < daftarProduk.size(); i++) {
            sb.append("- ").append(daftarProduk.get(i).getnamaProduk())
              .append(" x ").append(qty.get(i))
              .append(" = ").append(daftarProduk.get(i).getHarga() * qty.get(i))
              .append("\n");
        }
        sb.append("Total Bayar: ").append(totalHarga).append("\n");
        return sb.toString();
    }
}

