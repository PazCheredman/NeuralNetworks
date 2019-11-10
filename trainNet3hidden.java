import java.lang.Math;
import java.util.*; 
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class trainNet3hidden {

	static String dir= "C:\\Users\\Paz Cheredman\\Desktop\\לימודים\\רשתות נוירונים\\project\\group";
	static network net3Hidden = new network();
	static int [] ansI= {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
	static int [] ansJ= {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
	static int [] ansK= {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};


	public static void main(String[] args) {

		System.out.println( "--------------------------------------------------" );	 
		System.out.println( "Neural network training (serial and non-serial)" );
		System.out.println( "--------------------------------------------------" );	
		System.out.println();
		System.out.println("Please select the learning rate (between 0 to 1)");
		Scanner sc = new Scanner(System.in);
		double learningRate =sc.nextDouble(); 


		Layers lay = new Layers(10000, 1000);
		net3Hidden.add(lay);
		lay = new Layers(lay, 250);
		net3Hidden.add(lay);
		lay= new Layers(lay, 65);
		net3Hidden.add(lay);
		lay= new Layers(lay, 20);
		net3Hidden.add(lay);
		lay= new Layers(lay, 3);
		net3Hidden.add(lay);

		System.out.println();
		System.out.println( "--------------------------------------------------" );	 
		System.out.println( "	Serial network training:");
		System.out.println( "--------------------------------------------------" );	 

		System.out.println("The training is on 5 learning groups");
		for (int i = 0; i < 5; i++) {
			System.out.println();
			System.out.println("This is iteration number: "+i);
			runSerial(5, net3Hidden);
			int[] ansSerial= checkError(6);
			calculationOfSuccess(ansSerial);
			System.out.println();
			System.out.println( "--------------------------------------------------" );	 
		}

		System.out.println();
		System.out.println( "--------------------------------------------------" );	 
		System.out.println( "	Non Serial network training:");
		System.out.println( "--------------------------------------------------" );	 

		System.out.println("The training is on 19 learning groups");
		for (int i = 0; i < 5; i++) {
			System.out.println();
			System.out.println("This is iteration number: "+i);
			runNonSerial(19,net3Hidden);
			int[] ansNonSerial= checkError(20);
			calculationOfSuccess(ansNonSerial);
			System.out.println();
			System.out.println( "--------------------------------------------------" );	 
		}
	}

	/**
	 * @param learningRate
	 * @return random learningRate
	 */
	public double ChangeRo (double learningRate1){
		learningRate1=Math.random();
		System.out.println("Learning Rate =" +learningRate1);	
		return	learningRate1;
	}

	/**
	 * serial:
	 * this function receives input in the form: 
	 * circle(i) && triangle(i) && rectangle(i)
	 */

	///
	public static void processFile(String dirK, int i, int j, int k){
		String fileName="";

		fileName="\\circle"+i+".txt";
		ArrayList<Double> input= readFile(dirK+ fileName);
		net3Hidden.forward(input/*, output*/);
		double expected[]= new double[3];
		expected[0]= 1;
		expected[1]= 0;
		expected[2]= 0;
		net3Hidden.back(expected);

		fileName="\\triangle"+j+".txt";
		input= readFile(dirK+ fileName);
		net3Hidden.forward(input);
		expected[0]= 0;
		expected[1]= 1;
		expected[2]= 0;
		net3Hidden.back(expected);

		fileName="\\rectrangle"+k+".txt";
		input= readFile(dirK+ fileName);
		net3Hidden.forward(input);
		expected[0]= 0;
		expected[1]= 0;
		expected[2]= 1;
		net3Hidden.back(expected);
	}

	public static ArrayList<Double> readFile(String fileName) {

		ArrayList<Double>input= new ArrayList<>();
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String str;			
			str = br.readLine();
			while(str != null){
				for (int i = 0; i <str.length(); i++) {
					if(str.charAt(i)=='-'){
						input.add(0.0);
					}
					else if(str.charAt(i)=='*'){
						input.add(1.0); 
					}
				}
				str = br.readLine();
			}

			br.close();
			fr.close();

		}catch (Exception e) {
			System.out.println("error in reading file");
			System.out.println("file= "+fileName );
		}
		return input;
	}

	public static void runSerial(int k, network net){
		for (int j = 1; j <= k; j++) {
			for (int i = 0; i < 20; i++) {
				processFile(dir+j, i, i, i);
			}
		}
	}

	public static void runNonSerial(int k,network net){
		int[] ans=new int[3];
		for (int j = 1; j <= k; j++) {
			for (int i = 0; i < 20; i++) {
				ans=randomIndex();
				processFile(dir+j,ans[0], ans[1], ans[2]);
			}
		}
	}


	public static int[] randomIndex(){
		int [] ans=new int[3];

		int i=(int) (Math.random()*20);
		while(ansI[i]==-1){
			i=(int) (Math.random()*20);
		}
		ans[0]=ansI[i];
		ansI[i]=-1;

		i=(int) (Math.random()*20);
		while(ansJ[i]==-1){
			i=(int) (Math.random()*20);
		}
		ans[1]=ansJ[i];
		ansJ[i]=-1;

		i=(int) (Math.random()*20);
		while(ansK[i]==-1){
			i=(int) (Math.random()*20);
		}
		ans[2]=ansK[i];
		ansK[i]=-1; 

		for (i=0;i<20; i++){
			ansI[i]=i;
			ansJ[i]=i;
			ansK[i]=i;
		}
		return ans;
	}

	/**
	 * this function receives input in random form: 
	 * circle(i) && triangle(j) && rectangle(k)
	 * the function randomIndex calculate i, j ,k randomly
	 */


	public static int[] checkError(int k){
		int[] count=new int[3];
		String fileName="";
		for (int i = 0; i < 20; i++) {
			fileName="\\circle"+i+".txt";
			ArrayList<Double> input= readFile(dir+k+ fileName);
			ArrayList<Double> output;
			net3Hidden.forward(input/*, output*/);
			output = net3Hidden.getLast().getOutputArray();

			if(output.get(0)>output.get(1) && output.get(0)>output.get(2)){
				count[0]++;
			}

			int counterr=0;
			fileName="\\triangle"+i+".txt";
			input= readFile(dir+k+ fileName);

			for (int j = 0; j < input.size(); j++) {
				if(input.get(j)!=0){
					counterr++;
				}
			}
			net3Hidden.forward(input);
			output=net3Hidden.getLast().getOutputArray();
			if(output.get(0)<output.get(1) && output.get(1)>output.get(2)){
				count[1]++;
			}

			fileName="\\rectrangle"+i+".txt";
			input= readFile(dir+k+ fileName);
			net3Hidden.forward(input);
			output=net3Hidden.getLast().getOutputArray();
			if(output.get(2)>output.get(1) && output.get(2)>output.get(0)){
				count[2]++;
			}
		}
		return count;
	}


	/**
	 * function that calculates the % of the network success
	 */

	public static void calculationOfSuccess (int [] count){
		int countCircle=count[0], countTriangle=count[1], countRectangle=count[2];
		double successCircle, successTriangle, successRectangle, successTotal ;
		successCircle = (countCircle/20.0)*100;
		successTriangle = (countTriangle/20.0)*100;
		successRectangle = (countRectangle/20.0)*100;
		successTotal=((countCircle+countTriangle+countRectangle)/60.0)*100;

		System.out.println("Total success of the network: " + successTotal + "%");
		System.out.println("The network identified "+countCircle+ "/20 circles successfully: "+successCircle+"%");
		System.out.println("The network identified "+countTriangle+ "/20 triangle successfully: "+successTriangle+"%");
		System.out.println("The network identified "+countRectangle+ "/20 rectangle successfully: "+successRectangle+"%");

	}
}
