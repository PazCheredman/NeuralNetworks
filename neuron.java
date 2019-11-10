import java.util.ArrayList;
/**
 * this class represents single neuron
 *
 */
public class neuron {

	/**
	 * parameters:
	 * double output; //output y
	 * double weight[];
	 * double bias;
	 * double a=1.0;
	 * double gradient;
	 */
	
	double output; //output y
	double weight[];
	double bias;
	double a=1.0;
	double gradient;

	/**
	 * 
	 * @param x
	 * @return sigmoid
	 */
	private double sigmoid(double x) {
		double s= 1/(1+Math.exp(-a*x));
		return s;
	}

	/**
	 * constructor
	 * @param amount
	 */
	public neuron(int amount){
		bias=0;
		weight=new double[amount];
		for (int i = 0; i < amount; i++) {
			weight[i]= Math.random()*2 -1;
		}
	}
	/**
	 * foward propagation function
	 * @param in
	 */

	public void calcOutput(ArrayList<Double> in){ 
		double sum=0;
		for (int i = 0; i < in.size(); i++) {
			sum += weight[i] *in.get(i);
		}
		output= sigmoid(sum+bias);
	} 

	
	
	/**
	 * fixing the weights according
	 * @param in
	 * @param ro
	 */

	public void adjustWeight(ArrayList<Double> in, double ro){
		for (int i = 0; i < in.size(); i++) {
			weight[i]+= ro*gradient*in.get(i); //adjustWeight
			

		}
	} 

	/**
	 * 	*********gets & setters functions:*********
	 */

	public double getWeight(int index) {
		return weight[index];
	}

	public void setWeight(double we, int index) {
		this.weight[index] = we;
	}

	public double getBias() {
		return bias;
	}

	public void setBias(double bias) {
		this.bias = bias;
	}

	public double getOutput() {
		return output;
	}

	public void setOutput(double output) {
		this.output = output;
	}

	public double getGradient() {
		return gradient;
	}

	public void setGradient(double gradient) {
		this.gradient = gradient;
	}

	
	
}
