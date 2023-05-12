package com.uthai.uthaitask;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList;
    Context context1;
    public UserAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context1=context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_layout, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);

        holder.textUsername.setText(user.getUsername());
        holder.textEmail.setText(user.getEmail());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context1, UserInfoActivity.class);
                intent.putExtra("id",user.getId());
                intent.putExtra("name",user.getUsername());
                intent.putExtra("email",user.getEmail());
                intent.putExtra("street",user.getAddress().getStreet() );
                intent.putExtra("city",user.getAddress().getCity() );
                intent.putExtra("suite",user.getAddress().getSuite() );
                intent.putExtra("zipcode",user.getAddress().getZipcode() );
                intent.putExtra("phoneno",user.getPhone() );
                intent.putExtra("cname",user.getCompany().getName() );
                intent.putExtra("website",user.getWebsite() );
            context1.startActivity(intent);
            }
        });
        holder.textEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + user.getEmail()));
                intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
                intent.putExtra(Intent.EXTRA_TEXT, "Hy uthai test mail");
               context1.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textUsername;
        TextView textEmail;
LinearLayout linearLayout;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textUsername = itemView.findViewById(R.id.text_username);
            textEmail = itemView.findViewById(R.id.text_email);
            linearLayout = itemView.findViewById(R.id.text_layout);
        }
    }
}
