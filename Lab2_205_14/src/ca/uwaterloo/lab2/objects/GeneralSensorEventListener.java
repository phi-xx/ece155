package ca.uwaterloo.lab2.objects;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;
import ca.uwaterloo.lab2.objects.LowPassFilter;
import ca.uwaterloo.sensortoy.LineGraphView;

//Takes in the array of MaxAbs vals, and its corresponding text view from the previous classes
public class GeneralSensorEventListener implements SensorEventListener{
	TextView output;
	LineGraphView Graph;
	float[] maxVals;
	TextView[] tvMax;
	
	LowPassFilter X_lowPassFilter;
	LowPassFilter Y_lowPassFilter;
	LowPassFilter Z_lowPassFilter;
	public GeneralSensorEventListener(TextView outputView){
		output = outputView;
	}

	public GeneralSensorEventListener(TextView outputView, 
			LineGraphView outputGraph, 
			float[] MaxVals,
			TextView[] TvMax){
		output = outputView;
		Graph = outputGraph;
		maxVals = MaxVals;
		tvMax = TvMax;
		
		int filterLength = 50;
		X_lowPassFilter = new LowPassFilter(filterLength);
		Y_lowPassFilter = new LowPassFilter(filterLength);
		Z_lowPassFilter = new LowPassFilter(filterLength);

	}
	
	public void onAccuracyChanged(Sensor s, int i){}
	
	public void onSensorChanged(SensorEvent se){

		//Acc Sensor Output
		if(se.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
			
			//apply the low-pass filter
			X_lowPassFilter.AddNewValue(se.values[0]);
			Y_lowPassFilter.AddNewValue(se.values[1]);
			Z_lowPassFilter.AddNewValue(se.values[2]);
			
			//set output
			
		/*	output.setText("Accelerometer: " +
					"\nX: " + String.valueOf(X_lowPassFilter.GetResult()) + 
					"\nY: " + String.valueOf(Y_lowPassFilter.GetResult()) + 
					"\nZ: " + String.valueOf(Z_lowPassFilter.GetResult()) + 
					"\n----------------------------------");
			*/
			
			output.setText("Accelerometer: " +
					"\nX: " + String.valueOf(Z_lowPassFilter.GetPos()) + 
					"\nY: " + String.valueOf(Y_lowPassFilter.GetResult()) + 
					"\nZ: " + String.valueOf(Z_lowPassFilter.GetResult()) + 
					"\n----------------------------------");
	
			
			/*Graph.addPoint(new float[] {X_lowPassFilter.GetResult(),
										Y_lowPassFilter.GetResult(),
										Z_lowPassFilter.GetResult()});*/
			
			Graph.addPoint(new float[] {se.values[2],
										se.values[2],
										Z_lowPassFilter.GetResult()});

			//Checks to see if max value has changed, if so the new value is written into the array
			
			if (Math.abs(X_lowPassFilter.GetResult()) >maxVals[0]){
				maxVals[0] = Math.abs(X_lowPassFilter.GetResult());
			}
			if (Math.abs(Y_lowPassFilter.GetResult()) >maxVals[1]){
				maxVals[1] = Math.abs(Y_lowPassFilter.GetResult());
			}
			if (Math.abs(Z_lowPassFilter.GetResult()) >maxVals[2]){
				maxVals[2] = Math.abs(Z_lowPassFilter.GetResult());
			}
			
			
			//Outputs The First Index in TextViewArray with Max Vals
			tvMax[0].setText("Record Max Vals: \n" + 
					"X: " + maxVals[0] + "\n" + 
					"Y: " + maxVals[1] + "\n" +
					"Z: " + maxVals[2] + "\n" + 
					"---------------------------------\n---------------------------------");

		}


		//Rot Sensor Output
		if(se.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){

			output.setText("Rotation: " +
					"\nX: " + String.valueOf(se.values[0]) + 
					"\nY: " + String.valueOf(se.values[1]) + 
					"\nZ: " + String.valueOf(se.values[2]) + 
					"\n----------------------------------");

			//se.values[0] = xsin(theta/2)

			//Fills MaxVals [6, 7, 8]
			for (int index = 0; index <= 2; index++){
				if (Math.abs(se.values[index]) >maxVals[index+6]){
					maxVals[index+6] = Math.abs(se.values[index]);
				}
			}

			//Graph.addPoint(se.values);
			tvMax[2].setText("Record Max Vals: \n" + 
					"X: " + maxVals[6] + "\n" + 
					"Y: " + maxVals[7] + "\n" +
					"Z: " + maxVals[8] + "\n" + 
					"---------------------------------\n---------------------------------");
		}
	}
}