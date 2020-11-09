package com.example.saravanan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListAdapter  extends ArrayAdapter<User> {
    public ArrayList<User> MainList;

    public ArrayList<User> SubjectListTemp;

    public ListAdapter.SubjectDataFilter subjectDataFilter;

    public ListAdapter(Context context, int id, ArrayList<User> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<User>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<User>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (subjectDataFilter == null) {

            subjectDataFilter = new ListAdapter.SubjectDataFilter();
        }
        return subjectDataFilter;
    }


    public class ViewHolder {

        TextView Name;
        TextView Email;
        TextView Department;
        TextView Batch;
        TextView Country;
        ImageView Image;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListAdapter.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.custom_layout, null);

            holder = new ListAdapter.ViewHolder();

            holder.Name = (TextView) convertView.findViewById(R.id.FirstName);
            holder.Email = (TextView) convertView.findViewById(R.id.Email);
            holder.Department = (TextView) convertView.findViewById(R.id.Department);
            holder.Batch = (TextView) convertView.findViewById(R.id.Batch);
            holder.Country = (TextView) convertView.findViewById(R.id.Country);
            holder.Image=(ImageView)convertView.findViewById(R.id.imageView);

            convertView.setTag(holder);

        } else {
            holder = (ListAdapter.ViewHolder) convertView.getTag();
        }

        User subject = SubjectListTemp.get(position);

        holder.Name.setText(subject.getName());
        holder.Department.setText(subject.getDepartment());
        holder.Batch.setText(subject.getBatch());
        Glide.with(holder.Image.getContext()).load(subject.getImage()).into(holder.Image);

        return convertView;

    }

    private class SubjectDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<User> arrayList1 = new ArrayList<User>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    User subject = MainList.get(i);

                    if (subject.toString().toLowerCase().contains(charSequence))

                        arrayList1.add(subject);
                }
                filterResults.count = arrayList1.size();

                filterResults.values = arrayList1;
            } else {
                synchronized (this) {
                    filterResults.values = MainList;

                    filterResults.count = MainList.size();
                }
            }
            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            SubjectListTemp = (ArrayList<User>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));

            notifyDataSetInvalidated();
        }
    }
}
