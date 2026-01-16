import java.util.List;

abstract class Database<T> {
    public abstract void tambah(T item);
    public abstract void hapus(int id);
    public abstract T cari(int id);
    public abstract List<T> semuaData();
}