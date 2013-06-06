package mcross.Graph;

import mcross.Graph.Widgets.Graph;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

public class SettingsActivity extends Activity {
	
	private final static int NUM_OF_CELLS = 4;
	
	private EditText[] mRangeValues = null;
	
	private CheckBox mCheckAxis = null;
	private CheckBox mCheckGridlines = null;
	
	public static volatile Graph graph = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.settings);
        
        mRangeValues = new EditText [NUM_OF_CELLS];
        
        mRangeValues[0] = (EditText)findViewById(R.id.editMaxValueX);
        mRangeValues[1] = (EditText)findViewById(R.id.editMaxValueY);
        mRangeValues[2] = (EditText)findViewById(R.id.editMinValueX);
        mRangeValues[3] = (EditText)findViewById(R.id.editMinValueY);
        
        mCheckAxis      = (CheckBox)findViewById(R.id.checkShowAxis);
        mCheckGridlines = (CheckBox)findViewById(R.id.checkShowGridlines);
    }
    
    @Override
    public void onResume() {
    	super.onResume();
   
    	if(graph != null) {
			mRangeValues[0].setText(Float.toString(graph.getMaxValueX()));
			mRangeValues[1].setText(Float.toString(graph.getMaxValueY()));
			mRangeValues[2].setText(Float.toString(graph.getMinValueX()));
			mRangeValues[3].setText(Float.toString(graph.getMinValueY()));
				
			mCheckAxis.setChecked(graph.showAxis());
			mCheckGridlines.setChecked(graph.showGridlines());
    	}
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	
    	if(graph != null) {
    		graph.setMaxValues(Float.parseFloat(mRangeValues[0].getText().toString()),
    						   Float.parseFloat(mRangeValues[1].getText().toString()));

    		graph.setMinValues(Float.parseFloat(mRangeValues[2].getText().toString()),
    						   Float.parseFloat(mRangeValues[3].getText().toString()));
    	
    		graph.showAxis(mCheckAxis.isChecked());
    		graph.showGridLines(mCheckGridlines.isChecked());
    	}
    }
}