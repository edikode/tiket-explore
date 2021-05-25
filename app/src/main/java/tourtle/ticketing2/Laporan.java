package tourtle.ticketing2;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import android.app.Fragment;
import android.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.legacy.app.FragmentPagerAdapter;

import tourtle.ticketing2.laporan.LaporanBulanan;
import tourtle.ticketing2.laporan.LaporanHarian;
import tourtle.ticketing2.utils.Session;

public class Laporan extends AppCompatActivity {

    PickerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Laporan");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        adapter = new PickerAdapter(getFragmentManager());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager =  findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout =  findViewById(R.id.tabs);
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
