import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

abstract class Laporan extends JFrame {
    protected TransaksiDatabase db;
    protected JTable table;
    protected DefaultTableModel tableModel;
    protected JLabel lblTotalPendapatan, lblJumlahTransaksi;
    protected JButton btnRefresh, btnCetak;

    public Laporan(TransaksiDatabase db, String title) {
        this.db = db;
        setTitle(title);
        initComponents();
        loadData();
    }

    private void initComponents() {
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

        String[] columns = getColumnNames();
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panelButton = new JPanel();
        btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> loadData());

        btnCetak = new JButton("Cetak Laporan");
        btnCetak.addActionListener(e -> cetakLaporan());

        panelButton.add(btnRefresh);
        panelButton.add(btnCetak);
        add(panelButton, BorderLayout.SOUTH);
    }

    protected abstract String[] getColumnNames();
    protected abstract List<Transaksi> getTransaksiData();
    protected abstract String getPeriodeLaporan();

    protected void loadData() {
        tableModel.setRowCount(0);
        List<Transaksi> transaksiList = getTransaksiData();

        double totalPendapatan = 0;
        for (Transaksi t : transaksiList) {
            tableModel.addRow(createRowData(t));
            totalPendapatan += t.getTotalHarga();
        }

        lblJumlahTransaksi.setText("Jumlah Transaksi: " + transaksiList.size());
        lblTotalPendapatan.setText("Total Pendapatan: Rp " + String.format("%.2f", totalPendapatan));
    }

    protected Object[] createRowData(Transaksi t) {
        return new Object[]{
                t.getId(),
                t.getTanggalFormatted(),
                String.format("Rp %.2f", t.getTotalHarga())
        };
    }

    protected void cetakLaporan() {
        StringBuilder laporan = new StringBuilder();
        laporan.append("=".repeat(50)).append("\n");
        laporan.append("LAPORAN ").append(getPeriodeLaporan().toUpperCase()).append("\n");
        laporan.append("=".repeat(50)).append("\n\n");
        laporan.append("Periode: ").append(getPeriodeLaporan()).append("\n");
        laporan.append("Jumlah Transaksi: ").append(lblJumlahTransaksi.getText().split(": ")[1]).append("\n");
        laporan.append("Total Pendapatan: ").append(lblTotalPendapatan.getText().split(": ")[1]).append("\n");
        laporan.append("\n").append("=".repeat(50)).append("\n");
        laporan.append("DETAIL TRANSAKSI\n");
        laporan.append("=".repeat(50)).append("\n\n");

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                laporan.append(tableModel.getColumnName(j))
                        .append(": ")
                        .append(tableModel.getValueAt(i, j))
                        .append("\n");
            }
            laporan.append("-".repeat(30)).append("\n");
        }

        JTextArea textArea = new JTextArea(laporan.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        JOptionPane.showMessageDialog(this, scrollPane,
                "Preview Laporan " + getPeriodeLaporan(),
                JOptionPane.INFORMATION_MESSAGE);
    }
}