package com.know.plan.mode.factory;

/**
 * @Author: Facecat
 * @Date: 2020/9/20 11:03
 */
public class OnepieceFactory implements AnimeFactory {
    @Override
    public Anime create() {
        System.out.println("创建onepiece类");
        return  new OnePiece();
    }
}
