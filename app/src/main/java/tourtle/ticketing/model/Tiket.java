package tourtle.ticketing.model;


public class Tiket {

    private String idTiket;
    private Integer jumlahOrang;
    private Integer jumlahKendaraan;
    private String hari;
    private String idWisata;
    private String idNegara;
    private String print;
    private Integer totalBiaya;
    private Integer biayaTiket;
    private Integer biayaParkir;

    public Tiket(){

    }



    public void setHari(String hari) {
        this.hari = hari;
    }

    public void setTotalBiaya(int totalBiaya) {
        this.totalBiaya = totalBiaya;
    }

    public void setBiayaTiket(int biayaTiket) {
        this.biayaTiket = biayaTiket;
    }

    public void setBiayaParkir(int biayaParkir) {
        this.biayaParkir = biayaParkir;
    }

    public String getPrint() {
        return print;
    }

    public Tiket(String idTiket, String idWisata , Integer jumlahOrang, Integer jumlahKendaraan, Integer totalBiaya, Integer biayaParkir, Integer biayaTiket,
                 String hari, String idNegara, String print){
        this.idTiket = idTiket;
        this.idWisata = idWisata;
        this.jumlahOrang = jumlahOrang;
        this.jumlahKendaraan = jumlahKendaraan;
        this.totalBiaya = totalBiaya;
        this.biayaParkir = biayaParkir;
        this.biayaTiket = biayaTiket;
        this.hari = hari;
        this.idNegara = idNegara;
        this.print = print;

    }


    public String getHari() {
        return hari;
    }

    public String getIdNegara() {
        return idNegara;
    }

    public Integer getBiayaTiket() {
        return biayaTiket;
    }

    public String getIdWisata() {
        return idWisata;
    }

    public Integer getJumlahKendaraan() {
        return jumlahKendaraan;
    }

    public Integer getBiayaParkir() {
        return biayaParkir;
    }

    public String getIdTiket() {
        return idTiket;
    }

    public Integer getJumlahOrang() {
        return jumlahOrang;
    }

    public Integer getTotalBiaya() {
        return totalBiaya;
    }
}
