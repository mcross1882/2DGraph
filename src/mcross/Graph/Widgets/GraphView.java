package mcross.Graph.Widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GraphView extends View {
	
	private Paint mBrush;

	private Graph mGraph;
	
	private boolean mIsPreview = false;
	
	public GraphView(Context ct) {
		super(ct);
		init();
	}
	
	public GraphView(Context ct, AttributeSet set) {
		super(ct, set);
		init();
	}
	
	private void init() {
		mBrush  = new Paint();
		mGraph  = new Graph();
			
		mBrush.setARGB(255, 100, 100, 100);
		mBrush.setStrokeWidth(8.0f);
		mBrush.setStrokeCap(Cap.ROUND);
		mBrush.setAntiAlias(true);
		mBrush.setStyle(Style.STROKE);
	}
	
	public void setIsPreview(boolean flag) {
		mIsPreview = flag;
	}
	
	public boolean isPreview() {
		return mIsPreview;
	}
	
	public Paint getBrush() {
		return mBrush;
	}

	private void drawGrid(Canvas canvas, Paint brush) {
		if(!isInEditMode()) {
			mGraph.setSize(this.getWidth(), this.getHeight());
			mGraph.drawShape(canvas, brush);
		}
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		drawGrid(canvas, mBrush);
	}

	@Override	
    public boolean onTouchEvent(final MotionEvent event) {	
		if(!mIsPreview) {
			//BaseGraphData temp = getPointNearClick(event.getX(), event.getY());
			
			switch(event.getAction()) {
				
				case (MotionEvent.ACTION_DOWN):
				break;
				
				case (MotionEvent.ACTION_MOVE):
				break;
			
				case (MotionEvent.ACTION_UP):
				break;
				
				default:
					return super.onTouchEvent(event);
			}
		}

    
		return true;
    }
	
	public void queueReset() {
		mGraph.getPlotPoints().clear();
	}
	
	public void setGraph(Graph newGraph) {
		if(newGraph != null)
			mGraph = newGraph;
	}
	
	public Graph getGraph() {
		return mGraph;
	}
}
