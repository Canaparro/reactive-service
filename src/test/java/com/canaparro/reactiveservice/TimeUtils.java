package com.canaparro.reactiveservice;

public class TimeUtils {
	public TimeUtils() {}

	public static void sleepSeconds( final int seconds ) {
		try {
			Thread.sleep( seconds * 1000 );
		} catch ( InterruptedException e ) {
			throw new RuntimeException( e );
		}
	}
}