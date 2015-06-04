package ca.uwaterloo.lab2.objects;

public class LowPassFilter{
	private float currentValue = 0;
	private float[] filteringValue;
	private float coe;
	private int filterLength;
	
	public LowPassFilter(int Length){
		currentValue = (float)0;
		filterLength = Length;
		filteringValue = new float[filterLength];

		
		for (int i=0;i<filterLength;i++){
			filteringValue[i] = 0;
		}
	};
	
	public float GetResult(){
		return currentValue;
	}
	
	int overlapPosition = filterLength;
	
	public void AddNewValue(float NewValue){
		for(int i = 0; i < filterLength - 1; i++){
			filteringValue[i] = filteringValue[i+1];
		}
		filteringValue[filterLength-1] = NewValue;

		float sum = 0;
		for(int i = 0; i < filterLength - 1; i++){
			sum += (filteringValue[i+1] - filteringValue[i]);
		}
		float avg = sum/(filterLength-1);
		
		
		currentValue = currentValue + coe*(filteringValue[1] - filteringValue[0]);
		currentValue = currentValue + avg;
	};
	
	public float GetPos(){
		return coe;
	}
	
}