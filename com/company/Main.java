package com.company;
import com.sun.tools.javac.util.ArrayUtils;

import java.util.*;
import java.math.*;
import java.lang.*;
public class Main {

    public static void main(String[] args) {

        int [][] record = new int[10000][20];
        record = RecordsGenerator();
        int count =0;

        int [][] radixRecord = new int[10000][20];
        radixRecord = RadixSort1(record);




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

    public static int[][] RadixSort1(int [][] record){
        int numb_iteration = 0;
        int[][] arrayOrdered = new int[10000][20];

        for(int i=0; i<20; i++){
            int[][]tempArrayOrdered = new int[10000][20];

            HashMap<Integer, ArrayList<int[]>> sortedHash = new HashMap<Integer, ArrayList<int[]>>();
            int numbCount=0;
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
                    for(int arr=0; arr<tempArray.size();arr++) {
                        //tempArrayOrdered[arrCounter] = reverse(tempArray.get(arr));
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
            /*for(int a =0; a<record[i].length; a++){

                System.out.print(record[i][a] + " ");
            }
            System.out.print("\n");*/
        }
        return record;
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
