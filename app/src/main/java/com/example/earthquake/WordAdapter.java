package com.example.earthquake;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    String primaryLocation;
    String locationOffset;
    private static final String LOCATION_SEPARATOR = " of ";
    public WordAdapter(Activity context, ArrayList<Word> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list, parent, false);
            Word currentword = getItem(position);
            TextView firsttext=(TextView)listItemView.findViewById(R.id.first);
            String formattedMagnitude = formatMagnitude(currentword.getMs1());
            firsttext.setText(formattedMagnitude);
            GradientDrawable magnitudeCircle = (GradientDrawable) firsttext.getBackground();

            // Get the appropriate background color based on the current earthquake magnitude
            int magnitudeColor = getMagnitudeColor(currentword.getMs1());

            // Set the color on the magnitude circle
            magnitudeCircle.setColor(magnitudeColor);


            TextView secondtext=(TextView)listItemView.findViewById(R.id.second);
            TextView locationtext=(TextView)listItemView.findViewById(R.id.locaction);
            String originalLocation =currentword.getMs2();
            if (originalLocation.contains(LOCATION_SEPARATOR)) {
                String[] parts = originalLocation.split(LOCATION_SEPARATOR);
                locationOffset = parts[0] + LOCATION_SEPARATOR;
                primaryLocation = parts[1];
            } else {
                locationOffset = getContext().getString(R.string.near_the);
                primaryLocation = originalLocation;
            }
             secondtext.setText(locationOffset);
            locationtext.setText(primaryLocation);


            TextView thirdtxt=(TextView)listItemView.findViewById(R.id.third);
            Date dateObject = new Date(currentword.getMs3());
            String formattedDate = formatDate(dateObject);
            thirdtxt.setText(formattedDate);
            TextView fourtxt=(TextView)listItemView.findViewById(R.id.four);
            Date timeobj = new Date(currentword.getMs3());
            String formattedTime = formatTime(timeobj);
            fourtxt.setText(formattedTime);
        }
        return listItemView;
    }

    private int getMagnitudeColor(double magnitude) {
            int magnitudeColorResourceId;
            int magnitudeFloor = (int) Math.floor(magnitude);
            switch (magnitudeFloor) {
                case 0:
                case 1:
                    magnitudeColorResourceId = R.color.magnitude1;
                    break;
                case 2:
                    magnitudeColorResourceId = R.color.magnitude2;
                    break;
                case 3:
                    magnitudeColorResourceId = R.color.magnitude3;
                    break;
                case 4:
                    magnitudeColorResourceId = R.color.magnitude4;
                    break;
                case 5:
                    magnitudeColorResourceId = R.color.magnitude5;
                    break;
                case 6:
                    magnitudeColorResourceId = R.color.magnitude6;
                    break;
                case 7:
                    magnitudeColorResourceId = R.color.magnitude7;
                    break;
                case 8:
                    magnitudeColorResourceId = R.color.magnitude8;
                    break;
                case 9:
                    magnitudeColorResourceId = R.color.magnitude9;
                    break;
                default:
                    magnitudeColorResourceId = R.color.magnitude10plus;
                    break;
            }
            return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
        }


    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }
    private String formatTime(Date timeobj) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(timeobj);
    }

}
