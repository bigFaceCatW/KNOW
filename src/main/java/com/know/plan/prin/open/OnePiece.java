package com.know.plan.prin.open;

/**
 * @Author: Facecat
 * @Date: 2020/9/15 22:13
 */
public class OnePiece implements Anime {
    private Integer id;
    private String Name;
    private Double price;

    public OnePiece() {
    }

    public OnePiece(Integer id, String name, Double price) {
        this.id = id;
        Name = name;
        this.price = price;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.Name;
    }

    @Override
    public Double getPrice() {
        return this.price;
    }



//    开闭原则
    public static void main(String[] args) {
        Anime onePiece = new OnePiece(1,"海贼王",11800D);
        System.out.println(onePiece.getId()+"\n"+onePiece.getName()+"\n"+onePiece.getPrice());

        Anime onePieceLuFei = new OnePieceLuFei(1,"海贼王",11800D);
        OnePieceLuFei dto = (OnePieceLuFei)onePieceLuFei;
        System.out.println(onePieceLuFei.getId()+"\n"+onePieceLuFei.getName()+"\n"+onePieceLuFei.getPrice()+"\n"+dto.getPresonPrice());
    }
}
