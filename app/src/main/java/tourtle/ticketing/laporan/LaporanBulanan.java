package tourtle.ticketing.laporan;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import tourtle.ticketing.R;
import tourtle.ticketing.model.Tiket;
import tourtle.ticketing.utils.Session;


public class LaporanBulanan extends Fragment {
    private DatabaseReference databaseReference;
    private Query query;
    private String idWisata;
    private TextView tvTotalPendapatan;
    private TextView tvJumlahWisatawan, tvJumlahKendaraan, tvPendapatanTiket, tvPendapatanParkir, tvPajak;

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

        databaseReference = FirebaseDatabase.getInstance().getReference().child("tiket");
        tvTotalPendapatan = view.findViewById(R.id.tvTotalPendapatan);
        tvJumlahWisatawan = view.findViewById(R.id.tvJumlahPengunjung);
        tvPendapatanTiket = view.findViewById(R.id.tvPendapatanTiket);
        tvJumlahKendaraan = view.findViewById(R.id.tvJumlahKendaraan);
        tvPendapatanParkir = view.findViewById(R.id.tvPendapatanParkir);
        tvPajak = view.findViewById(R.id.tvPajak);

        final RackMonthPicker rackMonthPicker = new RackMonthPicker(getActivity())
                .setLocale(Locale.ENGLISH)
                .setSelectedMonth(4)
                .setColorTheme(R.color.colorPrimary)
                .setPositiveButton(new DateMonthDialogListener() {
                    @Override
                    public void onDateMonth(final int month, int startDate, int endDate, final int year, String monthLabel) {
                        query = databaseReference.orderByChild("idWisata").equalTo(idWisata);
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

                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    Tiket tiket = ds.getValue(Tiket.class);
                                    SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
                                    try {
                                        Date date = df.parse(tiket.getHari());
                                        String monthNumber = (String) DateFormat.format("MM", date);
                                        String yearSelected = (String) DateFormat.format("yyyy", date);

                                        Integer tahun = Integer.parseInt(yearSelected);
                                        Integer bulan = Integer.parseInt(monthNumber);

                                        if (tahun == year) {
                                            if (bulan == month) {
                                                jumlahPengunjung += tiket.getJumlahOrang();
                                                jumlahKendaraan += tiket.getJumlahKendaraan();
                                                pendapatanTiket += tiket.getBiayaTiket();
                                                pendapatanParkir += tiket.getBiayaParkir();
                                            }
                                        }

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }

                                DecimalFormat decimalFormat = new DecimalFormat("#,##0" +
                                        "");

                                totalBiaya = pendapatanParkir + pendapatanTiket;
                                Double pajak = totalBiaya * 0.1;
                                tvTotalPendapatan.setText(String.valueOf(decimalFormat.format(totalBiaya)));
                                tvJumlahWisatawan.setText(String.valueOf(jumlahPengunjung));
                                tvJumlahKendaraan.setText(String.valueOf(jumlahKendaraan));
                                tvPendapatanTiket.setText(String.valueOf(pendapatanTiket));
                                tvPendapatanParkir.setText(String.valueOf(pendapatanParkir));
                                tvPajak.setText(String.valueOf(decimalFormat.format(pajak)));
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
