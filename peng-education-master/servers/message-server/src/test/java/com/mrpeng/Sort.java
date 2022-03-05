package com.mrpeng;

public class Sort {
    public static void main(String[] args) {
        int []arr =new int[]{2,6,56,45,5,23,12};
        for (int i = 0; i <arr.length; i++) {
            for (int j = i+1; j <arr.length ; j++) {
                if(arr[i]<arr[j]){
                    int temp =arr[j];
                    arr[j]=arr[i];
                    arr[i] =temp;
                }
            }
        }
        for (int i : arr) {
            System.out.print(i+"  ");
        }
    }
}
