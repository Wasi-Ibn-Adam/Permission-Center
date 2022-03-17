package com.wasitech.permissioncenter.java.com.wasitech.ting.Chat.Module;

import androidx.annotation.NonNull;

import com.wasitech.assist.classes.Tinggo;

import com.wasitech.basics.classes.Issue;

public class Ting {
    private String msgId;
    private Tinggo tinggo;
    private int reaction;

    public Ting() {
    }

    public Ting(Tinggo tinggo) {
        this.tinggo = tinggo;
    }

    public Ting(String msgId, Tinggo tinggo, int reaction) {
        this.msgId = msgId;
        this.tinggo = tinggo;
        this.reaction = reaction;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Tinggo getTinggo() {
        return tinggo;
    }

    public void setTinggo(Tinggo tinggo) {
        this.tinggo = tinggo;
    }

    public int getReaction() {
        return reaction;
    }

    public void setReaction(int reaction) {
        this.reaction = reaction;
    }

    @Override
    @NonNull
    public String toString() {
        return msgId+"::"+reaction+"::"+tinggo.toString()+"\n";
    }

    public Ting(String fromByte) {
        try {
            if (fromByte == null || fromByte.isEmpty())
                return;
            String[] b = fromByte.replace("\n", "").split("::");
            if (b.length == 6) {
                msgId = b[0];
                reaction = Integer.parseInt(b[1]);
                tinggo = new Tinggo(Integer.parseInt(b[2]), b[3], b[4], b[5]);
            }
        } catch (Exception e) {
            Issue.set(e,Ting.class.getName());
        }
    }


}
