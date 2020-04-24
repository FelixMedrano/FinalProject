package com.example.finalproject.ui.home;

import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class HomeFragment extends Fragment {


    private HomeViewModel homeViewModel;
    private TextView welcome,instructions;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        //end of textbox
        return root;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //associate variable with widget
        welcome=view.findViewById(R.id.Welcome);
        instructions=view.findViewById(R.id.Instructions);
        //Set string using html
       String Welcome="<b>"+"Welcome! to my Movies App"+"</b>";
       //Paste using html
       welcome.setText(Html.fromHtml(Welcome));
       //Some instructions
       String Instructions="Welcome to my movies App! \n Here you will be able to type in the title of a movie and be able to see the original release date, \n" +
               "the awards given to the movie, and the Imdb Rating of this movie as well as a picture of its poster.\n"+"Furthermore You will have the opportunity to save your favorites. \n" +
               "Feel free to login using your own Google account";
       //setting them as as HTML helps format it better
       instructions.setText(Html.fromHtml(Instructions));

    }
}
