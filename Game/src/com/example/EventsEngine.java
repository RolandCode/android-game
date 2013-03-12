package com.example;

import android.view.*;

public class EventsEngine
{
    public interface FingersAct{
		public void onActDown(int index);
        public void onActUp(int index);		
	}
	
	public FingersAct fingersAct;
	
	public EventsEngine(){
		fingersAct = new FingersAct(){
			public void onActDown(int index){}
			public void onActUp(int index){}
		};
	} 
	
	public void setFingersActions(FingersAct fingersAct){
		this.fingersAct = fingersAct;
	}
	
	public boolean aliveID(int ID){
		if(ID == id[0] || ID == id[1] || ID == id[2] || ID == id[3] || ID == id[4]){
			return true;
		}
		return false;
	}
	
    MotionEvent e;
    public float[] x = {0, 0, 0, 0, 0};
    public float[] y = {1, 0, 0, 0, 0};
	public int[] id = {0, 0, 0, 0, 0};
	public void turnON(MotionEvent e){
		if(e != null){			
	    	for(int i = 0; i < x.length; i++){
				if(i < e.getPointerCount()){
				     	id[i] = e.getPointerId(i);
						x[id[i]] = e.getX(i);
	    	           	y[id[i]] = e.getY(i);
				}
				else{
					id[i] = 8;
				}
				
			//	if(e.getPointerCount() == 1)id[0]=0;
		
			}
			
			switch (e.getActionMasked()){
				case MotionEvent.ACTION_POINTER_DOWN:
				     switch(e.getActionIndex()){				
					     case 0:
						     fingersAct.onActDown(0);
							 break;
						 case 1:
						     fingersAct.onActDown(1);
							 break;
						 case 2:
						     fingersAct.onActDown(2);
							 break;
						 case 3:
						     fingersAct.onActDown(3);
							 break;
						 case 4:
						     fingersAct.onActDown(4);
							 break;
					 }
				     break;
					 
				case MotionEvent.ACTION_POINTER_UP:
					switch(e.getPointerId(e.getActionIndex())){
						case 0:
						    fingersAct.onActUp(0);
							break;
						case 1:
							fingersAct.onActUp(1);
							break;
						case 2:
							fingersAct.onActUp(2);
							break;
						case 3:
							fingersAct.onActUp(3);
							break;
						case 4:
							fingersAct.onActUp(4);
							break;
					}
					break;
					
				case MotionEvent.ACTION_DOWN:
					fingersAct.onActDown(0);
					break;
					
				case MotionEvent.ACTION_UP:
		     		id[id[0]]=8;
					switch(e.getPointerId(0)){
						case 0:
							fingersAct.onActUp(0);
							break;
						case 1:
							fingersAct.onActUp(1);
							break;
						case 2:
							fingersAct.onActUp(2);
							break;
						case 3:
							fingersAct.onActUp(3);
							break;
						case 4:
							fingersAct.onActUp(4);
							break;
					}
					break;
			}
		}
	}
}
