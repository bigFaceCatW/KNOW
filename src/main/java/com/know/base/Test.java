package com.know.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

/**
 * @Author: FaceCat
 * @Date: 2020/12/7 14:43
 */
public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);


//    1.冒泡算法降序
    public static void bubbleSortDesc(int array[]){
        for(int q=0;q<array.length;q++){
            for(int s=0;s<array.length-1;s++){
                if(array[s]<array[s+1]){
                    int flag =array[s+1];
                    array[s+1]=array[s];
                    array[s]=flag;
                }
            }

        }



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

//    2.冒泡算法升序
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
// 3.m^n
    public static int mn(int m,int n){
            int result = 1;
            for (int i = 0; i < n; i++) {
                result *= m;
            }
            return result;
        }


// 4.求50人围成圈，3和3的倍数都出局（约瑟夫环！）
// math为50、num为3
public static List<Integer> getNum(int math,int num){

    List<Integer> list = new LinkedList<Integer>();
    for(int i=0;i<math;i++){
        list.add(new Integer(i+1));
    }
    int index=-1;//定义下标，3的下标为2，因此从-1开始
    while(list.size()>1){
        index = (index + num) % list.size(); //得到应该出局的下标,%为取余（2%50，左边大于右边则为本身2）
        list.remove(index--);  //先去除，再减1
    }
    System.out.println(list.toString());
    return list ;//返回它的值
}
//4.map遍历
    public static Map<String,Object> entrySetMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("姓名", "文磊");
        for(Map.Entry<String,Object> entry:map.entrySet()){
           System.out.println("遍历key>>>"+entry.getKey());
           System.out.println("遍历value>>>"+entry.getValue());
        }
        return map;
    }

    public static void main(String[] args) throws IOException {
//    io流：字节转字符:InputStringReader
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("a.txt")));
        String str;
        while((str=br.readLine())!=null){
            System.out.println(str);
        }
//      字符转字节：将字符输入到文件
        Writer out = new BufferedWriter(new FileWriter("a.txt"));
        out.write(str);






















//  二维数组：
//        int[][] array = {{1,2,3},{4,5,6},{7,8,9}};
//        int[][] array1 = new int[5][6];
//  5.数组三种方式：
//        int[] arr = {1,3,4,7,6,5,10,9,2,8};
//        int[] arr1 = new int[]{1,3,4,7,6,5,10,9,2,8};
//        int[] arr2 = new int[]{10};//实际里面为1,2,3,4,5,6,7,8,9
//        bubbleSortDesc(arr1);
        entrySetMap();
    }






}
