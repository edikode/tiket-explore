package tourtle.ticketing.laporan;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
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
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import tourtle.ticketing.R;
import tourtle.ticketing.model.Tiket;
import tourtle.ticketing.utils.Session;


public class LaporanHarian extends Fragment implements DatePickerDialog.OnDateSetListener {
    private TextView dateTextView;
    private TextView tvJumlahWisatawan,tvJumlahKendaraan,tvPendapatanTiket,tvPendapatanParkir;
    private DatePickerDialog dpd;
    private DatabaseReference databaseReference;
    private Query query;
    private String idWisata;

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
        Button dateButton = view.findViewById(R.id.date_button);
//        Session session = new Session();
        Session session = new Session(getActivity());
        idWisata = session.getIdWisata();
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

            String hari = df.format(sDate.getTime());

            query = databaseReference.orderByChild("hari").equalTo(hari);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Integer totalBiaya = 0;
                    Integer jumlahPengunjung = 0;
                    Integer pendapatanTiket = 0;
                    Integer jumlahKendaraan = 0;
                    Integer pendapatanParkir = 0;

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Tiket tiket = ds.getValue(Tiket.class);
                        if (tiket != null && tiket.getIdWisata().equals(idWisata)) {
                            jumlahPengunjung += tiket.getJumlahOrang();
                            jumlahKendaraan += tiket.getJumlahKendaraan();
                            pendapatanTiket += tiket.getBiayaTiket();
                            pendapatanParkir += tiket.getBiayaParkir();
                        }
                    }
                    totalBiaya = pendapatanParkir + pendapatanTiket;
                    dateTextView.setText(String.valueOf(totalBiaya));
                    tvJumlahWisatawan.setText(String.valueOf(jumlahPengunjung));
                    tvJumlahKendaraan.setText(String.valueOf(jumlahKendaraan));
                    tvPendapatanTiket.setText(String.valueOf(pendapatanTiket));
                    tvPendapatanParkir.setText(String.valueOf(pendapatanParkir));
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
