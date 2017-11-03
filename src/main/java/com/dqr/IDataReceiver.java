package com.dqr;

import java.io.IOException;

/**
 * Data Receiver Interface.
 * <p>
 * Created by dqromney on 10/21/17.
 */
public interface IDataReceiver {

    void init(String[] args) throws IOException;

    void execute();
}
