package tourtle.ticketing2.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class TiketBulan {
    private String idTiket;
    private Integer jumlahOrang, wisdom, wisman;
    private Integer jumlahKendaraan, rodaDua, rodaEmpat, bus, jalan;
    private String idWisata;
    private Integer totalBiaya;
    private Integer biayaTiket;
    private Integer biayaParkir;

    public TiketBulan(String idTiket, Integer jumlahOrang, Integer jumlahKendaraan, String idWisata, Integer totalBiaya,
                 Integer biayaTiket, Integer biayaParkir, Integer wisdom, Integer wisman, Integer rodaDua, Integer rodaEmpat,
                 Integer bus, Integer jalan) {
        this.idTiket = idTiket;
        this.jalan = jalan;
        this.jumlahOrang = jumlahOrang;
        this.jumlahKendaraan = jumlahKendaraan;
        this.idWisata = idWisata;
        this.totalBiaya = totalBiaya;
        this.biayaTiket = biayaTiket;
        this.biayaParkir = biayaParkir;
        this.wisdom = wisdom;
        this.wisman = wisman;
        this.rodaDua = rodaDua;
        this.rodaEmpat = rodaEmpat;
        this.bus = bus;
    }

    public void setIdTiket(String idTiket) {
        this.idTiket = idTiket;
    }

    public void setJumlahOrang(Integer jumlahOrang) {
        this.jumlahOrang = jumlahOrang;
    }

    public void setWisdom(Integer wisdom) {
        this.wisdom = wisdom;
    }

    public void setWisman(Integer wisman) {
        this.wisman = wisman;
    }

    public void setJumlahKendaraan(Integer jumlahKendaraan) {
        this.jumlahKendaraan = jumlahKendaraan;
    }

    public void setRodaDua(Integer rodaDua) {
        this.rodaDua = rodaDua;
    }

    public void setRodaEmpat(Integer rodaEmpat) {
        this.rodaEmpat = rodaEmpat;
    }

    public void setBus(Integer bus) {
        this.bus = bus;
    }

    public void setJalan(Integer jalan) {
        this.jalan = jalan;
    }

    public void setIdWisata(String idWisata) {
        this.idWisata = idWisata;
    }

    public void setTotalBiaya(Integer totalBiaya) {
        this.totalBiaya = totalBiaya;
    }

    public void setBiayaTiket(Integer biayaTiket) {
        this.biayaTiket = biayaTiket;
    }

    public void setBiayaParkir(Integer biayaParkir) {
        this.biayaParkir = biayaParkir;
    }

    public String getIdTiket() {
        return idTiket;
    }

    public Integer getJumlahOrang() {
        return jumlahOrang;
    }

    public Integer getWisdom() {
        return wisdom;
    }

    public Integer getWisman() {
        return wisman;
    }

    public Integer getJumlahKendaraan() {
        return jumlahKendaraan;
    }

    public Integer getRodaDua() {
        return rodaDua;
    }

    public Integer getRodaEmpat() {
        return rodaEmpat;
    }

    public Integer getBus() {
        return bus;
    }

    public Integer getJalan() {
        return jalan;
    }

    public String getIdWisata() {
        return idWisata;
    }

    public Integer getTotalBiaya() {
        return totalBiaya;
    }

    public Integer getBiayaTiket() {
        return biayaTiket;
    }

    public Integer getBiayaParkir() {
        return biayaParkir;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("idWisata",idWisata);
        result.put("idTiket", idTiket);
        result.put("jumlahOrang", jumlahOrang);
        result.put("jumlahKendaraan", jumlahKendaraan);
        result.put("totalBiaya", totalBiaya);
        result.put("biayaTiket",biayaTiket);
        result.put("biayaParkir",biayaParkir);
        result.put("wisdom",wisdom);
        result.put("wisman",wisman);
        result.put("rodaDua",rodaDua);
        result.put("rodaEmpat",rodaEmpat);
        result.put("bus",bus);
        result.put("jalan",jalan);
        return result;
    }
}
