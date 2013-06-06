package mcross.Graph.Widgets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;

import mcross.Graph.CString;
import mcross.Graph.Shapes.*;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.Log;

public class Graph {

	public static final int LINE    = 0x100;
	public static final int SIMPLEPOINT        = 0x200;
	
	private int mVersionId = 1;
	
	private CString mGraphName;
	
	private float mCenterX  = 0.f;
	private float mCenterY  = 0.f;
	
	private float mRowSpacing = 0.f;
	private float mColSpacing = 0.f;
	
	private float mMaxValueX = 0.f;
	private float mMaxValueY = 0.f;
	
	private float mMinValueX = 0.f;
	private float mMinValueY = 0.f;

	private boolean showGridlines = true;
	private boolean showAxis      = true;
	
	//Variables Not Written To Disk
	private float mWidth    = 0.f;
	private float mHeight   = 0.f;
	
	private float mScaleX    = 0.f;
	private float mScaleY    = 0.f;
	
	private ArrayList<Point> mPointList;
	
	private void initialize() {
		mPointList = new ArrayList<Point>();
		
		mGraphName = new CString(64);
		
		mCenterX   = 0.f;
		mCenterY   = 0.f;
		
		mRowSpacing = 20.f;
		mColSpacing = 20.f;
		
		mMaxValueX  = 0.f;
		mMaxValueY  = 0.f;
		mMinValueX  = 0.f;
		mMinValueY  = 0.f;
		
		showGridlines = true;
		showAxis      = true;
		
		mWidth  = 0.f;
		mHeight = 0.f;
		
		mScaleX = 0.f;
		mScaleY = 0.f;
	}
	
	public Graph() {
		initialize();
	}
	
	public Graph(String graphName, 
				 float minX, float minY, 
				 float maxX, float maxY) 
	{
		initialize();
		
		this.setGraphName(graphName);
		this.setMinValues(minX, minY);
		this.setMaxValues(maxX, maxY);
	}
	
	public void setGraphName(String graphName) {
		if((graphName != null)      && 
		   (graphName.length() > 0) &&
		   (graphName.length() < 64)) 
		{
			mGraphName.copyString(graphName);
		}
	}
	
	public String getGraphName() {
		return mGraphName.getString();
	}
	
	public void setPlotPoints(ArrayList<Point> list) {
		if(list != null && list.size() > 0)
			mPointList = list;
	}
	
	public ArrayList<Point> getPlotPoints() {
		return mPointList;
	}
	
	public float getCarteseanX(float x) {
		return (x - mCenterX);
	}
	
	public float getCarteseanY(float y) {
		return (y - mCenterY);
	}
	
	public float getMaxValueX() {
		return mMaxValueX;
	}
	
	public float getMaxValueY() {
		return mMaxValueY;
	}
	
	public float getMinValueX() {
		return mMinValueX;
	}
	
	public float getMinValueY() {
		return mMinValueY;
	}
	
	public void setSize(float width, float height) {
		mWidth  = width;
		mHeight = height;
	}

	public void setMaxValues(float x, float y) {
		mMaxValueX = x;
		mMaxValueY = y;
	}
	
	public void setMinValues(float x, float y) {
		mMinValueX = x;
		mMinValueY = y;
	}
	
	public void showAxis(boolean flag) {
		showAxis = flag;
	}
	
	public boolean showAxis() {
		return showAxis;
	}
	
	public void showGridLines(boolean flag) {
		showGridlines = flag;
	}
	
	public boolean showGridlines() {
		return showGridlines;
	}

	public void addPoint(Point point) {
		if(point != null)
		{
			mPointList.add(point);
			//calcRangeValues();
		}
	}
	
	public BaseGraphData getShape(int id) {
		if(id < mPointList.size())
			return mPointList.get(id);
		else 
			return null;
	}
	
	public int getSize() {
		return mPointList.size();
	}
	
	public boolean isEmpty() {
		return mPointList.isEmpty();
	}
	
	private void calcScaleValues() {
		if(mMaxValueX != 0)
			mScaleX = (mWidth/(mMaxValueX-mMinValueX));
		
		if(mMaxValueY != 0)
			mScaleY = (mHeight/(mMaxValueY-mMinValueY));
	}
	
	public void calcOrigins() {
		mCenterX = scaleValue(mMinValueX*(-1.f), mScaleX, 0.f);
		mCenterY = scaleValue(mMaxValueY, mScaleY, 0.f);
	}
	
	public void drawShape(Canvas canvas, Paint brush) {
		canvas.drawColor(0xffffffff);
		
		brush.setStyle(Style.STROKE);
		brush.setStrokeWidth(1.f);
		brush.setARGB(175, 120, 120, 120);
		
		calcScaleValues();
		calcOrigins();
		
		if(showGridlines) {	
			mRowSpacing = (mHeight/ (mMaxValueY-mMinValueY));
			mColSpacing = (mWidth/ (mMaxValueX-mMinValueX));
			
			//Draw Horizontal Grid Lines
			float row = mCenterY;	
			for(; row < mHeight; row += mRowSpacing)
				canvas.drawLine(0.f, row, mWidth, row, brush);
			
			for(row=mCenterY; row >= 0.f; row -= mRowSpacing)
				canvas.drawLine(0.f, row, mWidth, row, brush);
			
			//Draw Vertical Grid Lines
			float col = mCenterX;
			for(; col < mWidth; col += mColSpacing)
				canvas.drawLine(col, 0.f, col, mHeight, brush);
	
			for(col=mCenterX; col >= 0.f; col -= mColSpacing)
				canvas.drawLine(col, 0.f, col, mHeight, brush);
		}
		
		if(showAxis) {
			brush.setStrokeWidth(2.f);
			
			//Draw Horizontal Axis
			brush.setARGB(200, 255, 0, 0);
			canvas.drawLine(0.f, mCenterY, mWidth, mCenterY, brush);
			
			float modifier = 15.f;
			
			if(mMinValueY <= 0.f)
				modifier = -15.f;
			
			canvas.drawText(String.format("%.0f", mMaxValueX), 
							(mWidth-20.f),
							(mCenterY+modifier), 
							brush);
			
			canvas.drawText(String.format("%.0f", mMinValueX), 
						   (mMinValueX*(-1.f)), 
						   (mCenterY+15.f),
						   brush);
			
			//Draw Vertical Axis
			brush.setARGB(200, 0, 255, 0);
			canvas.drawLine(mCenterX, 0.f, mCenterX, mHeight, brush);
			
			canvas.drawText(String.format("%.2f", mMaxValueY), 
							(mCenterX+5.f), 20.f, brush);
			
			canvas.drawText(String.format("%.2f", mMinValueY), 
							(mCenterX+5.f), (mHeight-10.f), brush);
		}
		
		plotShapes(canvas, brush);
	}
	
	private void plotShapes(Canvas canvas, Paint brush) {
		
		brush.setStrokeWidth(2.f);
		
	    Path  path = new Path();
	    Point temp = null;
	    
	    boolean first = true;
	    
	    int size = mPointList.size();
	    
	    for(int i = 0; i < size; i++){
	        temp = mPointList.get(i).copy();
			
			//Scale Shape
			temp.scale(mScaleX, (mScaleY * -1.f));
			
			//Translate Shape
			temp.translate(mCenterX, mCenterY);
	        
	        if(first){
	            first = false;
	            path.moveTo(temp.getOriginX(), temp.getOriginY());
	        }

	        else if(++i < size){
	            Point next = mPointList.get(i).copy();
				
				//Scale Shape
				next.scale(mScaleX, (mScaleY * -1.f));
				
				//Translate Shape
				next.translate(mCenterX, mCenterY);
				
				next.drawShape(canvas, brush);
				
	            path.quadTo(temp.getOriginX(), temp.getOriginY(), 
	            			next.getOriginX(), next.getOriginY());
	        }
	        else{
	            path.lineTo(temp.getOriginX(), temp.getOriginY());
	        }
	        
		    temp.drawShape(canvas, brush);	
	    }

		brush.setStyle(Style.STROKE);
	    canvas.drawPath(path, brush);
	}
	
	private float scaleValue(float value, float scale, float offset) {
		return ((value*scale)+offset);
	}
	
	public float scaleX(float value) {
		return ((value*mScaleX)+mCenterX);
	}
	
	public float getScaleX() {
		return mScaleX;
	}
	
	public float scaleY(float value) {
		return ((value*mScaleY)+mCenterY);
	}
	
	public float getScaleY() {
		return mScaleY;
	}
	
	public void clearShapes() {
		mPointList.clear();
	}
	
	public void zoomGraph(boolean zoomIn) {
		if(zoomIn) {
			mMinValueX -= (mMinValueX/10.f);
			mMinValueY -= (mMinValueY/10.f);

			mMaxValueX -= (mMaxValueX/10.f);
			mMaxValueY -= (mMaxValueY/10.f);
		}
		else {
			mMinValueX += (mMinValueX/10.f);
			mMinValueY += (mMinValueY/10.f);

			mMaxValueX += (mMaxValueX/10.f);
			mMaxValueY += (mMaxValueY/10.f);
		}
	}
	
	public BaseGraphData isNearClick(float x, float y) {
		BaseGraphData data = null;
		
		int size = mPointList.size();
		
		boolean found = false;
		
		float scaledX = ((x - mCenterX) / mScaleX);
		float scaledY = ((mCenterY - y) / mScaleY);
		
		for(int index=0; index < size; index++) {
			data = mPointList.get(index);

			if(data.nearClick(scaledX, scaledY)) {
				found = true;
				break;
			}
		}
		
		if(!found)
			data = null;
		
		return data;
	}
	
	public void writeToStream(DataOutputStream stream) throws Exception {
		if(stream != null) {
			try {
				stream.writeInt(mVersionId);
				
				mGraphName.writeArray(stream);
				
				stream.writeFloat(mCenterX);
				stream.writeFloat(mCenterY);
				
				stream.writeFloat(mMaxValueX);
				stream.writeFloat(mMaxValueY);

				stream.writeFloat(mMinValueX);
				stream.writeFloat(mMinValueY);
				
				stream.writeBoolean(showAxis);
				stream.writeBoolean(showGridlines);
				
				int numOfElements = mPointList.size();
				Point object = null;
				
				stream.writeInt(numOfElements);
				
				for(int index=0; index < numOfElements; index++) {
					object = mPointList.get(index);
					object.writeToStream(stream);
				}
			}
			catch(Exception error) {
				error.printStackTrace();
				Log.e("Plotrix", error.getMessage());
			}
		}
		else {
			throw new Exception("Cannot write Graph to stream. Stream is null");
		}
	}
	
	public void readFromStream(DataInputStream stream) throws Exception {
		if(stream != null) {
			try {
				mVersionId = stream.readInt();
				mGraphName.readArray(stream);
				
				mCenterX = stream.readFloat();
				mCenterY = stream.readFloat();
				
				mMaxValueX  = stream.readFloat();
				mMaxValueY  = stream.readFloat();

				mMinValueX  = stream.readFloat();
				mMinValueY  = stream.readFloat();
				
				showAxis = stream.readBoolean();
				showGridlines = stream.readBoolean();
				
				int numOfElements = stream.readInt();
				Point object = null;
				
				for(int index=0; index < numOfElements; index++) {
					object = readPointFromStream(stream);
					
					mPointList.add(object);
				}
			}
			catch(Exception error) {
				error.printStackTrace();
				Log.e("Plotrix", error.getMessage());
			}
		}
		else {
			throw new Exception("Cannot read Graph from stream. Stream is null");
		}
	}
	
	public Point readPointFromStream(DataInputStream stream) throws Exception {
		Point point = new Point();
		
		point.readFromStream(stream);
		
		return point;
	}
}
