import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class ProdukDatabase extends Database<Produk> {

    @Override
    public void tambah(Produk produk) {
        String sql = "INSERT INTO produk (nama, harga, stok) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produk.getNama());
            stmt.setDouble(2, produk.getHarga());
            stmt.setInt(3, produk.getStok());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hapus(int id) {
        String sqlDetail = "DELETE FROM detail_transaksi WHERE produk_id = ?";
        String sqlProduk = "DELETE FROM produk WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try {
                try (PreparedStatement stmt = conn.prepareStatement(sqlDetail)) {
                    stmt.setInt(1, id);
                    stmt.executeUpdate();
                }

                try (PreparedStatement stmt = conn.prepareStatement(sqlProduk)) {
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
    public Produk cari(int id) {
        String sql = "SELECT * FROM produk WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Produk(
                        rs.getInt("id"),
                        rs.getString("nama"),
                        rs.getDouble("harga"),
                        rs.getInt("stok")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Produk> semuaData() {
        List<Produk> list = new ArrayList<>();
        String sql = "SELECT * FROM produk ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Produk p = new Produk(
                        rs.getInt("id"),
                        rs.getString("nama"),
                        rs.getDouble("harga"),
                        rs.getInt("stok")
                );
                list.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void update(Produk produk) {
        String sql = "UPDATE produk SET nama = ?, harga = ?, stok = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produk.getNama());
            stmt.setDouble(2, produk.getHarga());
            stmt.setInt(3, produk.getStok());
            stmt.setInt(4, produk.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}