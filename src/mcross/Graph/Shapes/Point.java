package mcross.Graph.Shapes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mcross.Graph.Widgets.Graph;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;

public class Point extends BaseGraphData {
	private float x, y;
	
	public Point() {
		this.x = 0.0f;
		this.y = 0.0f;
	}
	
	public Point(String name, float xPos, float yPos) {
		this.x = xPos;
		this.y = yPos;
		setName(name);
	}
	
	public Point(Point point) {
		this.x = point.x;
		this.y = point.y;
		setName(point.getName());
	}
	
	public void setOriginXY(float xPos, float yPos) {
		this.x = xPos;
		this.y = yPos;
	}
	
	public float getOriginX() {
		return x;
	}
	
	public void setX(float xPos) {
		x = xPos;
	}
	
	public float getOriginY() {
		return y;
	}
	
	public void setY(float yPos) {
		y = yPos;
	}
	
	public void clear() {
		x = 0.0f;
		y = 0.0f;
		setName("");
	}
	
	public void drawShape(Canvas canvas, Paint brush) {
		brush.setStrokeWidth(10.f);
		brush.setStyle(Style.STROKE);
		brush.setARGB(255, 40, 40, 255);
		
		canvas.drawPoint(x, y, brush);
		
		brush.setTextSize(12.0f);
		brush.setStyle(Style.FILL);
		brush.setStrokeWidth(1.0f);
		canvas.drawText(getName().getString(), (x+20.0f), (y+20.0f), brush);
	}
	
	public Point copy() {
		Point point = new Point();
		
		point.x = this.x;
		point.y = this.y;
		
		point.setName(getName());
		
		return point;
	}

	@Override
	public void scale(float xScale, float yScale) {
		this.setOriginXY((x*xScale), (y*yScale));
	}

	@Override
	public void translate(float xTranslate, float yTranslate) {
		this.setOriginXY((x+xTranslate), (y+yTranslate));
	}

	@Override
	public void rotate(float xRotation, float yRotation) {
		//Rotation to a point
	}
	
	@Override
	public boolean nearClick(float xPosition, float yPosition) {
		boolean ret = false;
		
		float deltaX = Math.abs(xPosition - x);
		float deltaY = Math.abs(yPosition - y);
		
		if((deltaX < 10.f) && (deltaY < 10.f))
			ret = true;
		
		return ret;
	}

	@Override
	public void writeToStream(DataOutputStream stream) {
		if(stream != null) {
			try {
				getName().writeArray(stream);
				stream.writeFloat(this.x);
				stream.writeFloat(this.y);
			}
			catch(IOException error) {
				error.printStackTrace();
				Log.e("Plotrix", error.getMessage());
			}
		}
	}

	@Override
	public void readFromStream(DataInputStream stream) {
		if(stream != null) {
			try {
				getName().readArray(stream);
				this.x = stream.readFloat();
				this.y = stream.readFloat();
			}
			catch(IOException error) {
				error.printStackTrace();
				Log.e("Plotrix", error.getMessage());
			}
		}
	}
	
	@Override
	public int getTypeId() {
		return Graph.SIMPLEPOINT;
	}
}
