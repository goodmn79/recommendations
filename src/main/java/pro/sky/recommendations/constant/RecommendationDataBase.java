package pro.sky.recommendations.constant;

import java.util.Map;

public class RecommendationDataBase {
    public static final String RECOMMENDATIONS = "RECOMMENDATIONS";
    public static final String QUERIES = "QUERIES";

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS %s %s";
    private static final String RECOMMENDATIONS_COLUMN = "(ID UUID PRIMARY KEY, PRODUCT_ID   UUID NOT NULL UNIQUE, PRODUCT_TEXT TEXT NOT NULL)";
    private static final String QUERIES_COLUMN = "(ID UUID PRIMARY KEY, RECOMMENDATION_ID   UUID NOT NULL, QUERY VARCHAR(100) NOT NULL, ARGUMENTS VARCHAR(255), NEGATE BOOLEAN NOT NULL)";

    public static final Map<String, String> TABLE_DATA = Map.of(
            RECOMMENDATIONS, RECOMMENDATIONS_COLUMN,
            QUERIES, QUERIES_COLUMN
    );

    public static String createTableQuery(String tableName) {
        return String.format(CREATE_TABLE, tableName, TABLE_DATA.get(tableName));
    }
}
