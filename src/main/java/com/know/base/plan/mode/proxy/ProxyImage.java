package com.know.base.plan.mode.proxy;

/**
 * @Author: FaceCat
 * @Date: 2020/9/30 15:36
 */
//代理类
public class ProxyImage implements Image {
    private IntanceImage intanceImage;
    private String fileName;

    public ProxyImage (String fileName){
        this.fileName=fileName;
    }
    @Override
    public void display() {
        if(intanceImage==null){
            intanceImage = new IntanceImage(this.fileName);
        }
        intanceImage.display();
    }
}
