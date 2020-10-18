package com.know.plan.mode.factory;

/**
 * @Author: Facecat
 * @Date: 2020/9/20 11:06
 */
//工厂模式
public class Test {
    public static void main(String[] args) {
        AnimeFactory animeFactory = new OnepieceFactory();
        Anime anime=animeFactory.create();
        anime.author();
    }
}
