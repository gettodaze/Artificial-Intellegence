import java.util.*;
public class Ice {
	static int[] yVals = new int[162];
	private static double yBar;
	private static double yDev;
	private static double xBar;
	private static double xDev;
	private static double betaHat1;
	private static double betaHat0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int flag = Integer.valueOf(args[0]);
		initArray();
		yBar();
		yDev();
		xBar = (2016+1855)/(double)2;
		xDev();
		betaHat();
		if(flag == 100) {
			for(int i = 0; i < 162; i++) {
				System.out.println(i+1855+" "+yVals[i]);
			}
		}
		if(flag == 200) {
			System.out.println(yVals.length);
			System.out.println(String.format("%.2f",yBar));
			System.out.println(String.format("%.2f",yDev));
			
		}
		if(flag == 300) {
			double beta1 = Double.valueOf(args[1]);
			double beta2 = Double.valueOf(args[2]);
			double MSE = MSE(beta1, beta2);
			System.out.println(String.format("%.2f", MSE));
		}
		if(flag == 400) {
			double beta0 = Double.valueOf(args[1]);
			double beta1 = Double.valueOf(args[2]);
			double gradientA = gradientA(beta0, beta1);
			double gradientB = gradientB(beta0,beta1);
			System.out.println(String.format("%.2f", gradientA));
			System.out.println(String.format("%.2f", gradientB));
		}
		if(flag == 500) {
			double mu = Double.valueOf(args[1]);
			double t = Double.valueOf(args[2]);
			gradientDescentShow(mu, t);
		}
		if(flag == 600) {
			System.out.println(String.format("%.2f", betaHat0)+" "+String.format("%.2f", betaHat1)+" "+String.format("%.2f", MSE(betaHat0, betaHat1)));
		}
		if(flag == 700) {
			int year = Integer.valueOf(args[1]);
			System.out.println(String.format("%.2f", linearPrediction(year)));
		}
		if(flag == 800) {
			double mu = Double.valueOf(args[1]);
			double t = Double.valueOf(args[2]);
			gradientDescentShowClosed(mu, t);
		}
		if(flag == 900) {
			double mu = Double.valueOf(args[1]);
			double t = Double.valueOf(args[2]);
			gradientDescentShowClosedSGD(mu, t);
		}


		

	}
	
	public static void initArray() {
		yVals[0] = 118;
		yVals[1] = 151;
		yVals[2] =121;
		yVals[3] =96;
		yVals[4] =110;
		yVals[5] =117;
		yVals[6] =132;
		yVals[7] =104;
		yVals[8] =125;
		yVals[9] =118;
		yVals[10] =125;
		yVals[11] =123;
		yVals[12] =110;
		yVals[13] =127;
		yVals[14] =131;
		yVals[15] =99;
		yVals[16] =126;
		yVals[17] =144;
		yVals[18] =136;
		yVals[19] =126;
		yVals[20] =91;
		yVals[21] =130;
		yVals[22] =62;
		yVals[23] =112;
		yVals[24] =99;
		yVals[25] =161;
		yVals[26] =78;
		yVals[27] =124;
		yVals[28] =119;
		yVals[29] =124;
		yVals[30] =128;
		yVals[31] =131;
		yVals[32] =113;
		yVals[33] =88;
		yVals[34] =75;
		yVals[35] =111;
		yVals[36] =97;
		yVals[37] =112;
		yVals[38] =101;
		yVals[39] =101;
		yVals[40] =91;
		yVals[41] =110;
		yVals[42] =100;
		yVals[43] =130;
		yVals[44] =111;
		yVals[45] =107;
		yVals[46] =105;
		yVals[47] =89;
		yVals[48] =126;
		yVals[49] =108;
		yVals[50] =97;
		yVals[51] =94;
		yVals[52] =83;
		yVals[53] =106;
		yVals[54] =98;
		yVals[55] =101;
		yVals[56] =108;
		yVals[57] =99;
		yVals[58] =88;
		yVals[59] =115;
		yVals[60] =102;
		yVals[61] =116;
		yVals[62] =115;
		yVals[63] =82;
		yVals[64] =110;
		yVals[65] =81;
		yVals[66] =96;
		yVals[67] =125;
		yVals[68] =104;
		yVals[69] =105;
		yVals[70] =124;
		yVals[71] =103;
		yVals[72] =106;
		yVals[73] =96;
		yVals[74] =107;
		yVals[75] =98;
		yVals[76] =65;
		yVals[77] =115;
		yVals[78] =91;
		yVals[79] =94;
		yVals[80] =101;
		yVals[81] =121;
		yVals[82] =105;
		yVals[83] =97;
		yVals[84] =105;
		yVals[85] =96;
		yVals[86] =82;
		yVals[87] =116;
		yVals[88] =114;
		yVals[89] =92;
		yVals[90] =98;
		yVals[91] =101;
		yVals[92] =104;
		yVals[93] =96;
		yVals[94] =109;
		yVals[95] =122;
		yVals[96] =114;
		yVals[97] =81;
		yVals[98] =85;
		yVals[99] =92;
		yVals[100] =114;
		yVals[101] =111;
		yVals[102] =95;
		yVals[103] =126;
		yVals[104] =105;
		yVals[105] =108;
		yVals[106] =117;
		yVals[107] =112;
		yVals[108] =113;
		yVals[109] =120;
		yVals[110] =65;
		yVals[111] =98;
		yVals[112] =91;
		yVals[113] =108;
		yVals[114] =113;
		yVals[115] =110;
		yVals[116] =105;
		yVals[117] =97;
		yVals[118] =105;
		yVals[119] =107;
		yVals[120] =88;
		yVals[121] =115;
		yVals[122] =123;
		yVals[123] =118;
		yVals[124] =99;
		yVals[125] =93;
		yVals[126] =96;
		yVals[127] =54;
		yVals[128] =111;
		yVals[129] =85;
		yVals[130] =107;
		yVals[131] =89;
		yVals[132] =87;
		yVals[133] =97;
		yVals[134] =93;
		yVals[135] =88;
		yVals[136] =99;
		yVals[137] =108;
		yVals[138] =94;
		yVals[139] =74;
		yVals[140] =119;
		yVals[141] =102;
		yVals[142] =47;
		yVals[143] =82;
		yVals[144] =53;
		yVals[145] =115;
		yVals[146] =21;
		yVals[147] =89;
		yVals[148] =80;
		yVals[149] =101;
		yVals[150] =95;
		yVals[151] =66;
		yVals[152] =106;
		yVals[153] =97;
		yVals[154] =87;
		yVals[155] =109;
		yVals[156] =57;
		yVals[157] =87;
		yVals[158] =117;
		yVals[159] =91;
		yVals[160] =62;
		yVals[161] =65;
	}
	public static void yBar() {
		double ySum = 0;
		for(int i = 0; i < yVals.length; i++) {
			ySum += yVals[i];
		}
		yBar = ySum/yVals.length;
	}
	public static void yDev() {
		double yDevSum = 0;
		for(int i = 0; i < yVals.length; i++) {
			yDevSum += Math.pow(yVals[i] - yBar, 2);
		}
		yDev = Math.sqrt(yDevSum/(double)(yVals.length-1));
	}
	public static void xDev() {
		double xDevSum = 0;
		for(int i = 0; i < yVals.length; i++) {
			xDevSum += Math.pow((i+1855) - xBar, 2);
		}
		xDev = Math.sqrt(xDevSum/(double)(yVals.length-1));
	}
	public static double MSE(double beta0, double beta1) {
		double MSESum = 0;
		for(int i = 0; i < yVals.length; i++) {
			MSESum += Math.pow((beta0 + beta1*(i+1855) - yVals[i]),2);
		}
		return MSESum / yVals.length;
	}
	public static double gradientA(double beta0, double beta1) {
		double gradientA = 0;
		for(int i = 0; i < yVals.length; i++) {
			gradientA += (beta0 + beta1*(i+1855) - yVals[i]);
		}
		gradientA = gradientA*2 / yVals.length;
		return gradientA;
		
	}
	public static double gradientB(double beta0, double beta1) {
		double gradientB = 0;
		for(int i = 0; i < yVals.length; i++) {
			gradientB += (beta0 + beta1*(i+1855) - yVals[i])*(i+1855);
		}
		gradientB = gradientB*2 / yVals.length;
		return gradientB;
	}
	public static void gradientDescentShow(double mu, double t) {
		double beta0 = 0;
		double beta1 = 0;
		double nbeta0 = 0;
		double nbeta1 = 0;
		for(int i = 1; i <= t; i++) {
			nbeta0 = beta0 - mu*gradientA(beta0, beta1);
			nbeta1 = beta1 - mu*gradientB(beta0, beta1);
			beta0 = nbeta0;
			beta1 = nbeta1;
			System.out.println(i+" "+String.format("%.2f", beta0)+" "+String.format("%.2f", beta1)+" "+String.format("%.2f", MSE(beta0, beta1)));
		}
	}
	public static void betaHat() {
		double numSum = 0;
		double demSum = 0;
		for(int i = 0; i < yVals.length; i++) {
			numSum += (1855+i-xBar)*(yVals[i]-yBar);
			demSum += Math.pow((1855+i-xBar), 2);
		}
		betaHat1 = numSum/demSum;
		betaHat0 = yBar - betaHat1*xBar;
	}
	public static double linearPrediction(int year) {
		return betaHat0 + betaHat1*year;
	}
	
	public static void gradientDescentShowClosed(double mu, double t) {
		double beta0 = 0;
		double beta1 = 0;
		double nbeta0 = 0;
		double nbeta1 = 0;
		for(int i = 1; i <= t; i++) {
			nbeta0 = beta0 - mu*gradientAClosed(beta0, beta1);
			nbeta1 = beta1 - mu*gradientBClosed(beta0, beta1);
			beta0 = nbeta0;
			beta1 = nbeta1;
			System.out.println(i+" "+String.format("%.2f", beta0)+" "+String.format("%.2f", beta1)+" "+String.format("%.2f", MSEClosed(beta0, beta1)));
		}
	}
	public static double gradientAClosed(double beta0, double beta1) {
		double gradientA = 0;
		for(int i = 0; i < yVals.length; i++) {
			gradientA += (beta0 + beta1*((i+1855-xBar)/xDev) - yVals[i]);
		}
		gradientA = gradientA*2 / yVals.length;
		return gradientA;
		
	}
	public static double gradientBClosed(double beta0, double beta1) {
		double gradientB = 0;
		for(int i = 0; i < yVals.length; i++) {
			gradientB += (beta0 + beta1*((i+1855-xBar)/xDev) - yVals[i])*((i+1855-xBar)/xDev);
		}
		gradientB = gradientB*2 / yVals.length;
		return gradientB;
	}
	public static double MSEClosed(double beta0, double beta1) {
		double MSESum = 0;
		for(int i = 0; i < yVals.length; i++) {
			MSESum += Math.pow((beta0 + beta1*((i+1855-xBar)/xDev) - yVals[i]),2);
		}
		return MSESum / yVals.length;
	}
	public static void gradientDescentShowClosedSGD(double mu, double t) {
		double beta0 = 0;
		double beta1 = 0;
		double nbeta0 = 0;
		double nbeta1 = 0;
		Random r = new Random();
		int i1 = r.nextInt(yVals.length);
		for(int i = 1; i <= t; i++) {
			nbeta0 = beta0 - mu*2*(beta0 + beta1*((i1+1855-xBar)/xDev) - yVals[i1]);
			nbeta1 = beta1 - mu*2*(beta0 + beta1*((i1+1855-xBar)/xDev) - yVals[i1])*((i1+1855-xBar)/xDev);
			beta0 = nbeta0;
			beta1 = nbeta1;
			System.out.println(i+" "+String.format("%.2f", beta0)+" "+String.format("%.2f", beta1)+" "+String.format("%.2f", MSEClosed(beta0, beta1)));
		}
	}
	
}
