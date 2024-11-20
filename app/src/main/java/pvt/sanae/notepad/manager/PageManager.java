package pvt.sanae.notepad.manager;

import android.util.Log;

import androidx.viewpager2.widget.ViewPager2;

import pvt.sanae.notepad.MainActivity;
import pvt.sanae.notepad.R;
import pvt.sanae.notepad.adapter.PagerAdapter;
import pvt.sanae.notepad.fragment.ContentFragment;
import pvt.sanae.notepad.view.NavbarItem;

public class PageManager extends ActivityManager<MainActivity> {

    private static final String TAG = "PageManager";

    private PagerAdapter adapter;
    private ViewPager2 pager;

    public PageManager(MainActivity ac) {
        super(ac);
    }

    public void addPage(ContentFragment fragment) {
        adapter.add(fragment);
        ac.mNavbar.addItem(new NavbarItem(ac));
    }

    public void removePage(int position) {
        if (getSize() > 1) {
            adapter.remove(position);
            ac.mNavbar.removeItem(position);
        } else {
            ac.finish();
        }
    }

    public void removeCurrentPage() {
        removePage(getCurrentPosition());
    }

    public void setPageData(int position, ContentFragment cf) {
        adapter.set(position, cf);
    }

    public int getCurrentPosition() {
        return pager.getCurrentItem();
    }

    public void setCurrentPosition(int position) {
        pager.setCurrentItem(position);
    }

    public ContentFragment getCurrentFragment() {
        return getFragment(getCurrentPosition());
    }

    public ContentFragment getFragment(int position) {
        return adapter.get(position);
    }

    public int getSize() {
        return adapter.getItemCount();
    }

    @Override
    protected void initView() {
        adapter = new PagerAdapter(ac.getSupportFragmentManager(), ac.getLifecycle());
        pager = ac.findViewById(R.id.pager);
        pager.setAdapter(adapter);
    }

    @Override
    protected void bindListener() {
        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "page selected: " + position);
                // 注意：当 pager 位置变更时，会连带修改 navbar
                ac.mNavbar.select(position);
            }
        });
    }
}
