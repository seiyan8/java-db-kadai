package kadai_007;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Posts_Chapter07 {
    public static void main(String[] args) {
        // データベース接続情報
        String url = "jdbc:mysql://localhost:3306/challenge_java";
        String user = "root";
        String password = "seiya718";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("1データベース接続成功：" + conn);

            // レコード追加
            System.out.println("2レコード追加を実行します");

            String insertSQL = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES " +
                    "(1003, '2023-02-08', '昨日の夜は徹夜でした・・', 13)," +
                    "(1002, '2023-02-08', 'お疲れ様です！', 12)," +
                    "(1003, '2023-02-09', '今日も頑張ります！', 18)," +
                    "(1001, '2023-02-09', '無理は禁物ですよ！', 17)," +
                    "(1002, '2023-02-10', '明日から連休ですね！', 20)";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);
            int addedRows = pstmt.executeUpdate();
            System.out.println("3" + addedRows + "件のレコードが追加されました");

            // データ検索
            System.out.println("4ユーザーIDが1002のレコードを検索しました");

            String selectSQL = "SELECT posted_at, post_content, likes FROM posts WHERE user_id = ?";
            pstmt = conn.prepareStatement(selectSQL);
            pstmt.setInt(1, 1002);
            ResultSet result = pstmt.executeQuery();

            int count = 1;
            while (result.next()) {
                Date postedAt = result.getDate("posted_at");
                String content = result.getString("post_content");
                int likes = result.getInt("likes");

                System.out.printf("5%d件目：投稿日時=%s／投稿内容=%s／いいね数=%d%n",
                        count++, postedAt, content, likes);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}