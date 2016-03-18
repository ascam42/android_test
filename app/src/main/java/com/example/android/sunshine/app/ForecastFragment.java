package com.example.android.sunshine.app;
/*
**
** Created by   <ungaro_l@epitech.eu>
** For          Sunshine-Version-2
**
** Started on   3/16/16 6:02 PM
**
*/

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ForecastFragment extends Fragment
{

	public ForecastFragment()
	{
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View		rootView = inflater.inflate(R.layout.fragment_main, container, false);
		ListView	list_view = null;
		String		weather_data = null;

		try
		{
			weather_data = new WebRetriever().execute("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7").get();
		}
		catch (Exception e)
		{
			Log.e("WebRetriever", "Error", e);
		}

		String[] forecast_array = new String[]{"Today-Sunny-88/63", "Today-Sunny-88/63", "Today-Sunny-88/63", "Today-Sunny-88/63", "Today-Sunny-88/63", "Today-Sunny-88/63", "Today-Sunny-88/63", "Today-Sunny-88/63", "Today-Sunny-88/63", "Today-Sunny-88/63", "Today-Sunny-88/63", "Today-Sunny-88/63", "Today-Sunny-88/63", "Today-Sunny-88/63", "Today-Sunny-88/63", "Today-Sunny-88/63", "Today-Sunny-88/63", "Today-Sunny-88/63"};
		List<String> fake_data = new ArrayList<String>(Arrays.asList(forecast_array));

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast, // file
				R.id.list_item_forecast_textview, // XML id
				fake_data);
		list_view = (ListView) rootView.findViewById(R.id.listview_forecast);
		list_view.setAdapter(adapter);

		return rootView;
	}

	private class WebRetriever extends AsyncTask<String, Void, String>
	{
		@Override
		protected String doInBackground(String... url_param)
		{
			HttpURLConnection urlConnection = null;
			BufferedReader reader = null;
			String ret = null;

			InputStream stream = null;
			StringBuilder buffer = new StringBuilder();
			String line = null;

			try
			{
				URL url = new URL(url_param[0]);

				urlConnection = (HttpURLConnection) url.openConnection();
				urlConnection.setRequestMethod("GET");
				urlConnection.connect();
				stream = urlConnection.getInputStream();
				if (stream != null)
				{
					reader = new BufferedReader(new InputStreamReader(stream));
					while ((line = reader.readLine()) != null)
					{
						buffer.append(line);
						buffer.append("\n");
					}
					if (buffer.length() != 0)
						ret = buffer.toString();
				}
			} catch (IOException ex)
			{
				Log.e("WebRetriever", "Error", ex);
				return (null);
			} finally
			{
				if (urlConnection != null)
					urlConnection.disconnect();
				if (reader != null)
				{
					try
					{
						reader.close();
					} catch (final IOException ex)
					{
						Log.e("Webretriever", "Error closing stream", ex);
					}
				}
			}
			return (ret);
		}
	}

}
