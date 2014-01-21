package com.virtualpm.main.dialogs;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.actionbarsherlock.app.ActionBar;
import com.virtualpm.main.R;
import com.virtualpm.main.SigninTab;
import com.virtualpm.main.VirtualPM;
import com.virtualpm.main.api.PMServerAPI;
import com.virtualpm.main.listenerinterfaces.SubmitListener;
import com.virtualpm.main.localobjects.Land;

import java.text.SimpleDateFormat;

/**
 * Created by Joseph Altmaier on 1/20/14.
 */
public class SubmitDialog extends DialogFragment implements Button.OnClickListener, SubmitListener {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    public static final String SIGNIN_SHEET = "SIGNIN_SHEET";
    public static final String TAG = "SUBMIT_DIALOG";
    public static final String FRAGMENT_TAG = "FRAGMENT_TAG";

    private ProgressDialog progress;

    private Land land;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.submit_layout, container, false);
        Button b = (Button)v.findViewById(R.id.buttonSubmit);
        b.setOnClickListener(this);
        b = (Button)v.findViewById(R.id.buttonCancel);
        b.setOnClickListener(this);

        land = (Land)getArguments().getSerializable(SIGNIN_SHEET);
        getDialog().setTitle("Sign-in for " + land.getLandName() + " on " + sdf.format(land.getSheetDate()));
        return v;
    }

    private void submit(){
        progress = new ProgressDialog(getActivity());
        progress.show();
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.setContentView(R.layout.progressdialog);

        PMServerAPI.get().submitSheet(land, this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonSubmit:
                submit();
                break;
            case R.id.buttonCancel:
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().remove(this).commit();
                break;
            default:
                break;
        }
    }

    @Override
    public void submitSuccess() {
        FragmentManager manager = getFragmentManager();
        String tabTag = getArguments().getString(FRAGMENT_TAG);
        SigninTab tab = (SigninTab)manager.findFragmentByTag(tabTag);
        VirtualPM activity = (VirtualPM)getActivity();
        ActionBar bar = activity.getSupportActionBar();
        bar.removeTab(bar.getSelectedTab());
        manager.beginTransaction().remove(tab).remove(this).commit();
        if(bar.getTabCount() <= 0)
            activity.getMenu().findItem(R.id.submit).setEnabled(false);
        progress.dismiss();
    }

    @Override
    public void submitFailure(int code, String reason) {

    }
}
