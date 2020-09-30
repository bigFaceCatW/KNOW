package com.know.plan.mode.proxy;

/**
 * @Author: FaceCat
 * @Date: 2020/9/30 15:36
 */
public class ProxyImage implements Image {
    private IntanceImage intanceImage;
    private String fileName;

    public ProxyImage (String fileName){
        this.fileName=fileName;
    }
    @Override
    public void display() {
        intanceImage = new IntanceImage(this.fileName);
        intanceImage.display();
    }
}
