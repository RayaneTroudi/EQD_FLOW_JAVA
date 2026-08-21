package com.rayane.minifast.marketdata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MarketDataParquet implements MarketDataSource {

    private final String filePath;
    private final MarketDataAssumptions assumptions;

    public MarketDataParquet(String filePath, MarketDataAssumptions assumptions) {
        this.filePath = filePath;
        this.assumptions = assumptions;
    }

    @Override
    public MarketData load(LocalDate date) {
        List<OptionQuoteRow> rows = new ArrayList<>();

        String sql = "SELECT * FROM '" + filePath + "' WHERE QUOTE_DATE = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:duckdb:");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, date.toString());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OptionQuoteRow row = new OptionQuoteRow(
                        rs.getDouble("UNDERLYING_LAST"),
                        rs.getDouble("STRIKE"),
                        rs.getDate("EXPIRE_DATE").toLocalDate(),
                        rs.getDouble("C_IV"),
                        rs.getDouble("P_IV"),
                        rs.getDouble("C_BID"),
                        rs.getDouble("C_ASK"),
                        rs.getDouble("P_BID"),
                        rs.getDouble("P_ASK"),
                        rs.getDouble("C_VOLUME"),
                        rs.getDouble("P_VOLUME")
                    );
                    rows.add(row);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to load market data for " + date, e);
        }

        System.out.println("Loaded " + rows.size() + " rows for " + date);

        // TODO: filtrer rows (OTM + liquidité), construire ImpliedVolSurface, extraire spot
        // return new MarketData(date, spot, assumptions, surface);
        return null;
    }
}