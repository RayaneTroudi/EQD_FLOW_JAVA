package com.rayane.minifast.marketdata;

import com.rayane.minifast.surface.ImpliedVolSurface;
import com.rayane.minifast.surface.NearestNeighborInterpolator;
import com.rayane.minifast.surface.VolPoint;
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
        List<OptionQuoteRow> rows = readRawRows(date);

        List<OptionQuoteRow> filteredOTM = filteredOtmAndLiquid(rows);

        double spot = rows.get(0).getUnderlyingLast();

        List<VolPoint> volPoints = new ArrayList<>();
        for (OptionQuoteRow row : filteredOTM) {
            boolean isOtmCall = row.getStrike() >= row.getUnderlyingLast();
            double vol = isOtmCall ? row.getCallIV() : row.getPutIV();
            volPoints.add(new VolPoint(row.getStrike(), row.getExpireDate(), vol));
        }

        ImpliedVolSurface surface = new ImpliedVolSurface(date, volPoints, new NearestNeighborInterpolator());

        return new MarketData(date, spot, assumptions, surface);
    }

    private List<OptionQuoteRow> readRawRows(LocalDate date) {
        List<OptionQuoteRow> rows = new ArrayList<>();

        String sql = "SELECT " +
            "\"[UNDERLYING_LAST]\" AS underlying_last, " +
            "\"[STRIKE]\" AS strike, " +
            "\"[EXPIRE_DATE]\" AS expire_date, " +
            "\"[C_IV]\" AS c_iv, " +
            "\"[P_IV]\" AS p_iv, " +
            "\"[C_BID]\" AS c_bid, " +
            "\"[C_ASK]\" AS c_ask, " +
            "\"[P_BID]\" AS p_bid, " +
            "\"[P_ASK]\" AS p_ask, " +
            "\"[C_VOLUME]\" AS c_volume, " +
            "\"[P_VOLUME]\" AS p_volume " +
            "FROM '" + filePath + "' WHERE \"[QUOTE_DATE]\" = ?";

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

        return rows;
    }

    private List<OptionQuoteRow> filteredOtmAndLiquid(List<OptionQuoteRow> rows) {
        List<OptionQuoteRow> result = new ArrayList<>();

        for (OptionQuoteRow row : rows) {
            boolean isOtmCall = row.getStrike() >= row.getUnderlyingLast();
            double relevantVolume = isOtmCall ? row.getCallVolume() : row.getPutVolume();

            if (relevantVolume > 0) {
                result.add(row);
            }
        }

        return result;
    }
}
