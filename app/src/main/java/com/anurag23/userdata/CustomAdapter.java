package com.anurag23.userdata;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by anurag on 28/5/16.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.UserViewHolder> {

    private List<CustomModel> list;
    private LayoutInflater inflater;
    private Context context;


    public CustomAdapter(Context context, List<CustomModel> list){
        inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.user_item, parent, false);

        UserViewHolder holder = new UserViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {

        CustomModel model = list.get(position);
        holder.name.setText(model.getName());
        holder.userName.setText(model.getUserName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, userName;

        public UserViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            name = (TextView)itemView.findViewById(R.id.list_name);
            userName = (TextView)itemView.findViewById(R.id.list_username);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            CustomModel model = list.get(position);

            Intent intent = new Intent (context, UserActivity.class);

            intent.putExtra("name", model.getName());
            intent.putExtra("username", model.getUserName());
            intent.putExtra("email", model.getEmail());
            intent.putExtra("aStreet", model.getaStreet());
            intent.putExtra("aSuite", model.getaSuite());
            intent.putExtra("aCity", model.getaCity());
            intent.putExtra("aZip", model.getaZip());
            intent.putExtra("phone", model.getPhone());
            intent.putExtra("website", model.getWebsite());

            context.startActivity(intent);
        }
    }


}
