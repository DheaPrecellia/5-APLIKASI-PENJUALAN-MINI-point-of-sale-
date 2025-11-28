package AplikasiPenjualan;

public class Produk {
    private int kodeProduk;
    private String namaProduk;
    private double harga;
    private int jmlProduk;

    public Produk() {
    }
    
    public Produk(String namaProduk, double harga, int jmlProduk) {
        this.namaProduk = namaProduk;
        this.harga = harga;
        this.jmlProduk = jmlProduk;
    }
    
    public Produk(int kodeProduk, String namaProduk, double harga, int jmlProduk) {
        this.kodeProduk = kodeProduk;
        this.namaProduk = namaProduk;
        this.harga = harga;
        this.jmlProduk = jmlProduk;
    }
    
    public int getkodeProduk() {
        return kodeProduk;
    }
    
    public void setkodeProduk(int kodeProduk) {
        this.kodeProduk = kodeProduk;
    }
    
    public String getnamaProduk() {
        return namaProduk;
    }
    
    public void setnamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }
    
    public double getHarga() {
        return harga;
    }
    
    public void setHarga(double harga) {
        this.harga = harga;
    }
    
    public int getjmlProduk() {
        return jmlProduk;
    }
    
    public void setjmlProduk(int jmlProduk) {
        this.jmlProduk = jmlProduk;
    }
    
    public boolean kurangiStok(int jumlah) {
        if (jmlProduk >= jumlah) {
            jmlProduk -= jumlah;
            return true;
        }
        return false;
    }

    public void tambahStok(int jumlah) {
        jmlProduk += jumlah;
    }

    public String toString() {
        return "Produk{" +
                "Kode Barang=" + kodeProduk +
                ", Nama='" + namaProduk + '\'' +
                ", Harga=" + harga +
                ", Stok=" + jmlProduk +
                '}';
    }
}
