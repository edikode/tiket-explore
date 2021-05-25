package tourtle.ticketing2.model;

public class UserModel {
    private String idWisata,namaWisata,tanggal,jam, status,email;
    private Integer kodeVersi;

    public UserModel(String idWisata, String namaWisata, String tanggal, String jam, String status,String email,Integer kodeVersi) {
        this.idWisata = idWisata;
        this.namaWisata = namaWisata;
        this.tanggal = tanggal;
        this.jam = jam;
        this.status = status;
        this.email = email;
        this.kodeVersi = kodeVersi;
    }

    public String getIdWisata() {
        return idWisata;
    }

    public String getNamaWisata() {
        return namaWisata;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getJam() {
        return jam;
    }

    public String getStatus() {
        return status;
    }

    public String getEmail() {
        return email;
    }

    public Integer getKodeVersi() {
        return kodeVersi;
    }
}
