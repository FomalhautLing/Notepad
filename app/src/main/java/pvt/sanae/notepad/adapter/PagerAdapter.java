package pvt.sanae.notepad.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

import pvt.sanae.notepad.fragment.ContentFragment;

public class PagerAdapter extends FragmentStateAdapter {

    private final FragmentManager fm;
    private final List<ContentFragment> data;
    private final List<Long> position2id;
    private long id = 0;

    public PagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        position2id = new ArrayList<>();
        data = new ArrayList<>();
        fm = fragmentManager;
    }

    public ContentFragment get(int position) {
        return (ContentFragment) fm.findFragmentByTag("f" + position2id.get(position));
    }

    public void set(int position, ContentFragment cf) {
        data.set(position, cf);
        position2id.set(position, id++);
        notifyItemChanged(position);
    }

    public void add(ContentFragment fragment) {
        data.add(fragment);
        position2id.add(id++);
        notifyItemInserted(data.size() - 1);
    }

    public void remove(int position) {
        data.remove(position);
        position2id.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public long getItemId(int position) {
        return position2id.get(position);
    }

    @Override
    public boolean containsItem(long itemId) {
        for (var id : position2id) {
            if (id == itemId) return true;
        }
        return false;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
