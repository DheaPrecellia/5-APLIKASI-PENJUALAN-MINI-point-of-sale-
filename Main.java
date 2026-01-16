import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) {
            JOptionPane.showMessageDialog(null,
                    "Gagal koneksi ke database!\n" +
                            "Pastikan:\n" +
                            "1. MySQL sudah running\n" +
                            "2. Database 'pos_db' sudah dibuat\n" +
                            "3. Username dan password di DatabaseConnection.java sudah benar",
                    "Error Database",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        ProdukDatabase produkDb = new ProdukDatabase();
        TransaksiDatabase transaksiDb = new TransaksiDatabase(produkDb);

        SwingUtilities.invokeLater(() -> {
            JFrame frameMenu = new JFrame("Point of Sale - Menu Utama");
            frameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frameMenu.setSize(400, 350);

            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            panel.setLayout(new GridLayout(5, 1, 10, 10));

            JButton btnProduk = new JButton("Kelola Produk");
            JButton btnTransaksi = new JButton("Transaksi Penjualan");
            JButton btnLaporanHarian = new JButton("Laporan Harian");
            JButton btnLaporanMingguan = new JButton("Laporan Mingguan");
            JButton btnKeluar = new JButton("Keluar");

            btnProduk.addActionListener(e -> {
                ProdukForm form = new ProdukForm(produkDb);
                form.setVisible(true);
            });

            btnTransaksi.addActionListener(e -> {
                TransaksiForm form = new TransaksiForm(produkDb, transaksiDb);
                form.setVisible(true);
            });

            btnLaporanHarian.addActionListener(e -> {
                LaporanHarian form = new LaporanHarian(transaksiDb);
                form.setVisible(true);
            });

            btnLaporanMingguan.addActionListener(e -> {
                LaporanMingguan form = new LaporanMingguan(transaksiDb);
                form.setVisible(true);
            });

            btnKeluar.addActionListener(e -> {
                DatabaseConnection.closeConnection();
                System.exit(0);
            });

            panel.add(btnProduk);
            panel.add(btnTransaksi);
            panel.add(btnLaporanHarian);
            panel.add(btnLaporanMingguan);
            panel.add(btnKeluar);

            frameMenu.add(panel);
            frameMenu.setLocationRelativeTo(null);
            frameMenu.setVisible(true);
        });
    }
}