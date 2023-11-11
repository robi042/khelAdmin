package code.fortomorrow.kheloNowAdmin.CustomDialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import code.fortomorrow.kheloNowAdmin.Activities.Search_activity;
import code.fortomorrow.kheloNowAdmin.R;

public class LoaderDialog {
    Context mContext;
    Boolean state;
    Dialog loader;

    public void showDialog(Context mContext, Boolean state) {
        loader = new Dialog(mContext);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        if (state) {
            loader.show();
        } else {
            loader.dismiss();
        }
    }
}
