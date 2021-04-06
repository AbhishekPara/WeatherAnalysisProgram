/*
 Climate data analysis

 This program calculates the average of all seasons in London, Ontario from years 1941-2018 and saves it to four separate arrays,
 one for each season. The user is then prompted to enter which season they would like to view statistics for. Once the user chooses
 they will be prompted to enter what type of data they would like to see (e.g. mode, maximum, average etc.). The user can choose
 to view information for a new season by entering the number "9". This program also calculates the correlation coefficient
 for each season, giving the user an analysis of the temperature change trend over the years.
 
 
 by: Abhishek Parapuram
 
 2019/10/24
 
 Imports java.io, java.text.DecimalFormat, java.text.NumberFormat, java.util.ArrayList, java.util.Arrays, java.util.Scanner;
 Reads file "climateDataFull"
 
 Enter numbers corresponding to what option you would like to choose
 */


package culminatingClimateData;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ClimateData {

    // declares inputs
    static double year,month,day,temp;
    static int numYear=78;
    // stores all average temperatures for each season per year
    static double avgSpring[]=new double[numYear];
    static double avgSummer[]=new double[numYear];
    static double avgWinter[]=new double[numYear];
    static double avgFall[]=new double[numYear];
    static int indexPop=0;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner (new FileReader("climateDataFull1.txt"));
        Scanner scanner = new Scanner(System.in);
        NumberFormat d = new DecimalFormat("0.00");

        // declares array lists to temporarily store temperatures for each season per year
        ArrayList<Double> winter = new ArrayList<>();
        ArrayList<Double> summer = new ArrayList<>();
        ArrayList<Double> spring = new ArrayList<>();
        ArrayList<Double> fall = new ArrayList<>();        
        
        // used later for standard deviation and slope calculations
        double arrayX[]=new double[numYear];
        for (int x=1; x<=numYear;x++) {
            arrayX[x-1]=x;
        }
        
        // reads first line
        year=sc.nextDouble();
        month=sc.nextDouble();
        day=sc.nextDouble();
        temp=sc.nextDouble();

        // populates each season per year
        while (true) {
            populate(sc,winter,summer,spring,fall);
            if (year==9999 && day==99 && month==99) {
                break;
            }
        }

        System.out.println("Type number corresponding to option you would like to choose.");
        System.out.println();

        // for user inputs
        while (true) {
            double coR=0.0;
            int mode=0;
            double[] data=new double[numYear];
            double medMinMax[]=new double[3];
            double standardDeviation=0.0;
            double standardDeviationX=standardDeviation(arrayX);
            double average=0.0;
            String summarizeCoR="";
            String seasonNames[]= {"spring","fall","summer","winter"};
            // select season you want information for
            System.out.print("Enter a season (1:spring, 2:fall, 3:summer, 4:winter) or type 5 to exit: ");
            int seasonInput=scanner.nextInt();
            System.out.println();
            // ends program
            if (seasonInput>0 && seasonInput <=5) {


                if (seasonInput==5) {
                    break;
                }
                // collects data for selected season
                else if (seasonInput==2) {
                    mode= mode(avgFall);
                    data=avgFall;
                    coR=coefficientR(avgFall);
                    medMinMax=medMinMax(avgFall);
                    standardDeviation=standardDeviation(avgFall);
                    summarizeCoR=summarizeCoR(coR);
                    average=average(avgFall);
                }
                else if(seasonInput==4){
                    mode= mode(avgWinter);
                    data=avgWinter;
                    coR=coefficientR(avgWinter);
                    medMinMax=medMinMax(avgWinter);
                    standardDeviation=standardDeviation(avgWinter);
                    summarizeCoR=summarizeCoR(coR);
                    average=average(avgWinter);
                }
                else if(seasonInput==3){
                    mode= mode(avgSummer);
                    data=avgSummer;
                    coR=coefficientR(avgSummer);
                    medMinMax=medMinMax(avgSummer);
                    standardDeviation=standardDeviation(avgSummer);
                    summarizeCoR=summarizeCoR(coR);
                    average=average(avgSummer);
                }
                else if(seasonInput==1){
                    mode= mode(avgSpring);
                    data=avgSpring;
                    coR=coefficientR(avgSpring);
                    medMinMax=medMinMax(avgSpring);
                    standardDeviation=standardDeviation(avgSpring);
                    summarizeCoR=summarizeCoR(coR);
                    average=average(avgSpring);
                }
            }
            else {
                System.out.println("Not a valid input. Must enter number from 1-5.");
                System.out.println();
            }
            while (true) {
                // user can select specific information they want about that season
                System.out.print("Enter (1:mode, 2:table, 3:average, 4:median, 5:min, 6:max, 7:standard deviation, 8:conclusion, 9:new season/exit): ");
                int infoInput=scanner.nextInt();
                System.out.println();
                if (infoInput>0 &&infoInput<=9) {


                    if (infoInput==9) {
                        break;
                    }
                    else if(infoInput==2) {
                        System.out.println("Year\tAverage "+seasonNames[seasonInput-1]+" temperature (°C)");
                        for (int i =0; i<numYear;i++) {
                            System.out.println((1941+i)+"\t"+d.format(data[i]));
                        }
                        System.out.println();
                    }
                    else if(infoInput==1) {
                        System.out.println("The mode of "+seasonNames[seasonInput-1]+" is approximately "+mode+" °C");
                        System.out.println();
                    }
                    else if(infoInput==8) {
                        System.out.println("Correlation coefficient is "+d.format(coR)+" meaning there is a "+summarizeCoR);
                        // line of best fit calculation (correlation coefficient * (standardDeviationY/standardDeviationX)
                        System.out.println("The temperature increases approximately "+d.format(coR*(standardDeviation/standardDeviationX))+" °C every year in the season of "+seasonNames[seasonInput-1]);
                        System.out.println();
                    }
                    else if(infoInput==4) {
                        System.out.println("The median temperature for "+seasonNames[seasonInput-1]+" is "+d.format(medMinMax[0])+" °C");
                        System.out.println();
                    }
                    else if(infoInput==6) {
                        System.out.println("The maximum average temperature for "+seasonNames[seasonInput-1]+" is "+ d.format(medMinMax[2])+" °C");
                        System.out.println();
                    }
                    else if (infoInput==5) {
                        System.out.println("The minimum average temperature for "+seasonNames[seasonInput-1]+" is "+ d.format(medMinMax[1])+" °C");
                        System.out.println();
                    }
                    else if(infoInput==7) {
                        System.out.println("The standard deviation of the data is "+ d.format(standardDeviation));
                        System.out.println();
                    }
                    else if(infoInput==3) {
                        System.out.println("The average temperature in the season "+seasonNames[seasonInput-1]+" over the years 1941-2001 is "+d.format(average)+" °C");
                        System.out.println();
                    }
                }
                else {
                    System.out.println("Not a valid input. Must enter number from 1-9");
                    System.out.println();
                }
            }
        }
        scanner.close();
        sc.close();

    } // main method


    public static double average(double[] avgSeason) {
        double totalTemp=0.0;
        double average;
        for (int i=0; i<numYear; i++) {
            totalTemp+=avgSeason[i];
        }
        average=totalTemp/numYear;
        return average;
    }


    public static String summarizeCoR(double coR) {

        // correlation coefficient ranges from -1 to 1
        // if the correlation coefficient greater than 0, there is a positive trend, the higher the value the stronger the trend
        // if the correlation coefficient is less than 0, there is a negative trend, the lower the value the stronger the trend
        // if the correlation coefficient is equal to 0, there is no correlation

        if (coR>=0.5) {
            return "strong positive trend in the data";
        }

        else if (coR>0) {
            return "weak positive trend in the data";
        }
        else if (coR<0 && coR>-0.5) {
            return "weak negative trend in the data";
        }
        else if (coR<-0.5) {
            return "strong negative trend in the data";
        }
        else {
            return "no trend in the data";
        }

    }


    public static double standardDeviation(double[] avgSeason) {
        double totalTemp=0.0, total2=0.0;
        double average, standardDeviation;

        // total temperatures for selected season
        for (int i=0;i<numYear;i++) {
            totalTemp+=avgSeason[i];
        }
        // calculate average
        average=totalTemp/numYear;

        // each temperature - average all squared
        for (int i=0;i<numYear;i++) {
            total2+=Math.pow((avgSeason[i]-average), 2);
        }

        standardDeviation=Math.sqrt(total2/numYear);
        return standardDeviation;
    }


    public static double[] medMinMax(double[] avgSeason) {
        double sortedSeason[]=new double[numYear];
        // make copy of avgSeason
        int z=0;
        for (double c:avgSeason) {
            sortedSeason[z]=c;
            z+=1;
        }
        // sort copied array
        Arrays.sort(sortedSeason);
        // get median, minimum and maximum values
        double median=0;
        if (numYear%2==0) {
        	median=(sortedSeason[numYear/2-1]+sortedSeason[numYear/2])/2;
        }
        else {
        	median=sortedSeason[(numYear-1)/2];
        }
        double min=sortedSeason[0];
        double max=sortedSeason[numYear-1];
        // save values to an array, then return that array
        double medMinMax[]= {median,min,max};
        return medMinMax;
    }


    public static double coefficientR(double[] avgSeason) {
        double sumX = 0, sumY = 0, sumXY = 0;
        double squareSumX = 0, squareSumY = 0;

        // array for X axis (year)
        double X[]=new double[numYear];
        for (int i=1;i<=numYear;i++) {
            X[i-1]=i;
        }

        for (int i = 0; i < numYear; i++)
        {
            // array X total sum
            sumX = sumX + X[i];

            // array Y (avgSeason) total sum
            sumY = sumY + avgSeason[i];

            // sum of X[i] * Y[i].
            sumXY = sumXY + X[i] * avgSeason[i];

            // sum of square of array elements
            squareSumX = squareSumX + X[i] * X[i];
            squareSumY = squareSumY + avgSeason[i] * avgSeason[i];
        }

        // correlation coefficient formula
        double corr = (numYear * sumXY - sumX * sumY)/ (double)(Math.sqrt((numYear * squareSumX -sumX * sumX) * (numYear * squareSumY -  sumY * sumY)));
        
        return corr;
        
          
         

    } // coefficient R


    public static int mode(double[]avgSeason) {
        double seasonRound[]=new double[numYear];
        int z=0;
        // make copy of avgSeason
        for (double c:avgSeason) {
            seasonRound[z]=c;
            z+=1;
        }

        // round to integers as it is unlikely decimal numbers will repeat in the array
        for (int i=0;i<numYear;i++) {
            seasonRound[i]=Math.round(seasonRound[i]);
        }

        // sort array containing rounded season temperatures
        Arrays.sort(seasonRound);

        int seasonMode=0;
        // seasonHold is the number currently being checked for number of repetitions
        // starts by checking the number of repetitions of the first number of sorted array
        double seasonHold=seasonRound[0];
        int repetitionCounter=1,maxRepetitionCounter=0;
        // note for loop starts at 1
        for (int x=1;x<numYear;x++) {    
            // if the previous number is the same as the current number add one to the counter
            if (seasonRound[x]==seasonHold) {
                repetitionCounter+=1;
            }
            // else if check to see if this number repeats more than the current
            // most repeating number
            else if(repetitionCounter>maxRepetitionCounter) {
                maxRepetitionCounter=repetitionCounter;
                // save this number as the current mode
                seasonMode=(int)seasonHold;

                repetitionCounter=1;
                seasonHold=seasonRound[x];
            }
            // otherwise ignore and reset variables
            else {
                repetitionCounter=1;
                seasonHold=seasonRound[x];
            }    
        }

        return seasonMode;
    } // mode for season


    public static void populate(Scanner sc, ArrayList<Double> winter, ArrayList<Double> summer, ArrayList<Double> spring, ArrayList<Double> fall) {
        double yearHold;
        yearHold=year;
        // sorts temperatures into respective seasons
        while (true) {
            if (month>=3 && month<=5) {
                spring.add(temp);
            }
            else if(month>=6 && month<=8) {
                summer.add(temp);
            }
            else if(month>=9 && month<=11) {
                fall.add(temp);
            }
            else if(month>= 12 || month <=2) {
                winter.add(temp);
            }
            // reads next set of data
            year=sc.nextDouble();
            month=sc.nextDouble();
            day = sc.nextDouble();
            temp=sc.nextDouble();

            // checks to see if it is a new year or that it is the last set of information
            if  (year!=yearHold || (year==9999 && day==99 && month==99)) {
                // find the average of every season in this year
                average(spring,summer,fall,winter);
                break;
            }

        }    
    } // populate per year


    public static void average(ArrayList<Double> spring, ArrayList<Double> summer, ArrayList<Double> fall, ArrayList<Double> winter) {
        double totalSpring=0, totalSummer=0, totalWinter=0, totalFall=0;
        // gets total of all temperatures in winter for that year
        for (int c=0; c<winter.size();c++) {
            totalWinter+=winter.get(c);
        }
        // gets total of all temperatures in summer and spring for that year
        // in the same for loop as summer and spring have same number of days
        for(int c=0;c<summer.size();c++) {
            totalSpring+=spring.get(c);
            totalSummer+=summer.get(c);
        }
        // get total temperatures in fall for that year
        for (int c=0;c<fall.size();c++) {
            totalFall+=fall.get(c);
        }
        // saves average to an array
        avgSpring[indexPop]=totalSpring/spring.size();
        avgFall[indexPop]=totalFall/fall.size();
        avgWinter[indexPop]=totalWinter/winter.size();
        avgSummer[indexPop]=totalSummer/summer.size();
        indexPop+=1;
        // resets all variables for next year
        totalFall=0;
        totalSpring=0;
        totalSummer=0;
        totalWinter=0;
        spring.clear();
        summer.clear();
        winter.clear();
        fall.clear();

    } // average per year

} //class


