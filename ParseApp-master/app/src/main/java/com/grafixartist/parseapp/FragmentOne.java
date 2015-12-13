package com.grafixartist.parseapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by solan_000 on 13/12/2015.
 */
public class FragmentOne extends Fragment {
    EditText name;
    Button back;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        back = (Button) container.getRootView().findViewById(R.id.hide);
        name = (EditText) container.getRootView().findViewById(R.id.name);
        return inflater.inflate(R.layout.fragment_one_layout,container,false);
    }


}
