import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Neural {
	static ArrayList<Double> weights = new ArrayList<Double>();
	static double x1;
	static double x2;
	static List<double[]> trainingItems;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int flag = Integer.valueOf(args[0]);
		if(flag == 100) {
			init11Arg(args);
			System.out.println(String.format("%.5f",nodeInU('A'))+" "+
					String.format("%.5f",nodeOutV('A'))+" "+
					String.format("%.5f",nodeInU('B'))+" "+
					String.format("%.5f",nodeOutV('B'))+" "+
					String.format("%.5f",nodeInU('C'))+" "+
					String.format("%.5f",nodeOutV('C')));
		}
		if(flag == 200) {
			init11Arg(args);
			double y = Double.valueOf(args[12]);
			System.out.println(String.format("%.5f",error(y))+" "+
					String.format("%.5f",partDerErrorVC(y))+" "+
					String.format("%.5f",partDerErrorUC(y)));
			
		}
		if (flag == 300) {
			init11Arg(args);
			double y = Double.valueOf(args[12]);
			System.out.println(String.format("%.5f",dEdVj(y, 'A'))+" "+
					String.format("%.5f",dEdUj(y, 'A'))+" "+
					String.format("%.5f",dEdVj(y, 'B'))+" "+
					String.format("%.5f",dEdUj(y, 'B')));
			
		}
		if (flag == 400) {
			init11Arg(args);
			double y = Double.valueOf(args[12]);
			for(int i = 1; i <= 9; i++) {				
				System.out.print(String.format("%.5f",edgeWeightError(y, i))+" ");			
			}
		}
		if (flag == 500) {
			init11Arg(args);
			double y = Double.valueOf(args[12]);
			double stepSize = Double.valueOf(args[13]);
			for(int i = 1; i <= 9; i++) {				
				System.out.print(String.format("%.5f",weights.get(i-1))+" ");			
			}
			System.out.println();
			System.out.println(String.format("%.5f",error(y)));
			wGradDescent(stepSize, y);
			for(int i = 1; i <= 9; i++) {				
				System.out.print(String.format("%.5f",weights.get(i-1))+" ");			
			}
			System.out.println();
			System.out.println(String.format("%.5f",error(y)));
		}
		if (flag == 601) {
			readTrainingItems("hw2_midterm_A_train.txt");
			testtItems();
//			flag = 10;
		}
		if(flag == 600) {
			initWeights(args);
			double stepSize = Double.valueOf(args[10]);
			readTrainingItems("hw2_midterm_A_train.txt");
			epochWithPrint(trainingItems, stepSize);
		}
		if(flag == 700) {
			initWeights(args);
			double stepSize = Double.valueOf(args[10]);
			int t = Integer.valueOf(args[11]);
			readTrainingItems("hw2_midterm_A_train.txt");
			for(int i = 0; i < t; i++) {
				epoch(trainingItems, stepSize);	
				for(int k = 1; k <= 9; k++) {				
					System.out.print(String.format("%.5f",weights.get(k-1))+" ");
				}
				System.out.println();
				System.out.println(String.format("%.5f",evaluationSetError()));
			}
			
		}
		if(flag == 800) {
			initWeights(args);
			double stepSize = Double.valueOf(args[10]);
			int t = Integer.valueOf(args[11]);
			readTrainingItems("hw2_midterm_A_train.txt");
			int epochs = 0;
			double err1 = Double.MAX_VALUE;
			double err2 = evaluationSetError();
			while(err2 < err1) {
				epoch(trainingItems, stepSize);	
				err1 = err2;
				err2 = evaluationSetError();
				epochs += 1;
			} 
			System.out.println(epochs);
			for(int k = 1; k <= 9; k++) {				
				System.out.print(String.format("%.5f",weights.get(k-1))+" ");
			}
			System.out.println();
			System.out.println(String.format("%.5f",err2));
			int tries = 0;
			int success = 0;
			for(double[] d : trainingItems) {
				x1 = d[0];
				x2 = d[1];
				tries++;
				if(Double.compare(Math.round(nodeOutV('C')),d[2])==0){
					success++;
				}
//				System.out.println(tries+" V is "+String.format("%.2f",nodeOutV('C'))+", rounded to "+Math.round(nodeOutV('C'))+", "+d[2]);
			}
			System.out.println(String.format("%.5f",success/(double)tries));
			
			
			
		}
		
}
	public static void init11Arg(String[] args) {
		weights.add(Double.valueOf(args[1]));
		weights.add(Double.valueOf(args[2]));
		weights.add(Double.valueOf(args[3]));
		weights.add(Double.valueOf(args[4]));
		weights.add(Double.valueOf(args[5]));
		weights.add(Double.valueOf(args[6]));
		weights.add(Double.valueOf(args[7]));
		weights.add(Double.valueOf(args[8]));
		weights.add(Double.valueOf(args[9]));
		x1 = Double.valueOf(args[10]);
		x2 = Double.valueOf(args[11]);
	}
	public static void initWeights(String[] args) {
		weights.add(Double.valueOf(args[1]));
		weights.add(Double.valueOf(args[2]));
		weights.add(Double.valueOf(args[3]));
		weights.add(Double.valueOf(args[4]));
		weights.add(Double.valueOf(args[5]));
		weights.add(Double.valueOf(args[6]));
		weights.add(Double.valueOf(args[7]));
		weights.add(Double.valueOf(args[8]));
		weights.add(Double.valueOf(args[9]));
	}
	public static double ReLU(double z) {
		return Math.max(0, z);
	}
	public static double sigmoid(double z) {
		return 1/(1+Math.exp(-z));
	}
	//U is a numeric amalgamation of all inputs and weights
	public static double nodeInU(char node) {
		double weight1;
		double weight2;
		double weight3;
		double input1;
		double input2;
		double input3;
		double u;
		switch (node) {
			case 'A':
				weight1 = weights.get(0);
				weight2 = weights.get(1);
				weight3 = weights.get(2);
				input1 = 1;
				input2 = x1;
				input3 = x2;
				break;				
			case 'B': 	
				weight1 = weights.get(3);
				weight2 = weights.get(4);
				weight3 = weights.get(5);
				input1 = 1;
				input2 = x1;
				input3 = x2;
				break;
			case 'C':
				weight1 = weights.get(6);
				weight2 = weights.get(7);
				weight3 = weights.get(8);
				input1 = 1;
				input2 = nodeOutV('A');
				input3 = nodeOutV('B');
				break;
			default:
				throw new RuntimeException("The input node '"+node+"' for u calc was neither A B nor C");
		}
		
		u = weight1*input1 + weight2*input2 + weight3*input3;
		return u;			
		}
	//V is the output of given node node
	public static double nodeOutV(char node) {
		double u = nodeInU(node);
		double v;
		if(node == 'A' || node == 'B') {
			return ReLU(u);
		}
		if(node == 'C') {
			return sigmoid(u);
		}
		else {
			throw new RuntimeException("The input node '"+node+"' for v calc was neither A B nor C");
		}	
	}
	public static double error(double y) {
		return .5*Math.pow((nodeOutV('C') - y), 2);
	}
	public static double partDerErrorVC(double y) {
		return nodeOutV('C') - y;
	}
	public static double partDerErrorUC(double y) {
		double partDEU;
		double v = nodeOutV('C');
		partDEU = partDerErrorVC(y)*v*(1-v);
		return partDEU;
	}
	public static double dEdVj(double y, char node) {
		assert(node == 'A' || node == 'B');
		if(node == 'A') {
			return weights.get(7)*partDerErrorUC(y);
		} else {
			return weights.get(8)*partDerErrorUC(y);
		}
	}
	public static double dVjdUj(double y, char node) {
		assert(node == 'A' || node == 'B');
		if(nodeInU(node) < 0) return 0;
		else return 1;
	}
	public static double dEdUj(double y, char node) {
		return dEdVj(y, node)*dVjdUj(y, node);
	}
	public static double edgeWeightError(double y, int w) {
		double v;
		char toNode;
		switch (w) {
			case 1: v = 1;
					toNode = 'A';
					break;
			case 2: v = x1;
					toNode = 'A';
					break;
			case 3: v = x2;
					toNode = 'A';
					break;	
			case 4: v = 1;
					toNode = 'B';
					break;
			case 5: v = x1;
					toNode = 'B';
					break;
			case 6: v = x2;
					toNode = 'B';
					break;	
			case 7: v = 1;
					toNode = 'C';
					break;
			case 8: v = nodeOutV('A');
					toNode = 'C';
					break;
			case 9: v = nodeOutV('B');
					toNode = 'C';
					break;
			default: throw new RuntimeException("w must be between 1 and 9");
		}
		if(toNode == 'A' || toNode == 'B') {
			return v*dEdUj(y, toNode);
		} else {
			return v*partDerErrorUC(y);
		}
		
	}
	public static void wGradDescent(double stepSize, double y) {
		ArrayList<Double> nweights = new ArrayList<Double>();
		for(int i = 0; i <= 8; i++) {
			double newWeight = weights.get(i) - stepSize*edgeWeightError(y, i+1);
			nweights.add(newWeight);
		}
		weights = nweights;
	}
	public static void readTrainingItems(String filepath) {
		trainingItems = new ArrayList<double[]>();
		Scanner scan;
	    File file = new File(filepath);
	    try {
	        scan = new Scanner(file);

	        while(scan.hasNextDouble())
	        {
	        	int k = 0;
	        	double[] temp = new double[3];
	        	for(int i = 0; i< 3; i++) {
	        		if(scan.hasNextDouble()) temp[i] = scan.nextDouble();
	        	}
	        	trainingItems.add(temp);
	        	temp = new double[3];
	        }

	    } catch (FileNotFoundException e1) {
	            e1.printStackTrace();
	    }
	}
	public static void testtItems() {
		for(double[] d : trainingItems) {
			System.out.println(d[0]+" "+d[1]+" "+d[2]);
		}
	}
	public static void epochWithPrint(List<double[]> items, double stepSize) throws IOException {
		for(double[] d : items) {
			x1 = d[0];
			x2 = d[1];
			double y = d[2];
			wGradDescent(stepSize, y);
			System.out.println(String.format("%.5f",x1)+" "+
					String.format("%.5f",x2)+" "+
					String.format("%.5f",y));
			for(int i = 1; i <= 9; i++) {				
				System.out.print(String.format("%.5f",weights.get(i-1))+" ");			
			}
			System.out.println();
			System.out.println(String.format("%.5f",evaluationSetError()));
		}
		
		
	}
	public static double evaluationSetError() throws IOException {
		String filename = "hw2_midterm_A_eval.txt";
		File file = new File(filename);
		FileInputStream fileIn = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fileIn));
		double eSError = 0;
		String curr = null;
		for(int i = 0; i < 25; i++) {
			curr = br.readLine();
			String[] arr2 = curr.split(" ");
			x1 = Double.parseDouble(arr2[0]);
			x2 = Double.parseDouble(arr2[1]);
			double y = Double.parseDouble(arr2[2]);
			eSError += error(y);
		}
		return eSError;
//		double eSError = 0;
//		for(int i = 1; i <= 25; i++) {
//			double y = trainingItems.get(i-1)[2];
//			x1 = trainingItems.get(i-1)[0];
//			x2 = trainingItems.get(i-1)[1];
//			eSError += error(y);
//		}
//		return eSError;
	}
	public static void epoch(List<double[]> items, double stepSize) {
		for(double[] d : items) {
			x1 = d[0];
			x2 = d[1];
			double y = d[2];
			wGradDescent(stepSize, y);
		}
	}
}


