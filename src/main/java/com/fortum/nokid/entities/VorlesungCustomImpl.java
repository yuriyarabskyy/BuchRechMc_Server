package com.fortum.nokid.entities;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * Created by yuriyarabskyy on 22/05/16.
 */
public class VorlesungCustomImpl {

    @Autowired
    DataSource dataSource;

    public boolean insertPdf(long id, byte[] blob) {

        InputStream stream = new ByteArrayInputStream(blob);

        try {

            Connection connection = dataSource.getConnection();

            String query = "update VorlesungPdf set content = ? where id = ?";

            PreparedStatement prmt = connection.prepareStatement(query);

            prmt.setBinaryStream(1, stream, blob.length);

            prmt.setLong(2, id);

            prmt.executeUpdate();


        } catch (SQLException e) { return false; }

        return true;

    }

    public void getPdf(long id) {


    }

}
