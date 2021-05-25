package tourtle.ticketing2.laporan;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Fragment;
import androidx.appcompat.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rackspira.kristiawan.rackmonthpicker.RackMonthPicker;
import com.rackspira.kristiawan.rackmonthpicker.listener.DateMonthDialogListener;
import com.rackspira.kristiawan.rackmonthpicker.listener.OnCancelMonthDialogListener;

import java.text.DecimalFormat;
import java.util.Locale;

import tourtle.ticketing2.R;
import tourtle.ticketing2.model.Tiket;
import tourtle.ticketing2.utils.Session;


public class LaporanBulanan extends Fragment {
    private DatabaseReference databaseReference;
    private Query query;
    private String idWisata, idUser;
    private TextView tvTotalPendapatan;
    private TextView tvJumlahWisatawan, tvJumlahKendaraan, tvPendapatanTiket, tvPendapatanParkir,tvRodaDua,tvRodaEmpat,tvBus,
            tvPajak, tvManca, tvDomestik;

    public LaporanBulanan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_laporan_bulanan, container, false);
        Session session = new Session(getActivity());
        idWisata = session.getIdWisata();
        idUser = session.getIdUser();


        databaseReference = FirebaseDatabase.getInstance().getReference().child("tiket");
        tvTotalPendapatan = view.findViewById(R.id.tvTotalPendapatan);
        tvJumlahWisatawan = view.findViewById(R.id.tvJumlahPengunjung);
        tvPendapatanTiket = view.findViewById(R.id.tvPendapatanTiket);
        tvJumlahKendaraan = view.findViewById(R.id.tvJumlahKendaraan);
        tvPendapatanParkir = view.findViewById(R.id.tvPendapatanParkir);
        tvPajak = view.findViewById(R.id.tvPajak);
        tvManca = view.findViewById(R.id.tvManca);
        tvDomestik = view.findViewById(R.id.tvDomestik);

        tvRodaDua = view.findViewById(R.id.tvJumlahMotor);
        tvRodaEmpat = view.findViewById(R.id.tvJumlahMobil);
        tvBus = view.findViewById(R.id.tvJumlahBus);

        final RackMonthPicker rackMonthPicker = new RackMonthPicker(getActivity())
                .setLocale(Locale.ENGLISH)
                .setSelectedMonth(4)
                .setColorTheme(R.color.colorPrimary)
                .setPositiveButton(new DateMonthDialogListener() {
                    @Override
                    public void onDateMonth(int month, int startDate, int endDate, final int year, String monthLabel) {

                        String iBulan = month + "_" + year + "_" + idWisata + "_" + idUser;

                        query = databaseReference.orderByChild("iBulan").equalTo(iBulan);
                        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setMessage("sedang mengambil data");
                        progressDialog.show();

                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Integer totalBiaya = 0;
                                Integer jumlahPengunjung = 0;
                                Integer pendapatanTiket = 0;
                                Integer jumlahKendaraan = 0;
                                Integer pendapatanParkir = 0;
                                Integer manca = 0;
                                Integer domestik = 0;
                                Integer mobil =0;
                                Integer motor = 0;
                                Integer bus = 0;

                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    Tiket tiket = ds.getValue(Tiket.class);

                                    jumlahPengunjung += tiket.getJumlahOrang();
                                    jumlahKendaraan += tiket.getJumlahKendaraan();
                                    pendapatanTiket += tiket.getBiayaTiket();
                                    pendapatanParkir += tiket.getBiayaParkir();

                                    if (tiket.getIdNegara().equals("1")) {
                                        domestik += tiket.getJumlahOrang();
                                    } else {
                                        manca += tiket.getJumlahOrang();
                                    }

                                    if (tiket.getJenisKendaraan().equals("2")){
                                        motor+= tiket.getJumlahKendaraan();
                                    }else if (tiket.getJenisKendaraan().equals("3")){
                                        mobil += tiket.getJumlahKendaraan();
                                    }else if (tiket.getJenisKendaraan().equals("4")){
                                        bus += tiket.getJumlahKendaraan();
                                    }


                                }

                                DecimalFormat decimalFormat = new DecimalFormat("#,##0" +
                                        "");

                                totalBiaya = pendapatanParkir + pendapatanTiket;
                                Double pajak = (pendapatanTiket * 0.1) + (pendapatanParkir * 0.3);
                                tvTotalPendapatan.setText(String.valueOf(decimalFormat.format(totalBiaya)));
                                tvJumlahWisatawan.setText(String.valueOf(jumlahPengunjung));
                                tvJumlahKendaraan.setText(String.valueOf(jumlahKendaraan));
                                tvPendapatanTiket.setText(String.valueOf(decimalFormat.format(pendapatanTiket)));
                                tvPendapatanParkir.setText(String.valueOf(decimalFormat.format(pendapatanParkir)));
                                tvPajak.setText(String.valueOf(decimalFormat.format(pajak)));
                                tvDomestik.setText(String.valueOf(domestik));
                                tvManca.setText(String.valueOf(manca));

                                tvRodaDua.setText(decimalFormat.format(motor));
                                tvRodaEmpat.setText(decimalFormat.format(mobil));
                                tvBus.setText(decimalFormat.format(bus));

                                progressDialog.dismiss();


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                })
                .setNegativeButton(new OnCancelMonthDialogListener() {
                    @Override
                    public void onCancel(AlertDialog dialog) {
                        dialog.dismiss();
                    }
                });

        Button button = (Button) view.findViewById(R.id.btn_show);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rackMonthPicker.show();
            }
        });
        return view;
    }

}
