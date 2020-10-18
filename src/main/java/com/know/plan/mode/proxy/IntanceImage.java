package com.know.plan.mode.proxy;

/**
 * @Author: FaceCat
 * @Date: 2020/9/30 15:35
 */
public class IntanceImage implements Image {

    private  String  fileName;

    public IntanceImage( String fileName){
        this.fileName=fileName;
        loadImg();
    }

    @Override
    public void display() {
        System.out.println("加载照片>>>"+this.fileName);
    }

    public void loadImg(){
        System.out.println("照片加载");
    }
}
