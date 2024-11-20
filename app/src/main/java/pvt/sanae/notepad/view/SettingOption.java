package pvt.sanae.notepad.view;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.SwitchCompat;

import pvt.sanae.notepad.R;
import pvt.sanae.notepad.util.ConfigUtil;

public class SettingOption extends LinearLayout {

    private static final String[] appTheme = new String[]{"浅色", "深色", "使用系统设置"};

    private SwitchCompat switchBtn;
    private final Context context;
    private RadioGroup subOption;
    private ImageView expandHintArrow;
    private ImageView icon;

    public SettingOption(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(context, R.layout.view_setting_option, this);
        this.context = context;

        if (attrs != null) {
            try (TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.SettingOption)) {
                int rid = t.getResourceId(R.styleable.SettingOption_src, R.mipmap.setting_option_palette);
                icon = view.findViewById(R.id.icon);
                icon.setImageResource(rid);

                String title = t.getString(R.styleable.SettingOption_title);
                ((TextView) view.findViewById(R.id.title)).setText(title);

                String desc = t.getString(R.styleable.SettingOption_desc);
                ((TextView) view.findViewById(R.id.desc)).setText(desc);

                if (desc == null) {
                    var params = new GridLayout.LayoutParams();
                    params.rowSpec = GridLayout.spec(0, 2, 1);
                    params.columnSpec = GridLayout.spec(1, 1, 1);
                    params.setGravity(Gravity.CENTER_VERTICAL);
                    view.findViewById(R.id.title).setLayoutParams(params);
                }

                if (!t.getBoolean(R.styleable.SettingOption_with_switch_btn, false)) {
                    view.findViewById(R.id.switchBtn).setVisibility(GONE);
                    view.findViewById(R.id.switchBtnState).setVisibility(GONE);
                } else {
                    switchBtn = view.findViewById(R.id.switchBtn);
                    TextView state = view.findViewById(R.id.switchBtnState);
                    switchBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        state.setText(isChecked ? switchBtn.getTextOn() : switchBtn.getTextOff());
                    });
                }

                if (isClickable()) {
                    setOrientation(VERTICAL);
                    expandHintArrow = findViewById(R.id.expandHintArrow);

                    if (getId() == R.id.appTheme) {
                        initSubOption(appTheme, ConfigUtil.getAppTheme());
                    }

                    if (subOption != null) {
                        addView(subOption);
                        subOption.setVisibility(GONE);  // 默认收起
                        setOnClickListener(v -> {
                            if (subOption.getVisibility() == VISIBLE) {
                                setSelected(false);
                                subOption.setVisibility(GONE);
                                expandHintArrow.setRotation(0);
                            } else {
                                setSelected(true);
                                subOption.setVisibility(VISIBLE);
                                expandHintArrow.setRotation(180);
                            }
                        });
                    }
                } else {
                    findViewById(R.id.expandHintArrow).setVisibility(GONE);
                }
            }
        }
    }

    private void initSubOption(String[] options, int checked) {
        icon.measure(0, 0);
        MarginLayoutParams mp = (MarginLayoutParams) icon.getLayoutParams();

        subOption = new RadioGroup(context);
        subOption.setBackgroundResource(R.drawable.bg_setting_option_sub);
        subOption.setPadding(
                icon.getMeasuredWidth() + mp.leftMargin + mp.rightMargin,
                0,
                context.getResources().getDimensionPixelSize(R.dimen.settingOptionSubPadding),
                context.getResources().getDimensionPixelSize(R.dimen.settingOptionSubPadding)
        );

        for (String text : options) {
            RadioButton rb = new RadioButton(context);
            rb.setText(text);
            rb.setLayoutParams(new RadioGroup.LayoutParams(
                    MATCH_PARENT,
                    context.getResources().getDimensionPixelSize(R.dimen.settingOptionSubHeight)
            ));
            rb.setCompoundDrawablesWithIntrinsicBounds(
                    AppCompatResources.getDrawable(context, R.drawable.radio_setting_option_sub),
                    null, null, null
            );
            rb.setBackground(null);  // 这个不设 null 会有奇怪的动画效果
            rb.setButtonDrawable(null);
            rb.setCompoundDrawablePadding(context.getResources().getDimensionPixelSize(R.dimen.settingOptionSubPadding));
            subOption.addView(rb);
        }
        if (0 <= checked && checked < subOption.getChildCount()) {
            subOption.check(subOption.getChildAt(checked).getId());
        }
    }

    public void setSubOption(int position) {
        if (subOption == null) return;
        subOption.check(subOption.getChildAt(position).getId());
    }

    public int getSubOption() {
        if (subOption == null) return -1;
        return subOption.indexOfChild(subOption.findViewById(subOption.getCheckedRadioButtonId()));
    }

    public boolean isChecked() {
        return switchBtn != null && switchBtn.isChecked();
    }

    public void setChecked(boolean checked) {
        if (switchBtn == null) return;
        switchBtn.setChecked(checked);
    }
}
