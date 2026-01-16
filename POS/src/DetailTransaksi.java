class DetailTransaksi {
    private int id;
    private Produk produk;
    private int jumlah;
    private double subtotal;

    public DetailTransaksi(int id, Produk produk, int jumlah) {
        this.id = id;
        this.produk = produk;
        this.jumlah = jumlah;
        this.subtotal = produk.getHarga() * jumlah;
    }

    public DetailTransaksi(Produk produk, int jumlah) {
        this(0, produk, jumlah);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Produk getProduk() { return produk; }
    public void setProduk(Produk produk) { this.produk = produk; }

    public int getJumlah() { return jumlah; }
    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
        this.subtotal = produk.getHarga() * jumlah;
    }

    public double getSubtotal() { return subtotal; }
}