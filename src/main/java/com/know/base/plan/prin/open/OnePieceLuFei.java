package com.know.base.plan.prin.open;

/**
 * @Author: Facecat
 * @Date: 2020/9/15 22:29
 */
public class OnePieceLuFei extends OnePiece {

    public OnePieceLuFei(Integer id, String name, Double price) {
        super(id, name, price);
    }

   public Double getPresonPrice(){
      return super.getPrice()*0.6;
   }

}
