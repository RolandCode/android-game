package com.example;

import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

public class Sound {
    private SoundPool soundPool;
    private HashMap<String, Integer> hashMap;


    public Sound(){
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        hashMap = new HashMap<String, Integer>();
     }

    public void add(String name, int resource){
        hashMap.put(name, soundPool.load(Game.getInstance().getActivity(), resource, 1));
    }

    public void playSound(String sound) {
        soundPool.play(hashMap.get(sound), 1, 1, 0, 0, 1);    }
}
