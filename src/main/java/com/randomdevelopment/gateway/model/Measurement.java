package com.randomdevelopment.gateway.model;

public class Measurement {
	private long time;
	private double value;
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
	public static double[] getValues(Measurement[] measurements) {
		double[] values = new double[measurements.length];
		for(int i=0; i<measurements.length; i++) {
			values[i] = measurements[measurements.length-i-1].getValue();
		}
		return values;
	}
	
	public static long[] getTimes(Measurement[] measurements) {
		long[] times = new long[measurements.length];
		for(int i=0; i<measurements.length; i++) {
			times[i] = measurements[measurements.length-i-1].getTime();
		}
		return times;
	}
	
}
