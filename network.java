import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class network {

	private Layers first, last;
	ArrayList<Double> in,out;
	double ro;

		//foward need to array!!! 
	public void forward(ArrayList<Double> input){ 
		double [] ans= new double [input.size()];
		Layers lay = first;
		in=input;
		lay.calcOutput(input);
		lay= lay.getLayerNext();
		while(lay!=null){
			lay.calcOutput();
			if(lay.getLayerNext()==null){
				last=lay;
			}
			lay=lay.getLayerNext();
		}
		
	}

	public void back(double expected[]){
		Layers lay = last;
		lay.calcGradient(expected);
		lay.adjustWeight(ro);
		lay=lay.getLayerPrev();
		while(lay!=null){
			lay.calcGradient();
			if(lay.getLayerPrev()!=null){
				lay.adjustWeight(ro);
			}
			else{
				lay.adjustWeight(in, ro);
			}
			lay=lay.getLayerPrev();
		}
	}

	public void add(Layers lay){
		if (first==null){
			first= lay;
			last=lay;
		}
		else{
			last.setLayerNext(lay);
			last=lay;
		}
	}

	public Layers getLast() {
		return last;
	}

	public double getRo() {
		return ro;
	}

	public void setRo(double ro) {
		this.ro = ro;
	}

}
