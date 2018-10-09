package com.android.criminalintent.myfragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.criminalintent.R;
import com.android.criminalintent.utilites.Crime;
import com.android.criminalintent.utilites.CrimeLab;

import java.util.List;

public class CrimeListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private CrimeAdapter mAdapter;
    public CrimeListFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list,container,false);
        mRecyclerView=view.findViewById(R.id.crime_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        UpdateUI();
        return view;
    }

    private void UpdateUI(){
        List<Crime> crimes= CrimeLab.getCrimeLab(getActivity()).getCrimes();
        mAdapter=new CrimeAdapter(crimes);
        mRecyclerView.setAdapter(mAdapter);
    }

    public class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Crime mCrime;

        //View Holder Class
        public CrimeHolder(LayoutInflater layoutInflater,ViewGroup parent,int layout) {
            super(layoutInflater.inflate(layout,parent,false));
            mTitleTextView=itemView.findViewById(R.id.crime_title);
            mDateTextView=itemView.findViewById(R.id.crime_date);
            itemView.setOnClickListener(this);
        }
        public void bind(Crime crime){
            mCrime=crime;
            mTitleTextView.setText(crime.getTitle());
            mDateTextView.setText(crime.getDate().toString());
        }
        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(),mCrime.getTitle()+" clicked!",Toast.LENGTH_SHORT).show();
        }
    }

    public class RegularCrimeHolder extends CrimeHolder{

        public RegularCrimeHolder(LayoutInflater layoutInflater, ViewGroup parent) {
            super(layoutInflater, parent, R.layout.list_item_crime);
        }
    }

    public class SeriousCrimeHolder extends CrimeHolder{
        private Button mContactPoliceButton;
        public SeriousCrimeHolder(LayoutInflater layoutInflater, ViewGroup parent) {
            super(layoutInflater, parent, R.layout.list_item_serious_crime);
            mContactPoliceButton=itemView.findViewById(R.id.contact_police);
        }

        @Override
        public void bind(Crime crime) {
            super.bind(crime);
            mContactPoliceButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"Contact Police",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

//CrimeAdapter class
    public class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes){
            mCrimes=crimes;
        }

        @Override
        public int getItemViewType(int position) {
            if(mCrimes.get(position).isRequiresPolice()){
                return 1;
            }
            return 0;
        }

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
            if (viewType==0)
                return new RegularCrimeHolder(layoutInflater,parent);
            else
                return new SeriousCrimeHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder holder, int position) {
            Crime crime=mCrimes.get(position);
            holder.bind(crime);
        }


        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }

}
