import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class TransaksiDatabase extends Database<Transaksi> {
    private ProdukDatabase produkDb;

    public TransaksiDatabase(ProdukDatabase produkDb) {
        this.produkDb = produkDb;
    }

    @Override
    public void tambah(Transaksi transaksi) {
        String sqlTransaksi = "INSERT INTO transaksi (tanggal, total_harga) VALUES (?, ?)";
        String sqlDetail = "INSERT INTO detail_transaksi (transaksi_id, produk_id, jumlah, subtotal) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtTransaksi = conn.prepareStatement(sqlTransaksi, Statement.RETURN_GENERATED_KEYS)) {

                stmtTransaksi.setTimestamp(1, Timestamp.valueOf(transaksi.getTanggal()));
                stmtTransaksi.setDouble(2, transaksi.getTotalHarga());
                stmtTransaksi.executeUpdate();

                ResultSet rs = stmtTransaksi.getGeneratedKeys();
                if (rs.next()) {
                    int transaksiId = rs.getInt(1);
                    transaksi.setId(transaksiId);

                    try (PreparedStatement stmtDetail = conn.prepareStatement(sqlDetail)) {
                        for (DetailTransaksi detail : transaksi.getDetailList()) {
                            stmtDetail.setInt(1, transaksiId);
                            stmtDetail.setInt(2, detail.getProduk().getId());
                            stmtDetail.setInt(3, detail.getJumlah());
                            stmtDetail.setDouble(4, detail.getSubtotal());
                            stmtDetail.executeUpdate();
                        }
                    }
                }

                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hapus(int id) {
        String sqlDetail = "DELETE FROM detail_transaksi WHERE transaksi_id = ?";
        String sqlTransaksi = "DELETE FROM transaksi WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try {
                try (PreparedStatement stmt = conn.prepareStatement(sqlDetail)) {
                    stmt.setInt(1, id);
                    stmt.executeUpdate();
                }

                try (PreparedStatement stmt = conn.prepareStatement(sqlTransaksi)) {
                    stmt.setInt(1, id);
                    stmt.executeUpdate();
                }

                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Transaksi cari(int id) {
        String sqlTransaksi = "SELECT * FROM transaksi WHERE id = ?";
        String sqlDetail = "SELECT * FROM detail_transaksi WHERE transaksi_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtTransaksi = conn.prepareStatement(sqlTransaksi)) {

            stmtTransaksi.setInt(1, id);
            ResultSet rsTransaksi = stmtTransaksi.executeQuery();

            if (rsTransaksi.next()) {
                Transaksi transaksi = new Transaksi(
                        rsTransaksi.getInt("id"),
                        rsTransaksi.getTimestamp("tanggal").toLocalDateTime(),
                        rsTransaksi.getDouble("total_harga")
                );

                try (PreparedStatement stmtDetail = conn.prepareStatement(sqlDetail)) {
                    stmtDetail.setInt(1, id);
                    ResultSet rsDetail = stmtDetail.executeQuery();

                    while (rsDetail.next()) {
                        Produk produk = produkDb.cari(rsDetail.getInt("produk_id"));
                        if (produk != null) {
                            DetailTransaksi detail = new DetailTransaksi(
                                    rsDetail.getInt("id"),
                                    produk,
                                    rsDetail.getInt("jumlah")
                            );
                            transaksi.tambahDetail(detail);
                        }
                    }
                }

                return transaksi;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Transaksi> semuaData() {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT * FROM transaksi ORDER BY tanggal DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Transaksi t = new Transaksi(
                        rs.getInt("id"),
                        rs.getTimestamp("tanggal").toLocalDateTime(),
                        rs.getDouble("total_harga")
                );
                list.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Transaksi> getTransaksiHariIni() {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT * FROM transaksi WHERE DATE(tanggal) = CURDATE() ORDER BY tanggal DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Transaksi t = new Transaksi(
                        rs.getInt("id"),
                        rs.getTimestamp("tanggal").toLocalDateTime(),
                        rs.getDouble("total_harga")
                );
                list.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Method baru untuk mendapatkan transaksi minggu ini (7 hari terakhir)
    public List<Transaksi> getTransaksiMingguIni() {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT * FROM transaksi WHERE tanggal >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) ORDER BY tanggal DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Transaksi t = new Transaksi(
                        rs.getInt("id"),
                        rs.getTimestamp("tanggal").toLocalDateTime(),
                        rs.getDouble("total_harga")
                );
                list.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}