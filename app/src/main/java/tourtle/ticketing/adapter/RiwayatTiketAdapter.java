package tourtle.ticketing.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import tourtle.ticketing.DetailTiket;
import tourtle.ticketing.R;
import tourtle.ticketing.model.Tiket;


public class RiwayatTiketAdapter extends RecyclerView.Adapter<RiwayatTiketAdapter.ViewHolder> {

    private final ArrayList<Tiket> rvData;
    private final Context context;
    private Integer hargaParkir = 0;

    public RiwayatTiketAdapter(ArrayList<Tiket> inputData, Context context) {
        rvData = inputData;
        this.context = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView tvIdTransaksi;
        public final TextView tvTotalBiaya;


        public ViewHolder(View v) {
            super(v);
            tvIdTransaksi = v.findViewById(R.id.tvIdTiket);
            tvTotalBiaya = v.findViewById(R.id.tvTotalBiaya);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_riwayat_tiket, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Tiket tiket = rvData.get(position);
        holder.tvIdTransaksi.setText(tiket.getIdTiket());
        holder.tvTotalBiaya.setText(String.valueOf(tiket.getTotalBiaya()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Integer hargaTiket = tiket.getBiayaTiket()/tiket.getJumlahOrang();
                if (tiket.getBiayaParkir()==0){
                    hargaParkir = 0;
                }else{
                    hargaParkir = tiket.getBiayaParkir()/tiket.getJumlahKendaraan();
                }
                Intent intent = new Intent(context, DetailTiket.class);
                bundle.putString("idWisata",tiket.getIdWisata());
                bundle.putString("idTiket",tiket.getIdTiket());
                bundle.putInt("biayaTiket",tiket.getBiayaTiket());
                bundle.putInt("biayaParkir",tiket.getBiayaParkir());
                bundle.putInt("jumlahOrang",tiket.getJumlahOrang());
                bundle.putInt("jumlahKendaraan",tiket.getJumlahKendaraan());
                bundle.putInt("totalBiaya",tiket.getTotalBiaya());
                bundle.putString("hari",tiket.getHari());
                bundle.putString("idNegara",tiket.getIdNegara());
                bundle.putInt("parkir",hargaParkir);
                bundle.putInt("hargaTiket",hargaTiket);
                bundle.putString("print",tiket.getPrint());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return rvData.size();
    }

}