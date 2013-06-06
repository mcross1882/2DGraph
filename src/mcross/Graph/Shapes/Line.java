package mcross.Graph.Shapes;

public class Line {
	private Point mStart;
	private Point mEnd;
	
	public Line() {
		mStart = new Point();
		mEnd   = new Point();
	}
	
	public Line(float originX, float originY,
				float destX, float destY) 
	{
		mStart = new Point(null, originX, originY);
		mEnd   = new Point(null, destX, destY);
	}
	
	public Line(Point start, Point end) {
		if(start == null)
			mStart = new Point();
		else
			mStart = start;
		
		if(end == null)
			mEnd = new Point();
		else
			mEnd = end;
	}
	
	public void setOrigin(float x, float y) {
		mStart.setOriginXY(x, y);
	}
	
	public Point getOrigin() {
		return mStart.copy();
	}
	
	public void setDest(float x, float y) {
		mEnd.setOriginXY(x, y);
	}
	
	public Point getDest() {
		return mEnd.copy();
	}
}
