package com.company;
import com.sun.tools.javac.util.ArrayUtils;

import java.lang.reflect.Array;
import java.util.*;
import java.math.*;
import java.lang.*;
public class Main {

    public static void main(String[] args) {

        int [][] record = new int[10000][20];
        int [][] record2 = new int[10000][20];
        int [][] record3 = new int[10000][20];

        /**
         * Generating Nis randomly from 1-10
         */
//        record = RecordsGenerator();
//        record2 = record;
        //record3 = record;


        /**
         * Generating each Nis randomly
         */
        int []nis = NIsGenerator();
        record = randomNumberGenerator(nis);
        record2 = record;
        record3 = record;
        /***/

        int count =0;

        int [][] radixRecord = new int[10000][20];
        radixRecord = RadixSort1(record);
        printRecords(radixRecord);

        record2 = MergeSort(record2,0, record2.length-1);
        //printRecords(record2);

        long [][]record31 = RankSort(record3, nis);
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

    public static long rank(int [] record, int [] nis){

        long i=record[0];
        long nj= 0;
        long i2 = 0;
        for(int j=1; j<record.length-1;j++){
            nj = nis[j];
            if(i%2==0){
                i2=record[j];
            }else{
                i2 = nj-1-record[j];
            }
            i= (i* nj) + i2;
        }

        return i;
    }

    public static long[][] RankSort(int[][] record, int[]nis){

        long [][] record2 = new long[10000][21];
        //for(int[] r : record){

        for(int s=0;s<record.length;s++){

            int[] r = record[s];
            long i = 0;
            //int nj=0;
            record2[s][0]=record[s][0];
            i = rank(record[s], nis);
            for(int j=1; j<r.length; j++){
                record2[s][j]=record[s][j];
//                nj = nis[j];
//                int i2;
//                if(i % 2== 0){
//                    i2= r[j];
//                }
//                else{
//                    i2 = nj - 1 -r[j];
//                }
//                i= ((i*nj) + i2);

            }
            record2[s][20] = i;

        }

        record2 = QuickSort(record2, 0, record2.length-1);

        return record2;
    }

    public static boolean GrayCodeHorners(long[] X, long [] Y){

        if(X[X.length-1]<Y[Y.length-1]){
            return true;
        }
        else{
            return false;
        }

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
        }

        temp = record[right];
        record[right] = record[i];
        record[i] = temp;
        return i;
    }

    public static long[][] QuickSort(long[][] record, int left, int right){
        if(left<=right){
            int pi = Partition(record, left,right);
            QuickSort(record, left, pi-1);
            QuickSort(record, pi+1, right);
        }
        return record;
    }

    public static int[][] MergeSort(int [][] record1, int left, int right) {

        if (left < right) {
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
        }

        while (i <= middle) {
            numbers[k] = helper[i];
            k++;
            i++;
        }
    }

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

    public static int[][] RadixSort1(int [][] record){
        int numb_iteration = 0;

        for(int i=0; i<20; i++){
            int[][]tempArrayOrdered = new int[10000][20];

            HashMap<Integer, ArrayList<int[]>> sortedHash = new HashMap<Integer, ArrayList<int[]>>(); // Store a key and its value of each of the element of the array and key is the element at the field
            int numbCount=0;

            for(int a=0; a<record.length;a++) {

                numb_iteration++;

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

            }
            Collections.sort(sortedKeyList);// sort the key

            //System.out.println(sortedKeyList.toString());
            int counter=0;
            int arrCounter =0;

            //get key from the sorted keylist and append it into the array
            for(int a=0; a<sortedKeyList.size();a++){
                if(counter%2 ==0){
                    ArrayList<int[]> tempArray = sortedHash.get(sortedKeyList.get(a));
                    for(int arr=0; arr<tempArray.size();arr++) {
                        tempArrayOrdered[arrCounter] = tempArray.get(arr);
                        arrCounter++;
                    }
                }else{
                    ArrayList<int[]> tempArray = sortedHash.get(sortedKeyList.get(a));
                    for(int arr=0; arr<tempArray.size();arr++) {
                        //tempArrayOrdered[arrCounter] = reverse(tempArray.get(arr));
                        //Collections.reverse(tempArray.get(arr));
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



}
