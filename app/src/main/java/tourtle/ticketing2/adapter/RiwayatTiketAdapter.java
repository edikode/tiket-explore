package tourtle.ticketing2.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tourtle.ticketing2.DetailTiket;
import tourtle.ticketing2.R;
import tourtle.ticketing2.model.Tiket;


public class RiwayatTiketAdapter extends RecyclerView.Adapter<RiwayatTiketAdapter.ViewHolder> {

    private final ArrayList<Tiket> rvData;
    private final Context context;
    private Integer hargaParkir = 0;
    private Integer hargaTiket =0;

    public RiwayatTiketAdapter(ArrayList<Tiket> inputData, Context context) {
        rvData = inputData;
        this.context = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView tvIdTransaksi;
        public final TextView tvTotalBiaya,tvJam;
        public  final ImageView imgStatus;


        public ViewHolder(View v) {
            super(v);
            imgStatus = v.findViewById(R.id.imgStatus);
            tvIdTransaksi = v.findViewById(R.id.tvIdTiket);
            tvTotalBiaya = v.findViewById(R.id.tvTotalBiaya);
            tvJam = v.findViewById(R.id.tvJam);
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
        holder.tvJam.setText(tiket.getJam());
        if (tiket.getPrint().equals("1")){

        }else{
            holder.imgStatus.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                if (tiket.getBiayaTiket()==0){
                    hargaTiket = 0;
                }else{
                    hargaTiket = tiket.getBiayaTiket()/tiket.getJumlahOrang();
                }

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
                bundle.putString("jam",tiket.getJam());
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