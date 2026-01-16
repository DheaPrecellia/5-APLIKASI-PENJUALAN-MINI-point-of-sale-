import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class Transaksi {
    private int id;
    private LocalDateTime tanggal;
    private double totalHarga;
    private List<DetailTransaksi> detailList;

    public Transaksi(int id) {
        this.id = id;
        this.tanggal = LocalDateTime.now();
        this.detailList = new ArrayList<>();
        this.totalHarga = 0;
    }

    public Transaksi(int id, LocalDateTime tanggal, double totalHarga) {
        this.id = id;
        this.tanggal = tanggal;
        this.totalHarga = totalHarga;
        this.detailList = new ArrayList<>();
    }

    public void tambahDetail(DetailTransaksi detail) {
        detailList.add(detail);
        hitungTotal();
    }

    private void hitungTotal() {
        totalHarga = detailList.stream()
                .mapToDouble(DetailTransaksi::getSubtotal)
                .sum();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDateTime getTanggal() { return tanggal; }
    public double getTotalHarga() { return totalHarga; }
    public List<DetailTransaksi> getDetailList() { return detailList; }

    public String getTanggalFormatted() {
        return tanggal.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
}