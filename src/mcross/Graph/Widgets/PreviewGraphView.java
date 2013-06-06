package mcross.Graph.Widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PreviewGraphView extends RelativeLayout {
	private GraphView mGraphView;
	
	private TextView mExpandLabel;
	
	public PreviewGraphView(Context ct) {
		super(ct);
		
		initialize(ct);
	}
	
	public PreviewGraphView(Context ct, AttributeSet set) {
		super(ct, set);
		
		initialize(ct);
	}
	
	private void initialize(Context ct) {
		if(!isInEditMode()) 
		{
			mGraphView = new GraphView(ct);
			mGraphView.setIsPreview(true);
			
			mExpandLabel = new TextView(ct);
			mExpandLabel.setText("Click Graph to Expand");
			mExpandLabel.setBackgroundColor(0x99333333);
			mExpandLabel.setTextColor(0xffffffff);
			mExpandLabel.setGravity(Gravity.LEFT);
			
			this.addView(mGraphView);
			this.addView(mExpandLabel);
			
			RelativeLayout.LayoutParams params = 
					new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,
											     	RelativeLayout.LayoutParams.WRAP_CONTENT);
			
			mExpandLabel.setLayoutParams(params);
			mExpandLabel.setPadding(5, 5, 5, 5);
		}
	}
	
	public GraphView getGraphView() {
		return mGraphView;
	}
}
