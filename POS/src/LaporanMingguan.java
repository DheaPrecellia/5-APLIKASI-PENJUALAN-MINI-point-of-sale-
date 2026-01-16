import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class LaporanMingguan extends Laporan {

    public LaporanMingguan(TransaksiDatabase db) {
        super(db, "Laporan Mingguan");
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"ID", "Tanggal", "Total", "Hari"};
    }

    @Override
    protected List<Transaksi> getTransaksiData() {
        return db.getTransaksiMingguIni();
    }

    @Override
    protected String getPeriodeLaporan() {
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.minusDays(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return weekStart.format(formatter) + " s/d " + today.format(formatter);
    }

    // Override method createRowData untuk menambahkan kolom hari
    @Override
    protected Object[] createRowData(Transaksi t) {
        String hari = getDayName(t.getTanggal().toLocalDate());
        return new Object[]{
                t.getId(),
                t.getTanggalFormatted(),
                String.format("Rp %.2f", t.getTotalHarga()),
                hari
        };
    }

    // Override method loadData untuk menambahkan statistik per hari
    @Override
    protected void loadData() {
        super.loadData();

        // Tambahan: Hitung rata-rata per hari
        List<Transaksi> transaksiList = getTransaksiData();
        if (!transaksiList.isEmpty()) {
            Map<String, Double> pendapatanPerHari = new HashMap<>();
            Map<String, Integer> transaksiPerHari = new HashMap<>();

            for (Transaksi t : transaksiList) {
                String hari = getDayName(t.getTanggal().toLocalDate());
                pendapatanPerHari.put(hari,
                        pendapatanPerHari.getOrDefault(hari, 0.0) + t.getTotalHarga());
                transaksiPerHari.put(hari,
                        transaksiPerHari.getOrDefault(hari, 0) + 1);
            }

            double totalPendapatan = transaksiList.stream()
                    .mapToDouble(Transaksi::getTotalHarga)
                    .sum();

            double rataRataPerHari = totalPendapatan / 7;

            System.out.println("Rata-rata pendapatan per hari: Rp " +
                    String.format("%.2f", rataRataPerHari));

            // Update label dengan info tambahan
            lblTotalPendapatan.setText(
                    String.format("Total Pendapatan: Rp %.2f (Avg/hari: Rp %.2f)",
                            totalPendapatan, rataRataPerHari)
            );
        }
    }

    // Override method cetakLaporan untuk format khusus mingguan
    @Override
    protected void cetakLaporan() {
        StringBuilder laporan = new StringBuilder();
        laporan.append("=".repeat(60)).append("\n");
        laporan.append("LAPORAN MINGGUAN\n");
        laporan.append("=".repeat(60)).append("\n\n");
        laporan.append("Periode: ").append(getPeriodeLaporan()).append("\n");
        laporan.append(lblJumlahTransaksi.getText()).append("\n");
        laporan.append(lblTotalPendapatan.getText()).append("\n");

        // Tambahkan statistik per hari
        List<Transaksi> transaksiList = getTransaksiData();
        Map<String, Double> pendapatanPerHari = new HashMap<>();
        Map<String, Integer> transaksiPerHari = new HashMap<>();

        for (Transaksi t : transaksiList) {
            String hari = getDayName(t.getTanggal().toLocalDate());
            pendapatanPerHari.put(hari,
                    pendapatanPerHari.getOrDefault(hari, 0.0) + t.getTotalHarga());
            transaksiPerHari.put(hari,
                    transaksiPerHari.getOrDefault(hari, 0) + 1);
        }

        laporan.append("\n").append("=".repeat(60)).append("\n");
        laporan.append("RINGKASAN PER HARI\n");
        laporan.append("=".repeat(60)).append("\n\n");

        String[] hariUrutan = {"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"};
        for (String hari : hariUrutan) {
            double pendapatan = pendapatanPerHari.getOrDefault(hari, 0.0);
            int jumlah = transaksiPerHari.getOrDefault(hari, 0);
            laporan.append(String.format("%-10s: %d transaksi - Rp %.2f\n",
                    hari, jumlah, pendapatan));
        }

        laporan.append("\n").append("=".repeat(60)).append("\n");
        laporan.append("DETAIL TRANSAKSI\n");
        laporan.append("=".repeat(60)).append("\n\n");

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                laporan.append(tableModel.getColumnName(j))
                        .append(": ")
                        .append(tableModel.getValueAt(i, j))
                        .append("\n");
            }
            laporan.append("-".repeat(40)).append("\n");
        }

        javax.swing.JTextArea textArea = new javax.swing.JTextArea(laporan.toString());
        textArea.setEditable(false);
        textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));

        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(600, 500));

        javax.swing.JOptionPane.showMessageDialog(this, scrollPane,
                "Preview Laporan Mingguan",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    // Helper method untuk mendapatkan nama hari dalam bahasa Indonesia
    private String getDayName(LocalDate date) {
        String[] hariIndo = {"Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
        return hariIndo[date.getDayOfWeek().getValue() % 7];
    }
}