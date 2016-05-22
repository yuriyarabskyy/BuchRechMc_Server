package com.fortum.nokid.entities;

/**
 * Created by yuriyarabskyy on 22/05/16.
 */
public interface VorlesungCustom {

    public boolean insertPdf(long id, int length, byte[] blob);

    public void getPdf(long id);

}
