package com.know.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: FaceCat
 * @Date: 2020/12/7 14:43
 */
public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);


//    冒泡算法降序
    public static void bubbleSortDesc(int array[]){
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array.length-1;j++){
                if(array[j]<array[j+1]){
                    int tem = array[j+1];
                    array[j+1] =array[j];
                    array[j]=tem;
                }
            }
        }
        logger.error(Arrays.toString(array));
    }
//    冒泡算法升序
    public static void bubbleSortAsc(int array[]){
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array.length-1;j++){
                if(array[j]>array[j+1]){
                    int tem = array[j+1];
                    array[j+1] =array[j];
                    array[j]=tem;
                }
            }
        }
        logger.error(Arrays.toString(array));
    }
// m^n
    public static int mn(int m,int n){
            int result = 1;
            for (int i = 0; i < n; i++) {
                result *= m;
            }
            return result;
        }
// 求50人围成圈，3和3的倍数都出局（约瑟夫环！）
// num为50、num为3
public static List<Integer> getNum(int math,int num){

    List<Integer> list = new LinkedList<Integer>();
    for(int i=0;i<math;i++){
        list.add(new Integer(i+1));
    }
    int index=-1;//定义下标，3的下标为2，因此从-1开始
    while(list.size()>1){
        index = (index + num) % list.size(); //得到应该出局的下标
        list.remove(index--);  //先去除，再减1
    }
    return list ;//返回它的值
}


    public static void main(String[] args) {
//  二维数组：
//        int[][] array = {{1,2,3},{4,5,6},{7,8,9}};
//        int[][] array1 = new int[5][6];
//  数组三种方式：
        int[] arr = {1,3,4,7,6,5,10,9,2,8};
        int[] arr1 = new int[]{1,3,4,7,6,5,10,9,2,8};
        int[] arr2 = new int[]{10};//实际里面为1,2,3,4,5,6,7,8,9
        bubbleSortAsc(arr1);
    }





}
