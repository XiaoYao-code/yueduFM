package com.yaoxinyuan.dao;

import com.yaoxinyuan.model.Section;
import com.yaoxinyuan.util.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SectionDao {
    public void insert(int bid, String name) throws SQLException {
        String sql = "insert into sections (bid, name) values (?, ?)";

        try (Connection c = DB.getConnection()) {
            try (PreparedStatement s = c.prepareStatement(sql)) {
                s.setInt(1, bid);
                s.setString(2, name);

                s.executeUpdate();
            }
        }
    }

    public List<Section> selectByBid(int bid) throws SQLException {

        String sql = "select sections.sid, uuid, name " +
                "from sections left join audios on sections.sid = audios.sid " +
                "where bid = ? order by sections.sid";
        List<Section> sections = new ArrayList<>();
        try (Connection c = DB.getConnection()) {
            try (PreparedStatement s = c.prepareStatement(sql)) {
                s.setInt(1, bid);

                try (ResultSet r = s.executeQuery()) {
                    while (r.next()) {
                        Section section = new Section(
                                r.getInt("sid"),
                                r.getString("uuid"),
                                r.getString("name")
                        );
                        sections.add(section);
                    }
                }
            }
        }

        return sections;
    }
}
