import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

class TransaksiForm extends JFrame {
    private ProdukDatabase produkDb;
    private TransaksiDatabase transaksiDb;
    private Transaksi transaksiAktif;

    private JComboBox<Produk> cbProduk;
    private JSpinner spinnerJumlah;
    private JTable tableKeranjang;
    private DefaultTableModel tableModel;
    private JLabel lblTotal;
    private JButton btnTambah, btnHapus, btnBayar, btnRefresh;

    public TransaksiForm(ProdukDatabase produkDb, TransaksiDatabase transaksiDb) {
        this.produkDb = produkDb;
        this.transaksiDb = transaksiDb;
        buatTransaksiBaru();
        initComponents();
    }

    private void buatTransaksiBaru() {
        transaksiAktif = new Transaksi(0);
    }

    private void initComponents() {
        setTitle("Transaksi Penjualan");
        setSize(700, 500);
        setLayout(new BorderLayout(10, 10));

        JPanel panelInput = new JPanel(new GridLayout(3, 2, 5, 5));
        panelInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelInput.add(new JLabel("Pilih Produk:"));
        cbProduk = new JComboBox<>();
        loadProduk();
        panelInput.add(cbProduk);

        panelInput.add(new JLabel("Jumlah:"));
        spinnerJumlah = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        panelInput.add(spinnerJumlah);

        JPanel panelButtonInput = new JPanel(new FlowLayout());
        btnTambah = new JButton("Tambah ke Keranjang");
        btnRefresh = new JButton("Refresh Produk");
        btnTambah.addActionListener(e -> tambahKeKeranjang());
        btnRefresh.addActionListener(e -> loadProduk());
        panelButtonInput.add(btnTambah);
        panelButtonInput.add(btnRefresh);
        panelInput.add(panelButtonInput);

        add(panelInput, BorderLayout.NORTH);

        String[] columns = {"Produk", "Harga", "Jumlah", "Subtotal"};
        tableModel = new DefaultTableModel(columns, 0);
        tableKeranjang = new JTable(tableModel);
        add(new JScrollPane(tableKeranjang), BorderLayout.CENTER);

        JPanel panelBottom = new JPanel(new BorderLayout());

        lblTotal = new JLabel("Total: Rp 0");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelBottom.add(lblTotal, BorderLayout.WEST);

        JPanel panelButtonBottom = new JPanel(new FlowLayout());
        btnHapus = new JButton("Hapus Item");
        btnBayar = new JButton("BAYAR");
        btnBayar.setFont(new Font("Arial", Font.BOLD, 14));

        btnHapus.addActionListener(e -> hapusItem());
        btnBayar.addActionListener(e -> prosesPembayaran());

        panelButtonBottom.add(btnHapus);
        panelButtonBottom.add(btnBayar);
        panelBottom.add(panelButtonBottom, BorderLayout.EAST);

        add(panelBottom, BorderLayout.SOUTH);
    }

    private void loadProduk() {
        cbProduk.removeAllItems();
        for (Produk p : produkDb.semuaData()) {
            cbProduk.addItem(p);
        }
    }

    private void tambahKeKeranjang() {
        Produk produk = (Produk) cbProduk.getSelectedItem();
        if (produk == null) {
            JOptionPane.showMessageDialog(this, "Pilih produk terlebih dahulu!");
            return;
        }

        int jumlah = (int) spinnerJumlah.getValue();

        if (jumlah > produk.getStok()) {
            JOptionPane.showMessageDialog(this, "Stok tidak cukup! Stok tersedia: " + produk.getStok());
            return;
        }

        DetailTransaksi detail = new DetailTransaksi(produk, jumlah);
        transaksiAktif.tambahDetail(detail);

        updateTabelKeranjang();
        updateTotal();
    }

    private void hapusItem() {
        int row = tableKeranjang.getSelectedRow();
        if (row >= 0) {
            transaksiAktif.getDetailList().remove(row);
            updateTabelKeranjang();
            updateTotal();
        }
    }

    private void prosesPembayaran() {
        if (transaksiAktif.getDetailList().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Keranjang masih kosong!");
            return;
        }

        for (DetailTransaksi detail : transaksiAktif.getDetailList()) {
            Produk p = detail.getProduk();
            p.setStok(p.getStok() - detail.getJumlah());
            produkDb.update(p);
        }

        transaksiDb.tambah(transaksiAktif);

        JOptionPane.showMessageDialog(this,
                "Pembayaran berhasil!\nTotal: Rp " + transaksiAktif.getTotalHarga());

        buatTransaksiBaru();
        updateTabelKeranjang();
        updateTotal();
        loadProduk();
    }

    private void updateTabelKeranjang() {
        tableModel.setRowCount(0);
        for (DetailTransaksi detail : transaksiAktif.getDetailList()) {
            tableModel.addRow(new Object[]{
                    detail.getProduk().getNama(),
                    detail.getProduk().getHarga(),
                    detail.getJumlah(),
                    detail.getSubtotal()
            });
        }
    }

    private void updateTotal() {
        lblTotal.setText("Total: Rp " + transaksiAktif.getTotalHarga());
    }
}