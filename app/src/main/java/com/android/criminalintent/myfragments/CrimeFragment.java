package com.android.criminalintent.myfragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.android.criminalintent.utilites.Crime;
import com.android.criminalintent.R;


public class CrimeFragment extends Fragment implements TextWatcher , OnCheckedChangeListener {

    private static final String TAG = "CrimeFragment";
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateBtn;
    private CheckBox mSolvedCheckBox;
    public CrimeFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime=new Crime();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_crime,container,false);

        mTitleField=v.findViewById(R.id.crime_title);
        mTitleField.addTextChangedListener(this);

        mDateBtn =v.findViewById(R.id.crime_date);
        mDateBtn.setText(mCrime.getDate().toString());
        mDateBtn.setEnabled(false);

        mSolvedCheckBox=v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setOnCheckedChangeListener(this);
        return v;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mCrime.setTitle(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mCrime.setSolved(isChecked);
    }
}
