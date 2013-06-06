package mcross.Graph.Shapes;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import mcross.Graph.CString;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class BaseGraphData {
	
	private CString mName;
	
	public BaseGraphData() {
		mName    = new CString(48);
	}
	
	public void setName(String name) {
		if((name != null) && (name.length() < 48))
			mName.copyString(name);
	}
	
	public void setName(CString name) {
		if((name != null) && (name.getMaxLength() < 48))
			mName = name.clone();
	}
	
	public CString getName() {
		return mName;
	}
	
	public abstract void drawShape(Canvas canvas, Paint brush);
	
	public abstract void scale(float xScale, float yScale);
	
	public abstract void translate(float xTranslate, float yTranslate);
	
	public abstract void rotate(float xRotation, float yRotation);
	
	public abstract BaseGraphData copy();
	
	public abstract float getOriginX();
	
	public abstract float getOriginY();
	
	public abstract void setOriginXY(float xValue, float yValue);
	
	public abstract boolean nearClick(float xPosition, float yPosition);
	
	public abstract void writeToStream(DataOutputStream stream);
	
	public abstract void readFromStream(DataInputStream stream);
	
	public abstract int getTypeId();
}
