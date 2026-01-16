import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

class ProdukForm extends JFrame {
    private ProdukDatabase db;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField tfNama, tfHarga, tfStok;
    private JButton btnTambah, btnUpdate, btnHapus, btnClear, btnRefresh;
    private int selectedId = -1;

    public ProdukForm(ProdukDatabase db) {
        this.db = db;
        initComponents();
        loadData();
    }

    private void initComponents() {
        setTitle("Kelola Produk");
        setSize(700, 500);
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(4, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelForm.add(new JLabel("Nama Produk:"));
        tfNama = new JTextField();
        panelForm.add(tfNama);

        panelForm.add(new JLabel("Harga:"));
        tfHarga = new JTextField();
        panelForm.add(tfHarga);

        panelForm.add(new JLabel("Stok:"));
        tfStok = new JTextField();
        panelForm.add(tfStok);

        JPanel panelButton = new JPanel(new FlowLayout());
        btnTambah = new JButton("Tambah");
        btnUpdate = new JButton("Update");
        btnHapus = new JButton("Hapus");
        btnClear = new JButton("Clear");
        btnRefresh = new JButton("Refresh");

        panelButton.add(btnTambah);
        panelButton.add(btnUpdate);
        panelButton.add(btnHapus);
        panelButton.add(btnClear);
        panelButton.add(btnRefresh);
        panelForm.add(panelButton);

        add(panelForm, BorderLayout.NORTH);

        String[] columns = {"ID", "Nama", "Harga", "Stok"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        btnTambah.addActionListener(e -> tambahProduk());
        btnUpdate.addActionListener(e -> updateProduk());
        btnHapus.addActionListener(e -> hapusProduk());
        btnClear.addActionListener(e -> clearForm());
        btnRefresh.addActionListener(e -> loadData());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                pilihProduk();
            }
        });
    }

    private void tambahProduk() {
        try {
            String nama = tfNama.getText();
            double harga = Double.parseDouble(tfHarga.getText());
            int stok = Integer.parseInt(tfStok.getText());

            Produk produk = new Produk(0, nama, harga, stok);
            db.tambah(produk);

            JOptionPane.showMessageDialog(this, "Produk berhasil ditambahkan!");
            clearForm();
            loadData();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Input tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateProduk() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih produk terlebih dahulu!");
            return;
        }

        try {
            String nama = tfNama.getText();
            double harga = Double.parseDouble(tfHarga.getText());
            int stok = Integer.parseInt(tfStok.getText());

            Produk produk = new Produk(selectedId, nama, harga, stok);
            db.update(produk);

            JOptionPane.showMessageDialog(this, "Produk berhasil diupdate!");
            clearForm();
            loadData();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Input tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapusProduk() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih produk terlebih dahulu!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus?");
        if (confirm == JOptionPane.YES_OPTION) {
            db.hapus(selectedId);
            JOptionPane.showMessageDialog(this, "Produk berhasil dihapus!");
            clearForm();
            loadData();
        }
    }

    private void pilihProduk() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            selectedId = (int) tableModel.getValueAt(row, 0);
            tfNama.setText((String) tableModel.getValueAt(row, 1));
            tfHarga.setText(String.valueOf(tableModel.getValueAt(row, 2)));
            tfStok.setText(String.valueOf(tableModel.getValueAt(row, 3)));
        }
    }

    private void clearForm() {
        tfNama.setText("");
        tfHarga.setText("");
        tfStok.setText("");
        selectedId = -1;
        table.clearSelection();
    }

    private void loadData() {
        tableModel.setRowCount(0);
        for (Produk p : db.semuaData()) {
            tableModel.addRow(new Object[]{p.getId(), p.getNama(), p.getHarga(), p.getStok()});
        }
    }
}