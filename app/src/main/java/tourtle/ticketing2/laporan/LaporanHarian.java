package tourtle.ticketing2.laporan;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import tourtle.ticketing2.R;
import tourtle.ticketing2.model.Tiket;
import tourtle.ticketing2.utils.Session;


public class LaporanHarian extends Fragment implements DatePickerDialog.OnDateSetListener {
    private TextView dateTextView;
    private TextView tvJumlahWisatawan,tvJumlahKendaraan,tvPendapatanTiket,tvPendapatanParkir,
            tvRodaDua,tvRodaEmpat,tvBus,tvPajak,tvManca,tvDomestik;
    private DatePickerDialog dpd;
    private DatabaseReference databaseReference;
    private String idWisata,idUser;

    public LaporanHarian() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_laporan_harian, container, false);
        // Find our View instances
        dateTextView = view.findViewById(R.id.date_textview);
        tvJumlahWisatawan = view.findViewById(R.id.tvJumlahPengunjung);
        tvPendapatanTiket = view.findViewById(R.id.tvPendapatanTiket);
        tvJumlahKendaraan = view.findViewById(R.id.tvJumlahKendaraan);
        tvPendapatanParkir = view.findViewById(R.id.tvPendapatanParkir);
        tvPajak = view.findViewById(R.id.tvPajak);
        tvDomestik = view.findViewById(R.id.tvDomestik);
        tvManca = view.findViewById(R.id.tvManca);
        tvRodaDua = view.findViewById(R.id.tvJumlahMotor);
        tvRodaEmpat = view.findViewById(R.id.tvJumlahMobil);
        tvBus = view.findViewById(R.id.tvJumlahBus);

        Button dateButton = view.findViewById(R.id.date_button);
//        Session session = new Session();
        Session session = new Session(getActivity());
        idWisata = session.getIdWisata();
        idUser = session.getIdUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("tiket");

        // Show a datepicker when the dateButton is clicked
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                if (dpd == null) {
                    dpd = DatePickerDialog.newInstance(
                            LaporanHarian.this,
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    );
                } else {
                    dpd.initialize(
                            LaporanHarian.this,
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    );
                }

                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");
        if (dpd != null) dpd.setOnDateSetListener(this);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("sedang mengambil data");
        progressDialog.show();

        String date = (++monthOfYear) + "-" + dayOfMonth + "-" + year;
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        try {
            Date sDate = df.parse(date);
            String tanggal = df.format(sDate);

            String iHari = tanggal+"_"+idWisata+"_"+idUser;;


            Query query = databaseReference.orderByChild("iHari").equalTo(iHari);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Integer totalBiaya = 0;
                    Integer jumlahPengunjung = 0;
                    Integer pendapatanTiket = 0;
                    Integer jumlahKendaraan = 0;
                    Integer pendapatanParkir = 0;
                    Integer domestik = 0;
                    Integer manca = 0;
                    Integer mobil =0;
                    Integer motor = 0;
                    Integer bus = 0;


                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Tiket tiket = ds.getValue(Tiket.class);
                        if (tiket != null && tiket.getIdWisata().equals(idWisata)) {
                            if (tiket.getIdNegara().equals("1")){
                                domestik += tiket.getJumlahOrang();
                            }else{
                                manca += tiket.getJumlahOrang();
                            }


                            if (tiket.getJenisKendaraan().equals("2")){
                                motor+= tiket.getJumlahKendaraan();
                            }else if (tiket.getJenisKendaraan().equals("3")){
                                mobil += tiket.getJumlahKendaraan();
                            }else if (tiket.getJenisKendaraan().equals("4")){
                                bus += tiket.getJumlahKendaraan();
                            }

                            jumlahPengunjung += tiket.getJumlahOrang();
                            jumlahKendaraan += tiket.getJumlahKendaraan();
                            pendapatanTiket += tiket.getBiayaTiket();
                            pendapatanParkir += tiket.getBiayaParkir();
                        }
                    }
                    totalBiaya = pendapatanParkir + pendapatanTiket;
                    Double pajak = (pendapatanTiket * 0.1)+(pendapatanParkir*0.3);

                    DecimalFormat decimalFormat = new DecimalFormat("#,##0" +
                            "");

                    dateTextView.setText(String.valueOf(decimalFormat.format(totalBiaya)));
                    tvJumlahWisatawan.setText(String.valueOf(jumlahPengunjung));
                    tvJumlahKendaraan.setText(String.valueOf(jumlahKendaraan));
                    tvPendapatanTiket.setText(String.valueOf(decimalFormat.format(pendapatanTiket)));
                    tvPendapatanParkir.setText(String.valueOf(decimalFormat.format(pendapatanParkir)));
                    tvDomestik.setText(String.valueOf(domestik));
                    tvManca.setText(String.valueOf(manca));
                    tvPajak.setText(String.valueOf(decimalFormat.format(pajak)));

                    tvRodaDua.setText(decimalFormat.format(motor));
                    tvRodaEmpat.setText(decimalFormat.format(mobil));
                    tvBus.setText(decimalFormat.format(bus));

                    progressDialog.dismiss();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
