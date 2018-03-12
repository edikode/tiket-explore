package tourtle.ticketing;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v13.app.FragmentPagerAdapter;

import tourtle.ticketing.laporan.LaporanBulanan;
import tourtle.ticketing.laporan.LaporanHarian;
import tourtle.ticketing.laporan.LaporanTahunan;
import tourtle.ticketing.utils.Session;

public class Laporan extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    PickerAdapter adapter;
    private String idWisata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new PickerAdapter(getFragmentManager());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        Session session = new Session(Laporan.this);
        idWisata = session.getIdWisata();

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < adapter.getCount(); i++) //noinspection ConstantConditions
            tabLayout.getTabAt(i).setText(adapter.getTitle(i));
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


    private class PickerAdapter extends FragmentPagerAdapter {
        private static final int NUM_PAGES = 2;
        Fragment LaporanHarian;
        Fragment LaporanBulanan;

        PickerAdapter(FragmentManager fm) {
            super(fm);
            LaporanHarian = new LaporanHarian();
            LaporanBulanan = new LaporanBulanan();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return LaporanHarian;
                case 1:
                default:
                    return LaporanBulanan;
            }
        }

        int getTitle(int position) {
            switch (position) {
                case 0:
                    return R.string.tab_title_harian;
                case 1:
                default:
                    return R.string.tab_title_bulanan;
            }
        }
    }
}
