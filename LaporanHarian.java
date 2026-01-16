import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

class LaporanHarian extends JFrame {
    private TransaksiDatabase db;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblTotalPendapatan, lblJumlahTransaksi;

    public LaporanHarian(TransaksiDatabase db) {
        this.db = db;
        initComponents();
        loadData();
    }

    private void initComponents() {
        setTitle("Laporan Harian");
        setSize(700, 500);
        setLayout(new BorderLayout(10, 10));

        JPanel panelInfo = new JPanel(new GridLayout(2, 1, 5, 5));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblJumlahTransaksi = new JLabel("Jumlah Transaksi: 0");
        lblJumlahTransaksi.setFont(new Font("Arial", Font.BOLD, 14));

        lblTotalPendapatan = new JLabel("Total Pendapatan: Rp 0");
        lblTotalPendapatan.setFont(new Font("Arial", Font.BOLD, 14));

        panelInfo.add(lblJumlahTransaksi);
        panelInfo.add(lblTotalPendapatan);

        add(panelInfo, BorderLayout.NORTH);

        String[] columns = {"ID", "Tanggal", "Total"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> loadData());
        JPanel panelButton = new JPanel();
        panelButton.add(btnRefresh);
        add(panelButton, BorderLayout.SOUTH);
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Transaksi> transaksiHariIni = db.getTransaksiHariIni();

        double totalPendapatan = 0;
        for (Transaksi t : transaksiHariIni) {
            tableModel.addRow(new Object[]{
                    t.getId(),
                    t.getTanggalFormatted(),
                    t.getTotalHarga()
            });
            totalPendapatan += t.getTotalHarga();
        }

        lblJumlahTransaksi.setText("Jumlah Transaksi: " + transaksiHariIni.size());
        lblTotalPendapatan.setText("Total Pendapatan: Rp " + totalPendapatan);
    }
}