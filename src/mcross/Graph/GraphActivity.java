package mcross.Graph;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import mcross.Graph.Shapes.Point;
import mcross.Graph.Widgets.Graph;
import mcross.Graph.Widgets.GraphView;
import mcross.algorithm.infix.Expression;
import mcross.algorithm.infix.InfixEvaluator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class GraphActivity extends Activity {
	
	public final static String TEMP_FILE = "temp.graph";
	
	private Graph mGraph;
	
	private GraphView mGraphView;
	
	private InfixEvaluator mEvaluator;
	
	private String mExpression;
	
	private EditText mEditExpression;
	
	private LinearLayout mInputLayout;
	
	private boolean mInputShowing = false;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mEvaluator = new InfixEvaluator();
        
        mGraphView = (GraphView)findViewById(R.id.GraphView);
        
        mEditExpression = (EditText)findViewById(R.id.ExpressionEdit);
        
        mEditExpression.setInputType(EditorInfo.TYPE_NULL);
        
        mInputLayout = (LinearLayout)findViewById(R.id.input_board);
        
        ((Button)findViewById(R.id.buttonSquared)).setText(Html.fromHtml("x<sup>2</sup>"));
        ((Button)findViewById(R.id.buttonExponent)).setText(Html.fromHtml("x<sup>n</sup>"));

        ((Button)findViewById(R.id.buttonSin)).setOnLongClickListener(mLongClickListener);
        ((Button)findViewById(R.id.buttonDelete)).setOnLongClickListener(mLongClickListener);
        
        mInputLayout.setVisibility(View.INVISIBLE);
        
        mGraph = mGraphView.getGraph();
        
        mGraph.setMaxValues(15.f, 15.f);
        mGraph.setMinValues(-15.f, -15.f);
        
        SettingsActivity.graph = mGraph;
    }
    
    public void runIterations() {
    	try {
    		mGraph.clearShapes();
    		
	    	float y = 0;
	    	
	    	Expression expr = new Expression();
	    	
	        for(float x=-10.f; x <= 10.f; x += 1.f) {
	        	expr.variableList.put("x", new Double(x));
	        	
	        	expr = mEvaluator.evaluate(mExpression, expr);
	        	
	        	y = (float) mEvaluator.run(expr);
	        	
	        	mGraph.addPoint(new Point(String.format("(%.1f, %.1f)",x,y),x,y));
	        }
	        
	        mGraphView.invalidate();
    	}
    	catch(Exception e) {
    		Toast.makeText(this, "Invalid expression", Toast.LENGTH_LONG).show();
    		e.printStackTrace();
    	}
    }
    
    public void onGraphButtonClicked(View v) {
    	switch(v.getId()) {
	    	case R.id.ButtonParse:
	    	{
		       	mExpression = mEditExpression.getText().toString();
		       	runIterations();	
	    	}
	    	break;
    	}
    }
    
    public void onGraphLabelClicked(View v) {
    	if(mInputShowing)  {
    		mInputLayout.setVisibility(View.INVISIBLE);
    		mInputShowing = false;
    	}
    	else {
    		mInputLayout.setVisibility(View.VISIBLE);
    		mInputShowing = true;
    	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.settings:
                Intent newIntent = new Intent(this, SettingsActivity.class);
                startActivity(newIntent);
            break;
            
            case R.id.quit:
                this.finish();
            break;
            
            default:
                return super.onOptionsItemSelected(item);
        }
       
        return true;
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    }
    
    public void saveTempFile() {
        try {
	        FileOutputStream stream = openFileOutput(TEMP_FILE, MODE_PRIVATE);
	        DataOutputStream dStream = new DataOutputStream(stream);
	        
	        mGraph.writeToStream(dStream);
	        
	        dStream.flush();
	        dStream.close();
	        
	        stream.close();
        }
        catch(FileNotFoundException e) {
        	e.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    public void openTempFile() {
        try {
	        FileInputStream stream = openFileInput(TEMP_FILE);
	        DataInputStream dStream = new DataInputStream(stream);
	        
	        mGraph.readFromStream(dStream);
	        
	        dStream.close();
	        
	        stream.close();
        }
        catch(FileNotFoundException e) {
        	e.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void onInputButtonClicked(View v) {
    	String insertText = "";
    	
    	switch(v.getId()) {
	    	case R.id.button0:
	    		insertText = "0";
	    	break;
	    	
	    	case R.id.button1:
	    		insertText = "1";
	    	break;
	    	
	    	case R.id.button2:
	    		insertText = "2";
	    	break;
	    	
	    	case R.id.button3:
	    		insertText = "3";
	    	break;

	    	case R.id.button4:
	    		insertText = "4";
	    	break;

	    	case R.id.button5:
	    		insertText = "5";
	    	break;

	    	case R.id.button6:
	    		insertText = "6";
	    	break;

	    	case R.id.button7:
	    		insertText = "7";
	    	break;

	    	case R.id.button8:
	    		insertText = "8";
	    	break;

	    	case R.id.button9:
	    		insertText = "9";
	    	break;
	    	
	    	case R.id.buttonX:
	    		insertText = "x";
	    	break;
	    	
	    	case R.id.buttonY:
	    		insertText = "y";
	    	break;

	    	case R.id.buttonAdd:
	    		insertText = "+";
	    	break;
	    	
	    	case R.id.buttonSubtract:
	    		insertText = "-";
	    	break;
	    	
	    	case R.id.buttonMultiply:
	    		insertText = "*";
	    	break;
	    	
	    	case R.id.buttonDivide:
	    		insertText = "/";
	    	break;
	    	
	    	case R.id.buttonSin:
	    		insertText = "sin(";
	    	break;
	    	
	    	case R.id.buttonCos:
	    		insertText = "cos(";
	    	break;
	    	
	    	case R.id.buttonTan:
	    		insertText = "tan(";
	    	break;
	    	
	    	case R.id.buttonLog:
	    		insertText = "log(";
	    	break;
	    	
	    	case R.id.buttonPeriod:
	    		insertText = ".";
	    	break;
	    	
	    	case R.id.buttonFactorial:
	    		insertText = "!";
	    	break;
	    	
	    	case R.id.buttonSquareRoot:
	    		insertText = "sqrt(";
	    	break;
	    	
	    	case R.id.buttonSquared:
	    		insertText = "^2";
	    	break;
	    	
	    	case R.id.buttonExponent:
	    		insertText = "^";
	    	break;
	    	
	    	case R.id.buttonLParen:
	    		insertText = "(";
	    	break;
	    	
	    	case R.id.buttonRParen:
	    		insertText = ")";
	    	break;
	    	
	    	case R.id.buttonDelete:
	    	{
	    		CharSequence seq = mEditExpression.getText();
	    		
	    		if(seq.length() != 0) 
	    			mEditExpression.setText(seq.subSequence(0, seq.length()-1));
	    		
	    		return;
	    	}
    	}
    
    	CharSequence newText = Html.fromHtml(mEditExpression.getText() + insertText);
    	
    	mEditExpression.setText(newText);
    }
    
    private OnLongClickListener mLongClickListener =
    	new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				CharSequence insertText = "";
				
				switch(v.getId()) {
					case R.id.buttonSin:
						insertText = "asin(";
					break;
					
					case R.id.buttonCos:
						insertText = "acos(";
					break;
					
					case R.id.buttonTan:
						insertText = "atan(";
					break;
					
					case R.id.buttonDelete:
						mEditExpression.setText("");
						return true;
				}

		    	CharSequence newText = Html.fromHtml(mEditExpression.getText().toString() + insertText);
		    	
		    	mEditExpression.setText(newText);
		    	
				return true;
			}
    	
    };
}