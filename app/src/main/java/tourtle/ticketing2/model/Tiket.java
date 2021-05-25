package tourtle.ticketing2.model;



public class Tiket {

    private String idTiket;
    private Integer jumlahOrang;
    private Integer jumlahKendaraan;
    private String hari,jam;
    private String idWisata;
    private String idNegara;
    private String print;
    private Integer totalBiaya;
    private Integer biayaTiket;
    private Integer biayaParkir;
    private String idUser;
    private String iHari,iBulan,jenisKendaraan,index;

    public Tiket(){

    }

    public Tiket(String idTiket, String idWisata , Integer jumlahOrang, Integer jumlahKendaraan, Integer totalBiaya, Integer biayaParkir, Integer biayaTiket,
                 String hari, String idNegara, String print,String idUser, String iHari, String iBulan,String jenisKendaraan,String jam,String index){
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
        this.idUser = idUser;
        this.iHari = iHari;
        this.iBulan = iBulan;
        this.jenisKendaraan = jenisKendaraan;
        this.jam = jam;
        this.index = index;
    }





    public String getPrint() {
        return print;
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

    public String getJenisKendaraan() {
        return jenisKendaraan;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getiHari() {
        return iHari;
    }

    public String getiBulan() {
        return iBulan;
    }

    public String getJam(){
        return jam;
    }

    public String getIndex() {
        return index;
    }






}
