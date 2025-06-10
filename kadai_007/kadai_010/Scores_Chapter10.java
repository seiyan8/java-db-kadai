package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Scores_Chapter10 {
    public static void main(String[] args) {
        Connection con = null;
        try {
            // データベースに接続
        	con = DriverManager.getConnection(
        		    "jdbc:mysql://localhost:3306/challenge_java?useSSL=false&serverTimezone=Asia/Tokyo",
        		    "root",
        		    "seiya718"
        	);
        	con.setAutoCommit(true);
        	
        	System.out.println("1データベース接続成功：" + con);
            // 点数更新
            String updateSql = "UPDATE scores SET score_math = 95, score_english = 80 WHERE name = '武者小路勇気'";
            Statement stmt = con.createStatement();
            System.out.println("2レコード更新を実行します");
            int rows = stmt.executeUpdate(updateSql);
            System.out.println(rows + "件のレコードが更新されました");

            // 並べ替えして取得
            String selectSql = "SELECT * FROM scores ORDER BY score_math DESC, score_english DESC";
            ResultSet rs = stmt.executeQuery(selectSql);
            System.out.println("4数学・英語の点数が高い順に並べ替えました");

            int count = 1;
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int math = rs.getInt("score_math");
                int english = rs.getInt("score_english");

                System.out.printf("%d件目：生徒ID=%d／氏名=%s／数学=%d／英語=%d%n",
                        count, id, name, math, english);
                count++;
            }

            // 接続を閉じる
            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("エラー発生：" + e.getMessage());
            e.printStackTrace();
        }
    }
}