package com.example.finalproject.ui.dashboard;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.GlobalVariables;
import com.example.finalproject.R;
import com.example.finalproject.ui.notifications.NotificationsFragment;
import com.squareup.picasso.Picasso;


import org.json.JSONException;
import org.json.JSONObject;

public class DashboardFragment extends Fragment {
    public static final String TAG = "MyTag";
    StringRequest stringRequest; // Assume this exists.
    RequestQueue requestQueue;  // Assume this exists.
    private com.example.finalproject.ui.dashboard.DashboardViewModel DashboardViewModel;
    private EditText editText;
    private TextView inputTextView,savFav;
    private ImageView Poster;
    private ImageButton Fav;
    String i;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //DashboardViewMOdel allows me to write onto the xml file from the fragment
        DashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.Display);
        DashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Set variables to their appropriate xml file counterparts
        inputTextView = view.findViewById(R.id.Display);
        Fav=view.findViewById(R.id.FavButton);
        //Text above star for favorite
        savFav=view.findViewById(R.id.saveFav);
        savFav.setVisibility(View.GONE);
        //favbutton invisible
        Fav.setVisibility(View.GONE);
        editText = view.findViewById(R.id.editText);
        Poster=view.findViewById(R.id.Poster);
        //Made this placeholder widget invisible so that the User doesnt have to stare at the placeholder I chose.
        Poster.setVisibility(View.GONE);
        //Button when pushed will start everything
        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Movie is the user input, it is being checked as to if the input was empty or not
                String Movie = editText.getText().toString();
                if (Movie.isEmpty()) {
                    inputTextView.setText("Please enter a Movie");
                    Poster.setVisibility(View.GONE);
                    savFav.setVisibility(View.GONE);
                    Fav.setVisibility(View.GONE);
                } else {


                    //Portions of the URL to be used in the JSON functions
                    String url = "http://www.omdbapi.com/?apikey=7efb670f&t=";

                    String Finalurl = url + Movie;
                    //old test function to see it it worked
                    // inputTextView.setText(FinalURL);
                    //Instantiate RequestQueue
                    requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                    //Request String

                    stringRequest = new StringRequest(Request.Method.GET, Finalurl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Display the first 500 characters of the response string.
                                    inputTextView.setText("Response is: "+ response);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            inputTextView.setText("That didn't work!");
                        }

                    });
                    // Set the tag on the request.
                    stringRequest.setTag(TAG);

                    // Add the request to the RequestQueue.
                    requestQueue.add(stringRequest);

                    if (requestQueue != null) {
                        requestQueue.cancelAll(TAG);
                    }
                    GlobalVariables globalVariables=(GlobalVariables)getActivity().getApplicationContext();
                    //initiated global variable in GlobalVariables. This with the if statements will cycle through the same jsonObjectRequest
                    //With different calls to set the other global variables, allowing favorites to be cycled through

                    i=globalVariables.getCounter();
                    //if global counter is 1
                        if(i=="1") {
                            //JSONObjectRequest that retrieves the right information
                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Finalurl, null, new Response.Listener<JSONObject>() {


                                @Override

                                public void onResponse(JSONObject response) {

                                    inputTextView.setText("Response: " + response.toString());


                                    //Get city Name

                                    try {

                                        //Get the right information, used Postman to see what was returned
                                        final String FavTitle = response.getString("Title");
                                        String Title = response.getString("Title");
                                        String Awards = response.getString("Awards");
                                        String IMDBR = response.getString("imdbRating");
                                        String Released = response.getString("Released");
                                        final String Favposter = response.getString("Poster");
                                        String poster = response.getString("Poster");
                                        //Use HTML to make certain words bold, easier for the user to see and read the screen
                                        String FinalString = "<b>" + "Release Date: " + "</b>" + Released + "<b>" + "\nAwards: " + "</b>" + Awards + "<b>" + "\nIMDB Rating: " + "</b>" + IMDBR;

                                        //Set the source of the imageView named Poster to the poster URL using Picasso. Added the appropriate code i the manifest
                                        Picasso.get().load(poster).into(Poster);
                                        //Made the image visible so the poster is visible
                                        Poster.setVisibility(View.VISIBLE);
                                        //set the textView to the above html
                                        inputTextView.setText(Html.fromHtml(FinalString));
                                        Fav.setVisibility(View.VISIBLE);
                                        savFav.setVisibility(View.VISIBLE);

                                        Fav.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                setGlobal(FavTitle, Favposter);
                                                GlobalVariables globalVariables=(GlobalVariables)getActivity().getApplicationContext();
                                                globalVariables.setCounter("2");


                                            }
                                        });


                                    } catch (JSONException e) {
                                        //Do not use this for a real application
                                        inputTextView.setText("Movie not found, please check spelling");
                                        e.printStackTrace();
                                    }


                                }

                            }, new Response.ErrorListener() {
                                //To tell you theres been an error. I setText to error.getMessage() in order to see the ClearText Traffic error
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO: Handle error
                                    inputTextView.setText("That didn't work ");
                                }
                            });
                            //***********************************end of request
                            // Access the RequestQueue through your singleton class.
                            MySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(jsonObjectRequest);
                        }
                        //if global counter is 3
                       if(i=="3"){
                           //JSONObjectRequest that retrieves the right information
                           JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Finalurl, null, new Response.Listener<JSONObject>() {


                               @Override

                               public void onResponse(JSONObject response) {

                                   inputTextView.setText("Response: " + response.toString());


                                   //Get city Name

                                   try {

                                       //Get the right information, used Postman to see what was returned
                                       final String FavTitle = response.getString("Title");
                                       String Title = response.getString("Title");
                                       String Awards = response.getString("Awards");
                                       String IMDBR = response.getString("imdbRating");
                                       String Released = response.getString("Released");
                                       final String Favposter = response.getString("Poster");
                                       String poster = response.getString("Poster");
                                       //Use HTML to make certain words bold, easier for the user to see and read the screen
                                       String FinalString = "<b>" + "Release Date: " + "</b>" + Released + "<b>" + "\nAwards: " + "</b>" + Awards + "<b>" + "\nIMDB Rating: " + "</b>" + IMDBR;

                                       //Set the source of the imageView named Poster to the poster URL using Picasso. Added the appropriate code i the manifest
                                       Picasso.get().load(poster).into(Poster);
                                       //Made the image visible so the poster is visible
                                       Poster.setVisibility(View.VISIBLE);
                                       //set the textView to the above html
                                       inputTextView.setText(Html.fromHtml(FinalString));
                                       Fav.setVisibility(View.VISIBLE);
                                       savFav.setVisibility(View.VISIBLE);

                                       Fav.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               setGlobal3(FavTitle, Favposter);
                                               GlobalVariables globalVariables=(GlobalVariables)getActivity().getApplicationContext();
                                               globalVariables.setCounter("1");

                                           }
                                       });


                                   } catch (JSONException e) {
                                       //Do not use this for a real application
                                       inputTextView.setText("Movie not found, please check spelling");
                                       e.printStackTrace();
                                   }


                               }

                           }, new Response.ErrorListener() {
                               //To tell you theres been an error. I setText to error.getMessage() in order to see the ClearText Traffic error
                               @Override
                               public void onErrorResponse(VolleyError error) {
                                   // TODO: Handle error
                                   inputTextView.setText("That didn't work ");
                               }
                           });
                           //***********************************end of request
                           // Access the RequestQueue through your singleton class.
                           MySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(jsonObjectRequest);
                       }
                       //if global counter is 2
                       if(i=="2"){
                           //JSONObjectRequest that retrieves the right information
                           JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Finalurl, null, new Response.Listener<JSONObject>() {


                               @Override

                               public void onResponse(JSONObject response) {

                                   inputTextView.setText("Response: " + response.toString());


                                   //Get city Name

                                   try {

                                       //Get the right information, used Postman to see what was returned
                                       final String FavTitle = response.getString("Title");
                                       String Title = response.getString("Title");
                                       String Awards = response.getString("Awards");
                                       String IMDBR = response.getString("imdbRating");
                                       String Released = response.getString("Released");
                                       final String Favposter = response.getString("Poster");
                                       String poster = response.getString("Poster");
                                       //Use HTML to make certain words bold, easier for the user to see and read the screen
                                       String FinalString = "<b>" + "Release Date: " + "</b>" + Released + "<b>" + "\nAwards: " + "</b>" + Awards + "<b>" + "\nIMDB Rating: " + "</b>" + IMDBR;

                                       //Set the source of the imageView named Poster to the poster URL using Picasso. Added the appropriate code i the manifest
                                       Picasso.get().load(poster).into(Poster);
                                       //Made the image visible so the poster is visible
                                       Poster.setVisibility(View.VISIBLE);
                                       //set the textView to the above html
                                       inputTextView.setText(Html.fromHtml(FinalString));
                                       Fav.setVisibility(View.VISIBLE);
                                       savFav.setVisibility(View.VISIBLE);

                                       Fav.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               setGlobal2(FavTitle, Favposter);
                                               GlobalVariables globalVariables=(GlobalVariables)getActivity().getApplicationContext();
                                               globalVariables.setCounter("3");

                                           }
                                       });


                                   } catch (JSONException e) {
                                       //Do not use this for a real application
                                       inputTextView.setText("Movie not found, please check spelling");
                                       e.printStackTrace();
                                   }


                               }

                           }, new Response.ErrorListener() {
                               //To tell you theres been an error. I setText to error.getMessage() in order to see the ClearText Traffic error
                               @Override
                               public void onErrorResponse(VolleyError error) {
                                   // TODO: Handle error
                                   inputTextView.setText("That didn't work ");
                               }
                           });
                           //***********************************end of request
                           // Access the RequestQueue through your singleton class.
                           MySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(jsonObjectRequest);
                       }







                }
            }


        });
    }
    public void setGlobal(String title, String poster){
                    //Tell user it was saved
                Toast toast = Toast.makeText(getContext(),"Marked as favorite", Toast.LENGTH_LONG);
                toast.show();
                //access global variables
                GlobalVariables globalVariables=(GlobalVariables)getActivity().getApplicationContext();
                //set variables
                globalVariables.setMtitle1(title);
                globalVariables.setMPoster1(poster);


    }
    public void setGlobal2(String title, String poster){
        //Tell user it was saved
        Toast toast = Toast.makeText(getContext(),"Marked as favorite ", Toast.LENGTH_LONG);
        toast.show();
        //access global variables
        GlobalVariables globalVariables=(GlobalVariables)getActivity().getApplicationContext();
        //set variables
        globalVariables.setMtitle2(title);
        globalVariables.setMPoster2(poster);

    }
    public void setGlobal3(String title, String poster){
        //Tell user it was saved
        Toast toast = Toast.makeText(getContext(),"Marked as favorite ", Toast.LENGTH_LONG);
        toast.show();
        //access global variables
        GlobalVariables globalVariables=(GlobalVariables)getActivity().getApplicationContext();
        //set variables
        globalVariables.setMtitle3(title);
        globalVariables.setMPoster3(poster);
    }



    }

