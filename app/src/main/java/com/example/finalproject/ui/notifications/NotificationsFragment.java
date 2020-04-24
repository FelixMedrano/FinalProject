package com.example.finalproject.ui.notifications;


import android.os.Build;
import android.os.Bundle;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.finalproject.GlobalVariables;
import com.example.finalproject.R;
import com.squareup.picasso.Picasso;



public class NotificationsFragment extends Fragment {
    private TextView Title,fav1,fav2,fav3;
    Button delete1,delete2,delete3;
     ImageView poster1, poster2, poster3;
     String URL1,URL2,URL3;





    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                          ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Setting title of the fragment
        Title=view.findViewById(R.id.FavTitle);
        String title="<b>"+"Favorites"+"</b>";
         Title.setText(Html.fromHtml(title));
         //get poster id
        poster1=view.findViewById(R.id.Poster1);
        poster2=view.findViewById(R.id.Poster2);
        poster3=view.findViewById(R.id.Poster3);
        //get texview id
        fav1=view.findViewById(R.id.Fav1);
        fav2=view.findViewById(R.id.Fav2);
        fav3=view.findViewById(R.id.Fav3);
        //get button id
        delete1=view.findViewById(R.id.Delete1);
        delete2=view.findViewById(R.id.Delete2);
        delete3=view.findViewById(R.id.Delete3);
        //listen to if a button is pushed
        delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete1();
            }
        });
        delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete2();
            }
        });
        delete3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete3();
            }
        });

         //Setting all the default images to nothing until the poster is set.
        poster1.setVisibility(View.GONE);
        poster2.setVisibility(View.GONE);
        poster3.setVisibility(View.GONE);
        //Global variables
        GlobalVariables globalVariables = (GlobalVariables) getActivity().getApplicationContext();
        //set the textviews to the title

            fav1.setText(globalVariables.getMtitle1());
            fav2.setText(globalVariables.getMtitle2());
            fav3.setText(globalVariables.getMtitle3());

            //set the variables to the url of the posters
            URL1=globalVariables.getMPoster1();
            URL2=globalVariables.getMPoster2();
            URL3=globalVariables.getMPoster3();
            //set posters to their image
            Picasso.get().load(URL1).into(poster1);
            Picasso.get().load(URL2).into(poster2);
            Picasso.get().load(URL3).into(poster3);
            //make them visible
            poster1.setVisibility(View.VISIBLE);
            poster2.setVisibility(View.VISIBLE);
            poster3.setVisibility(View.VISIBLE);

    }


    public void Delete1() {
        GlobalVariables globalVariables=(GlobalVariables)getActivity().getApplicationContext();
        //Make the posters invisible
        poster1.setVisibility(View.GONE);
        //delete the global variables' value
        globalVariables.setMtitle1(null);
        globalVariables.setMPoster1(null);
        //reset counter
        globalVariables.setCounter("1");
        //refresh fragment
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }
    public void Delete2() {
        GlobalVariables globalVariables=(GlobalVariables)getActivity().getApplicationContext();
        //Make the posters invisible
        poster2.setVisibility(View.GONE);
        //delete the global variables' value
        globalVariables.setMtitle2(null);
        globalVariables.setMPoster2(null);
        //reset counter
        globalVariables.setCounter("2");
        //refresh fragment
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();

    }
    public void Delete3() {
        GlobalVariables globalVariables=(GlobalVariables)getActivity().getApplicationContext();
        //Make the posters invisible
        poster3.setVisibility(View.GONE);
        //delete the global variables' value
        globalVariables.setMtitle3(null);
        globalVariables.setMPoster3(null);
        //reset counter
        globalVariables.setCounter("3");
        //refresh fragment
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();

    }

}

