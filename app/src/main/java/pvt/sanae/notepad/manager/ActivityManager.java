package pvt.sanae.notepad.manager;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityManager<Activity extends AppCompatActivity> {

    protected final Activity ac;

    public ActivityManager(Activity ac) {
        this.ac = ac;
        initView();
        bindListener();
    }

    protected void initView() {
    }

    protected void bindListener() {
    }
}
