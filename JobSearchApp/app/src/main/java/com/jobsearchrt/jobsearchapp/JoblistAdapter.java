package com.jobsearchrt.jobsearchapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by suman on 8/1/2016.
 */
public class JoblistAdapter extends ArrayAdapter {
    ArrayList<Results> results;
    Context context;
    public JoblistAdapter(Context context, ArrayList<Results> results) {
        super(context, R.layout.single_row_listofjobs,results);
        this.context=context;
        this.results=results;
    }
    class ResultViewHolder {
        TextView mytitle;
        TextView mycompany;
        TextView myaddress;
        TextView mydescription;

        ResultViewHolder(View v) {
            mytitle= (TextView) v.findViewById(R.id.titlefield);
            mycompany= (TextView) v.findViewById(R.id.comapnyfield);
            myaddress = (TextView) v.findViewById(R.id.addressfield);
            mydescription = (TextView) v.findViewById(R.id.descriptionfield);

        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ResultViewHolder holder = null;
        if(row==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.single_row_listofjobs,parent,false);
            holder = new ResultViewHolder(row);
            row.setTag(holder);
            }
        else {
            holder= (ResultViewHolder) row.getTag();
        }
        Results result = results.get(position);
        holder.mytitle.setText(result.jobtitle);
        holder.mycompany.setText("Company" +result.company);
        holder.myaddress.setText("Address" +result.city+","+ result.state+", " +result.country);
        holder.mydescription.setText(result.snippet);
        return row;
    }
}
