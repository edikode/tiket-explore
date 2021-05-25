package tourtle.ticketing2.model;

public class WisataModel {
    String idWisata,alamat,idKota,namaWisata;
    Integer parkirBus,parkirRodaDua,parkirRodaEmpat,tiketDomestik,tiketManca,tiketWeekend;

    public WisataModel(String idWisata, String alamat, String idKota, String namaWisata, Integer parkirBus, Integer parkirRodaDua, Integer parkirRodaEmpat, Integer tiketDomestik, Integer tiketManca, Integer tiketWeekend) {
        this.idWisata = idWisata;
        this.alamat = alamat;
        this.idKota = idKota;
        this.namaWisata = namaWisata;
        this.parkirBus = parkirBus;
        this.parkirRodaDua = parkirRodaDua;
        this.parkirRodaEmpat = parkirRodaEmpat;
        this.tiketDomestik = tiketDomestik;
        this.tiketManca = tiketManca;
        this.tiketWeekend = tiketWeekend;
    }

    public String getIdWisata() {
        return idWisata;
    }

    public void setIdWisata(String idWisata) {
        this.idWisata = idWisata;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getIdKota() {
        return idKota;
    }

    public void setIdKota(String idKota) {
        this.idKota = idKota;
    }

    public String getNamaWisata() {
        return namaWisata;
    }

    public void setNamaWisata(String namaWisata) {
        this.namaWisata = namaWisata;
    }

    public Integer getParkirBus() {
        return parkirBus;
    }

    public void setParkirBus(Integer parkirBus) {
        this.parkirBus = parkirBus;
    }

    public Integer getParkirRodaDua() {
        return parkirRodaDua;
    }

    public void setParkirRodaDua(Integer parkirRodaDua) {
        this.parkirRodaDua = parkirRodaDua;
    }

    public Integer getParkirRodaEmpat() {
        return parkirRodaEmpat;
    }

    public void setParkirRodaEmpat(Integer parkirRodaEmpat) {
        this.parkirRodaEmpat = parkirRodaEmpat;
    }

    public Integer getTiketDomestik() {
        return tiketDomestik;
    }

    public void setTiketDomestik(Integer tiketDomestik) {
        this.tiketDomestik = tiketDomestik;
    }

    public Integer getTiketManca() {
        return tiketManca;
    }

    public void setTiketManca(Integer tiketManca) {
        this.tiketManca = tiketManca;
    }

    public Integer getTiketWeekend() {
        return tiketWeekend;
    }

    public void setTiketWeekend(Integer tiketWeekend) {
        this.tiketWeekend = tiketWeekend;
    }
}
