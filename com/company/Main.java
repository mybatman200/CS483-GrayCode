package com.company;
import com.sun.tools.javac.util.ArrayUtils;


import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.math.*;
import java.lang.*;
public class Main {

    static int record1Iter=0;
    static int record2Iter =0;
    static int record3Iter = 0;
    public static void main(String[] args) throws IOException {

        int [][] record = new int[10000][20];
        int [][] record2 = new int[10000][20];
        int [][] record3 = new int[10000][20];

         // Generating Nis randomly from 1-10
//        record = RecordsGenerator();
//        record2 = record;
//        record3 = record;


        //Generating each Nis randomly
        int []nis = NIsGenerator();
        record = randomNumberGenerator(nis);
        record2 = record;
        record3 = record;

        int[] scoreBefore = CalculateScore(record);

        System.out.println("BinaryScore: "+ scoreBefore[0] + " fullScore: "+ scoreBefore[1]);
        WriteToFile(record, "Pre_Sort_Record.txt");

        int [][]radixRecord = RadixSort1(record);
        int [][] grayOrderRecord = MergeSort(record2,0, record2.length-1);
        long [][]hornersRecord = RankSort(record3, nis);

        int[] scoreAfter = CalculateScore(record);
        WriteToFile(radixRecord,"radixRecord.txt");
        WriteToFile(grayOrderRecord,"grayOrderRecord.txt");
        WriteToFile(hornersRecord,"hornersRecord.txt");

        WriteCountToFile(record1Iter,record2Iter,record3Iter, scoreBefore[0], scoreBefore[1], scoreAfter[0], scoreAfter[1], "SortCount.txt");
        //printRecords(record31);
    }

    public static int[][] randomNumberGenerator(int []nis){

        int [][] records= new int[10000][20];
        for(int i=0;i<records.length;i++){
            for(int j=0; j<records[i].length;j++){
                int randomNumber = (int) (Math.random()*((nis[j]-1)+1))+1;
                records[i][j]=randomNumber;
            }
        }
        return records;
    }
    public static int[] NIsGenerator(){
        int[] nis = new int[20];
        for(int i=0; i<20; i++){
            int randomNumber = (int) (Math.random()*((10-1)+1))+1;
            nis[i] = randomNumber;
        }
        return nis;

    }
    public static boolean GrayCode(int[] X, int [] Y){

        int d=-1;
        int totalD =0;
        for(int i=-1; i<X.length-1;i++){
            if(X[i+1] != Y[i+1]){
                d = i;
                break;
                //System.out.println(d);
            }
        }
        for(int i=0; i<d+1; i++){
            totalD = totalD+X[i];
            //System.out.println("HELLO " + totalD);
        }

        if(totalD%2 ==0){
            return X[d+1]<Y[d+1];
        }
        else{
            return Y[d+1]<X[d+1];
        }

    }

    /**
     *RankSort / HornersSort
     * support methods: rank(), GrayCodeHorners()
     */
    public static long[][] RankSort(int[][] record, int[]nis){

        long [][] record2 = new long[10000][21];
        //for(int[] r : record){

        for(int s=0;s<record.length;s++){
            record2Iter++;
            int[] r = record[s];
            long i = 0;
            record2[s][0]=record[s][0];
            i = rank(record[s], nis);
            for(int j=1; j<r.length; j++){
                record2[s][j]=record[s][j];
                record2Iter++;
            }
            record2[s][20] = i;

        }

        record2 = MergeSort(record2, 0, record2.length-1);

        return record2;
    }

    public static long rank(int [] record, int [] nis){

        long i=record[0];
        long n= 0;

        for(int j=1; j<record.length-1;j++){
            record2Iter++;
            long i2 = 0;
            n = nis[j];
            if(i%2==0){
                i2=record[j];
            }else{
                i2 = n-1-record[j];
            }
            i= (i* n) + i2;
        }

        return i;
    }

    public static boolean GrayCodeHorners(long[] X, long [] Y){

        if(X[X.length-1]<Y[Y.length-1]){
            return true;
        }
        else{
            return false;
        }

    }

    /**
        QuickSort()
        support method: Partition()
     */

    public static long[][] QuickSort(long[][] record, int left, int right){
        if(left<=right){
            record1Iter++;
            int pi = Partition(record, left,right);
            QuickSort(record, left, pi-1);
            QuickSort(record, pi+1, right);
        }
        return record;
    }

    public static int Partition(long[][]record, int left, int right){
        long[] pivot = record[right];
        /*for(int i=0; i<pivot.length; i++){
            System.out.print(pivot[i]);
        }
        System.out.print("\n");*/
        long [] temp;
        int i = left;

        for (int j=left; j<right;j++){
            if(GrayCodeHorners(record[j],pivot)){
                temp = record[j];
                record[j] = record[i];
                record[i] = temp;
                i += 1;
            }
            record1Iter++;
        }

        temp = record[right];
        record[right] = record[i];
        record[i] = temp;
        return i;
    }

    /**
        MergeSort
        support method: merge()
     */
    public static int[][] MergeSort(int [][] record1, int left, int right) {

        if (left < right) {
            record1Iter++;
            int middle = left + (right - left) / 2;
            MergeSort(record1,left, middle);
            MergeSort(record1,middle + 1, right);
            merge(record1, left, middle, right);
        }
        return record1;
    }

    public static void merge(int[][]numbers,int left, int middle, int right) {
        int [][] helper = new int [10000][20];
        for (int i = left; i <= right; i++) {
            helper[i] = numbers[i];
            record1Iter++;
        }

        int i = left;
        int j = middle + 1;
        int k = left;

        while (i <= middle && j <= right) {
            if (GrayCode(helper[i] , helper[j])) {
                numbers[k] = helper[i];
                i++;
            } else {
                numbers[k] = helper[j];
                j++;
            }
            k++;
            record1Iter++;
        }

        while (i <= middle) {
            numbers[k] = helper[i];
            k++;
            i++;
            record1Iter++;
        }
    }


    public static long[][] MergeSort(long [][] record1, int left, int right) {

        if (left < right) {
            record1Iter++;
            int middle = left + (right - left) / 2;
            MergeSort(record1,left, middle);
            MergeSort(record1,middle + 1, right);
            merge(record1, left, middle, right);
        }
        return record1;
    }

    public static void merge(long[][]numbers,int left, int middle, int right) {
        long [][] helper = new long [10000][20];
        for (int i = left; i <= right; i++) {
            helper[i] = numbers[i];
            record1Iter++;
        }

        int i = left;
        int j = middle + 1;
        int k = left;

        while (i <= middle && j <= right) {
            if (GrayCodeHorners(helper[i] , helper[j])) {
                numbers[k] = helper[i];
                i++;
            } else {
                numbers[k] = helper[j];
                j++;
            }
            k++;
            record1Iter++;
        }

        while (i <= middle) {
            numbers[k] = helper[i];
            k++;
            i++;
            record1Iter++;
        }
    }

    /**
    RadixSort Method
     */
    public static int[][] RadixSort1(int [][] record){

        for(int i=0; i<20; i++){
            record3Iter++;
            int[][]tempArrayOrdered = new int[10000][20];

            HashMap<Integer, ArrayList<int[]>> sortedHash = new HashMap<Integer, ArrayList<int[]>>(); // Store a key and its value of each of the element of the array and key is the element at the field
            int numbCount=0;

            for(int a=0; a<record.length;a++) {
                record3Iter++;

                ArrayList<int[]> tempArray = new ArrayList<int[]>();
                int numb = record[a][i]; // element at each record

                if(!sortedHash.containsKey(numb)){ // check if the element is in the hashmap, if not then store it and its array
                    tempArray.add(record[a]);
                    sortedHash.put(numb, tempArray);
                }else{
                    tempArray = sortedHash.get(numb); // if it's a available, then store it array with the key
                    tempArray.add(record[a]);
                    sortedHash.put(numb, tempArray);
                }
            }
            ArrayList<Integer> sortedKeyList = new ArrayList<Integer>();// store a sortedkeylist
            for(int key: sortedHash.keySet()){
                sortedKeyList.add(key);
                record3Iter++;
            }
            Collections.sort(sortedKeyList);// sort the key

            int counter=0;
            int arrCounter =0;

            //get key from the sorted keylist and append it into the array
            for(int a=0; a<sortedKeyList.size();a++){
                record3Iter++;
                if(counter%2 ==0){
                    ArrayList<int[]> tempArray = sortedHash.get(sortedKeyList.get(a));
                    for(int arr=tempArray.size()-1; arr>=0;arr--){
                        tempArrayOrdered[arrCounter] = tempArray.get(arr);
                        arrCounter++;
                    }
                }else{
                    ArrayList<int[]> tempArray = sortedHash.get(sortedKeyList.get(a));
                    for(int arr=0; arr<tempArray.size();arr++) {
                        tempArrayOrdered[arrCounter] = tempArray.get(arr);
                        arrCounter++;
                    }
                }
                counter++;
            }

            record = tempArrayOrdered;
        }


        for(int i=0; i<record.length;i++){
            record[i] = reverse(record[i]);
        }
        return record;
    }

    /**
     Generate random number from 1-10 for the whole dataset
     Support method: FieldGenerator
     */
    public static int[][] RecordsGenerator(){
        int[][] record = new int[10000][20];
        for(int i=0; i<record.length;i++){
            record[i] = FieldGenerator();
        }

        return record;
    }

    public static int[] FieldGenerator(){
        int[] fields = new int[20];

        for(int i=0; i<fields.length; i++){
            int randomNumber = (int) (Math.random()*((10-1)+1))+1;
            fields[i]=randomNumber;
        }
        return fields;
    }

    /**
        print records method for testing
     */
    public static void printRecords(int[][]record){
        for(int i=0; i<record.length;i++){
            //record[i] = reverse(record[i]);
            for(int a =0; a<record[i].length; a++){

                System.out.print(record[i][a] + " ");
            }
            System.out.print("\n");
        }
    }

    public static void printRecords(long[][]record){
        for(int i=0; i<record.length;i++){
            //record[i] = reverse(record[i]);
            for(int a =0; a<record[i].length; a++){

                System.out.print(record[i][a] + " ");
            }
            System.out.print("\n");
        }
    }


    public static int[] reverse(int[] objectArray){
        for(int i = 0; i < objectArray.length / 2; i++)
        {
            int temp = objectArray[i];
            objectArray[i] = objectArray[objectArray.length - i - 1];
            objectArray[objectArray.length - i - 1] = temp;
        }

        return objectArray;
    }

    /**
        Method to write records to file
     */
    public static void WriteToFile(int[][] record, String name) throws IOException{
        String strFilePath = "/Users/tringuyen/Desktop/"+name;
        BufferedWriter writer = new BufferedWriter(new FileWriter(strFilePath));
        writer.append("");
        String str = "";
        for(int i=0;i<record.length;i++){
            for(int j=0; j<record[i].length; j++){
                str = record[i][j]+" ";
                writer.append(str);
            }
            str = "\n";
            writer.append(str);
        }

        writer.close();

    }
    public static void WriteToFile(long[][] record, String name) throws IOException{
        String strFilePath = "/Users/tringuyen/Desktop/"+name;
        BufferedWriter writer = new BufferedWriter(new FileWriter(strFilePath));
        writer.append("");
        String str = "";
        for(int i=0;i<record.length;i++){
            for(int j=0; j<record[i].length; j++){
                str = record[i][j]+" ";
                writer.append(str);
            }
            str = "\n";
            writer.append(str);
        }

        writer.close();

    }

    public static void WriteCountToFile(int i1, int i2, int i3, int binaryScore_before, int fullScore_before, int binaryScore_after, int fullScore_after,String name) throws IOException{
        String strFilePath = "/Users/tringuyen/Desktop/"+name;
        BufferedWriter writer = new BufferedWriter(new FileWriter(strFilePath));
        writer.append("");
        String str = "grayCount:"+i1 + "\n";
        writer.append(str);
        String str2 = "hornersCount:"+i2+ "\n";
        writer.append(str2);
        String str3 = "radixCount:"+i3+ "\n";
        writer.append(str3);
        String str4 = "binary score before:"+binaryScore_before+ "\n";
        writer.append(str4);
        String str5 = "full score before:"+fullScore_before+ "\n";
        writer.append(str5);
        String str6 = "binary score after:"+binaryScore_after+ "\n";
        writer.append(str6);
        String str7 = "full score after:"+fullScore_after+ "\n";
        writer.append(str7);
        writer.close();
    }

    public static int[] CalculateScore(int[][] records){
        int binary_score=0;
        int full_score =0;
        for(int j=0; j<records[0].length; j++){
            for(int i=1; i<records.length; i++){
                if(records[i][j] != records[i-1][j]){
                    binary_score++;
                    full_score = full_score + Math.abs(records[i][j]-records[i-1][j]);
                }
            }
        }
        int [] scores = {binary_score, full_score};
        return scores;
    }
    public static int[] CalculateScore(long[][] records){
        int binary_score=0;
        int full_score =0;
        for(int j=0; j<records[0].length; j++){
            for(int i=1; i<records.length; i++){
                if(records[i][j] != records[i-1][j]){
                    binary_score++;
                    full_score = full_score +  (int) Math.abs(records[i][j]-records[i-1][j]);
                }
            }
        }
        int [] scores = {binary_score, full_score};
        return scores;
    }




}
