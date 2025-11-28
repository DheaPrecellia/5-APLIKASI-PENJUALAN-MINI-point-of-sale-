package AplikasiPenjualan;

import java.util.ArrayList;

public class Laporan {
    private ArrayList<Transaksi> riwayatTransaksi;

    public Laporan() {
        riwayatTransaksi = new ArrayList<>();
    }

    public void tambahTransaksi(Transaksi transaksi) {
        riwayatTransaksi.add(transaksi);
    }

    public void cetakLaporan() {
        System.out.println("\n===== LAPORAN PENJUALAN HARIAN =====");
        double totalPendapatan = 0;

        for (Transaksi t : riwayatTransaksi) {
            System.out.println(t.toString());
            totalPendapatan += t.getTotalHarga();
        }

        System.out.println("------------------------------------");
        System.out.println("Total Pendapatan Hari Ini : " + totalPendapatan);
        System.out.println("====================================\n");
    }
}

