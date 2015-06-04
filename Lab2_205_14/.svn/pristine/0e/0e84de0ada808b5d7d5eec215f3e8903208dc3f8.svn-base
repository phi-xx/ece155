package ca.uwaterloo.lab2;


import java.util.Arrays;

import android.app.Activity;
import android.app.Fragment;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import ca.uwaterloo.sensortoy.LineGraphView;
import ca.uwaterloo.lab2.objects.GeneralSensorEventListener;


public class MainActivity extends Activity {

	static LineGraphView graph;
	static Button resetButton;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}

		graph = new LineGraphView(getApplicationContext(),100, Arrays.asList("x","y","z"));
		graph.setVisibility(View.VISIBLE);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */

	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}


		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			TextView tv = (TextView) rootView.findViewById(R.id.label1);

			//Initializes Text View [Not Needed]
			tv.setText("Reading Sensors......");

			//Array to hold the printing of the x,y,z data from sensors
			TextView[] tvMax = new TextView[3];

			//Initializes max-value TextView Array
			for(int i = 0; i < 3; i++){
				tvMax[i] = new TextView(rootView.getContext());
			}


			//Holds Abs Max of X Y Z from 3 Sensors (array of size 9)
			//Acceleration x, y, z || Magnetic x, y, z || Rotational x, y, z
			float[] maxVals = new float[]{0,0,0,0,0,0,0,0,0};

			//Use valIndex later in labs 2++
			int valIndex = 0;
			for(int i = 0; i < 3; i++){
				tvMax[i].setText("Record Max Vals: \n" + 
						"X: " + maxVals[valIndex] + "\n" + 
						"Y: " + maxVals[valIndex + 1] + "\n" +
						"Z: " + maxVals[valIndex + 2] + "\n" + 
						"---------------------------------\n---------------------------------");
				valIndex ++;
			}

			//set the reset button
			resetButton = (Button) rootView.findViewById(R.id.ResetButton);
			OnClickListener resetButtonListener = new ResetButtonListener(maxVals);
			resetButton.setOnClickListener(resetButtonListener);
			
			//Consider making this just 1 TextView

			TextView AccText = new TextView(rootView.getContext());
			AccText.setText("this is AccText");

			TextView RotText = new TextView(rootView.getContext());
			RotText.setText("this is RotText");


			//Sets up l as a linear layout
			LinearLayout l = (LinearLayout) rootView.findViewById(R.id.LinearLabel);

			//Fills out Layout
			l.addView(graph);
			l.addView(AccText);
			l.addView(tvMax[0]);
			l.addView(RotText);
			l.addView(tvMax[2]);
			l.setOrientation(LinearLayout.VERTICAL);

			//Setting up sensor manager
			SensorManager sensorManager = (SensorManager) 
					rootView.getContext().getSystemService(SENSOR_SERVICE);

			
			//Initializing 4 Event Listeners for 4 main sensors (light, acc, mag, rot)
			
			SensorEventListener accListener = new GeneralSensorEventListener(AccText, graph, maxVals, tvMax);
			
			SensorEventListener rotListener = new GeneralSensorEventListener(RotText, graph, maxVals, tvMax);
			
			
			
			Sensor accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

			Sensor rotSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

		
			sensorManager.registerListener(accListener, accSensor, SensorManager.SENSOR_DELAY_FASTEST);

			sensorManager.registerListener(rotListener, rotSensor, SensorManager.SENSOR_DELAY_FASTEST);

			return rootView;
		}
	}
}



class ResetButtonListener implements OnClickListener{
	float[] resetVals;
	
	ResetButtonListener(float[] ResetVals){
		resetVals = ResetVals;
	}
	public void onClick(View v){
		for (int i = 0; i < resetVals.length; i++){
			resetVals[i] = 0;
		}
	}
};

