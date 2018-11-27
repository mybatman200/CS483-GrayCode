package com.company;
import java.util.*;
import java.math.*;
import java.lang.*;
public class Main {

    public static void main(String[] args) {

        int [][] record = new int[10000][20];
        record = RecordsGenerator();
        int count =0;
        /*for(int i=0; i<record.length;i++){
           for(int a =0; a<record[i].length; a++){

               System.out.print(record[i][a] + " ");
           }
           System.out.print("\n");
        }*/
        int [][] newRecord = new int[10000][20];
        RadixSort1(record);
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
            int randomNumber = (int) (Math.random()*((10-2)+1))+2;
            fields[i]=randomNumber;
        }
        return fields;
    }

/*    public static int[][] RadixSort(int [][] record){
        int numb_iteration = 0;
        int [][] newRecord = new int[10000][20];
        for(int i=record.length-1; i<record.length; i--){
            System.out.print(record[i]);
        }
        return newRecord;

    }*/
    public static void RadixSort1(int [][] record){
        int numb_iteration = 0;
        int[][]tempArrayOrdered = new int[10000][20];
        //for(int i=record[0].length-1; i>=0; i--){
        for(int i=0; i<20; i++){

            HashMap<Integer, ArrayList<int[]>> sortedHash = new HashMap<Integer, ArrayList<int[]>>();
            for(int a=0; a<record.length;a++) {
                numb_iteration++;
                ArrayList<int[]> tempArray = new ArrayList<int[]>();
                int numb = record[a][i];

                if(!sortedHash.containsKey(numb)){
                    tempArray.add(record[a]);
                    sortedHash.put(numb, tempArray);
                }else{
                    tempArray = sortedHash.get(numb);
                    tempArray.add(record[a]);
                    sortedHash.put(numb, tempArray);
                }
                //System.out.println(numb);
            }
            ArrayList<Integer> sortedKeyList = new ArrayList<Integer>();
            for(int key: sortedHash.keySet()){
                sortedKeyList.add(key);
            }
            Collections.sort(sortedKeyList);

            //System.out.println(sortedKeyList.toString());
            int counter=0;
            int arrCounter =0;
            for(int a=0; a<sortedKeyList.size();a++){
                if(counter%2 ==0){
                    ArrayList<int[]> tempArray = sortedHash.get(sortedKeyList.get(a));
                    for(int arr=0; arr<tempArray.size();arr++) {
                        tempArrayOrdered[arrCounter] = tempArray.get(arr);
                        arrCounter++;
                    }
                }else{
                    ArrayList<int[]> tempArray = sortedHash.get(sortedKeyList.get(a));
                    Collections.reverse(tempArray);
                    for(int arr=0; arr<tempArray.size();arr++) {
                        tempArrayOrdered[arrCounter] = tempArray.get(arr);
                        arrCounter++;
                    }
                }
                counter++;
            }

        }
        for(int i=0; i<record.length;i++){
            for(int a =0; a<record[i].length; a++){

                System.out.print(tempArrayOrdered[i][a] + " ");
            }
            System.out.print("\n");
        }



    }

}
