import java.util.ArrayList;

public class Layers {
	public ArrayList<neuron> lay= new ArrayList<>();
	private Layers layerPrev;
	private Layers layerNext;
	private double a= 1.0;

	public double sigmoid(double x) {
		double s= 1/(1+Math.exp(-a*x));
		return s;
	}

	//*********first layer:*********//

	//constructor  
	public Layers(int neuronsAmountInput, int neuronsAmountOutput){
		layerPrev=null;
		layerNext = null;
		for (int i = 0; i < neuronsAmountOutput; i++) {
			lay.add(new neuron(neuronsAmountInput));
		}
	}

	//calcOutput
	//foward propagation
	public void calcOutput(ArrayList<Double> arr){ //layerPrev==null
		for (int i = 0; i < lay.size(); i++) {
			lay.get(i).calcOutput(arr);
		}
	}

	//adjustWeight
	public void adjustWeight(ArrayList<Double> arr,double ro) {// first layer
		for (int i = 0; i < lay.size(); i++) {
			neuron n= lay.get(i);
			n.adjustWeight(arr, ro);
		}
		//System.out.println(arr);
	}

	//*********hidden layer:*********//

	//constructor
	public Layers(Layers layPrev, int neuronsAmount){
		layerPrev= layPrev;
		layerNext = null;
		for (int i = 0; i < neuronsAmount; i++) {
			lay.add(new neuron(layPrev.lay.size()));
		}

	}

	//calcOutput
	//foward propagation
	public void calcOutput(){
		ArrayList<Double> arr= new ArrayList<>();
		for (int i = 0; i < layerPrev.lay.size(); i++) {
			arr.add(layerPrev.lay.get(i).getOutput());
		}
		for (int i = 0; i < lay.size(); i++) {
			lay.get(i).calcOutput(arr);
		}
				
	}

	//adjustWeight
	public void adjustWeight(double ro) {  //!=First Layer
		ArrayList<Double> arr= new ArrayList<>();
		for (int i = 0; i < layerPrev.lay.size(); i++) {
			arr.add(layerPrev.lay.get(i).getOutput());
		}
		for (int i = 0; i < lay.size(); i++) {
			neuron n= lay.get(i);
			n.adjustWeight(arr, ro);
		}

	}
	
	//back propagation
	public void calcGradient(){ //!=LayerLast
		for (int j=0; j<lay.size(); j++){
			neuron n= lay.get(j);
			double out= n.getOutput();
			//double gradient=;
			double gradient=this.a*out*(1-out)*layerNext.calcSumGradient(j);
		//	System.out.println("calcGradient= "+ gradient);
			n.setGradient(gradient);
		}
	}
	
	//back propagation
	public double calcSumGradient(int index){
		double sum=0;
		for (int j=0; j<lay.size(); j++){
			neuron n= lay.get(j);
			sum+=n.getWeight(index)*n.getGradient();
		}
		return sum;
	}
	
	
	//*********last layer:*********//
	
	/*//foward propagation --- same to hidden! 
		public void calcOutput(){
			ArrayList<Double> arr= new ArrayList<>();
			for (int i = 0; i < layerPrev.lay.size(); i++) {
				arr.add(layerPrev.lay.get(i).getOutput());
			}
			for (int i = 0; i < lay.size(); i++) {
				lay.get(i).calcOutput(arr);
			}
		}*/
	
	//back propagation function 
	public void calcGradient(double expected[]){ 
		for (int i = 0; i < lay.size(); i++) {
			neuron n= lay.get(i);
			double out= n.getOutput();
			double gradient=this.a*out*(1-out)*(expected[i]-out);
			n.setGradient(gradient);
			double error=expected[i]-out;
		}
	}
	
	
	public ArrayList<Double> getOutputArray(){
		ArrayList<Double> ans= new ArrayList<>();
		for (int i = 0; i < lay.size(); i++) {
			ans.add(lay.get(i).getOutput());
		}
		return ans;
				
				
	}
	
	//*********gets & setters functions:*********//


	public int getSize(){
		return lay.size();
	}

	public neuron getNeuron(int index) {
		return lay.get(index);
	}
	public Layers getLayerNext() {
		return layerNext;
	}
	public void setLayerNext(Layers layerNext) {
		this.layerNext = layerNext;
	}
	public void setNext(Layers next){
		layerNext=next;
	}

	public Layers getLayerPrev() {
		return layerPrev;
	}
	public void setLayerPrev(Layers layerPrev) {
		this.layerPrev = layerPrev;
	}


}





