package com.ssafy.yumyume.user.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import com.ssafy.yumyume.constant.ActivityLevel;
import com.ssafy.yumyume.user.model.dto.UserDto;
import com.ssafy.yumyume.util.db.DbUtil;

public class UserDaoImpl implements UserDao {

	private static UserDao userDao = new UserDaoImpl();

	private UserDaoImpl() {
	}

	public static UserDao getDao() {
		return userDao;
	}

	@Override
	public void registUser(UserDto user) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DbUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append(
					"insert into user (id, password, name, email, email_domain, weight, height, gender, age, activity_level, std_weight, std_kcal, std_carb_g, std_protein_g,  std_fat_g, std_sugar_g, std_natrium_g) \n");
			sql.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, user.getId());
			String pwd = user.getPassword();
			String hashpwd = BCrypt.hashpw(pwd, BCrypt.gensalt());
			pstmt.setString(2, hashpwd);
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, user.getEmailDomain());
			pstmt.setDouble(6, user.getWeight());
			pstmt.setDouble(7, user.getHeight());
			pstmt.setString(8, user.getGender());
			pstmt.setInt(9, user.getAge());
			pstmt.setString(10, user.getActivityLevel());
			String level = user.getActivityLevel();
			double num = 0;
			switch (level) {
				case "SEDENTARY":
					num = ActivityLevel.SEDENTARY.getNum();
					break;

				case "LIGHT":
					num = ActivityLevel.LIGHT.getNum();
					break;

				case "MODERATE":
					num = ActivityLevel.MODERATE.getNum();
					break;

				case "ACTIVE":
					num = ActivityLevel.ACTIVE.getNum();
					break;

				case "VERY_ACTIVE":
					num = ActivityLevel.VERY_ACTIVE.getNum();
					break;
			}
			double sWeight = 0;
			double stdKcal = 0;
			if ("남".equals(user.getGender())) {
				sWeight = Math.round((user.getHeight() - 100) * 90) / 100.0;
				stdKcal = (10 * sWeight + 6.25 * user.getHeight() - 5 * user.getAge() + 5) * num;
			} else {
				sWeight = Math.round((user.getHeight() - 100) * 85) / 100.0;
				stdKcal = (10 * sWeight + 6.25 * user.getHeight() - 5 * user.getAge() - 161) * num;
			}
			pstmt.setDouble(11, sWeight);
			pstmt.setDouble(12, Math.round(stdKcal * 100) / 100.0);
			pstmt.setDouble(13, Math.round(stdKcal * 0.6 / 4 * 100) / 100.0);
			pstmt.setDouble(14, Math.round(stdKcal * 0.14 / 4 * 100) / 100.0);
			pstmt.setDouble(15, Math.round(stdKcal * 0.22 / 9 * 100) / 100.0);
			pstmt.setDouble(16, Math.round(stdKcal * 0.04 / 4 * 100) / 100.0);
			pstmt.setDouble(17, 2);

			pstmt.executeUpdate();
		} finally {
			DbUtil.close(pstmt, conn);
		}
	}

	@Override
	public boolean idDuplicate(String id) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DbUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("select count(*) as cnt \n");
			sql.append("from user \n");
			sql.append("where id = ?");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("cnt") > 0;
			}
			return false;
		} finally {
			DbUtil.close(rs, pstmt, conn);
		}
	}

	@Override
	public UserDto login(String id, String password) throws SQLException {

		UserDto user = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DbUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append(
					"select id, password, name, age, email, email_domain, height, weight, gender, activity_level, role, std_weight, std_kcal, std_carb_g, std_protein_g, std_fat_g, std_sugar_g, std_natrium_g \n");
			sql.append("from user \n");
			sql.append("where id = ?");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				String storedPwd = rs.getString("password");
				if(BCrypt.checkpw(password, storedPwd)) {
					user = new UserDto();
					user.setId(rs.getString("id"));
					user.setName(rs.getString("name"));
					user.setAge(rs.getInt("age"));
					user.setEmail(rs.getString("email"));
					user.setEmailDomain(rs.getString("email_domain"));
					user.setHeight(rs.getDouble("height"));
					user.setWeight(rs.getDouble("weight"));
					user.setGender(rs.getString("gender"));
					user.setActivityLevel(rs.getString("activity_level"));
					user.setRole(rs.getString("role"));
					user.setStdWeight(rs.getDouble("std_weight"));
					user.setStdKcal(rs.getDouble("std_kcal"));
					user.setStdCarb(rs.getDouble("std_carb_g"));
					user.setStdProtein(rs.getDouble("std_protein_g"));
					user.setStdFat(rs.getDouble("std_fat_g"));
					user.setStdSugar(rs.getDouble("std_sugar_g"));
					user.setStdNatrium(rs.getDouble("std_natrium_g"));					
				} else {
					
				}
			}
		} finally {
			DbUtil.close(rs, pstmt, conn);
		}

		return user;
	}

	@Override
	public UserDto getUserInfobyId(String id) throws SQLException {

		UserDto user = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DbUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append(
					"select id, name, age, email, email_domain, height, weight, gender, activity_level, role, std_weight, std_kcal, std_carb_g, std_protein_g, std_fat_g, std_sugar_g, std_natrium_g \n");
			sql.append("from user \n");
			sql.append("where id = ?");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = new UserDto();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setAge(rs.getInt("age"));
				user.setEmail(rs.getString("email"));
				user.setEmailDomain(rs.getString("email_domain"));
				user.setHeight(rs.getDouble("height"));
				user.setWeight(rs.getDouble("weight"));
				user.setGender(rs.getString("gender"));
				user.setActivityLevel(rs.getString("activity_level"));
				user.setRole(rs.getString("role"));
				user.setStdWeight(rs.getDouble("std_weight"));
				user.setStdKcal(rs.getDouble("std_kcal"));
				user.setStdCarb(rs.getDouble("std_carb_g"));
				user.setStdProtein(rs.getDouble("std_protein_g"));
				user.setStdFat(rs.getDouble("std_fat_g"));
				user.setStdSugar(rs.getDouble("std_sugar_g"));
				user.setStdNatrium(rs.getDouble("std_natrium_g"));
			}

		} finally {
			DbUtil.close(rs, pstmt, conn);
		}

		return user;
	}

	@Override
	public void updateUser(UserDto user) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DbUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("update user set name = ?, email = ?, email_domain = ?, age = ?, gender = ?, height = ?, weight = ?, activity_level = ?, std_weight = ?, std_kcal = ?, std_carb_g = ?, std_protein_g = ?, std_fat_g = ?, std_sugar_g = ?, std_natrium_g = ?\n"); // 왕창 추가 필요
			sql.append("where id = ?");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getEmailDomain());
			pstmt.setInt(4, user.getAge());
			pstmt.setString(5, user.getGender());
			pstmt.setDouble(6, user.getHeight());
			pstmt.setDouble(7, user.getWeight());
			pstmt.setString(8, user.getActivityLevel());
			String level = user.getActivityLevel();
			double num = 0;
			switch (level) {
				case "SEDENTARY":
					num = ActivityLevel.SEDENTARY.getNum();
					break;

				case "LIGHT":
					num = ActivityLevel.LIGHT.getNum();
					break;

				case "MODERATE":
					num = ActivityLevel.MODERATE.getNum();
					break;

				case "ACTIVE":
					num = ActivityLevel.ACTIVE.getNum();
					break;

				case "VERY_ACTIVE":
					num = ActivityLevel.VERY_ACTIVE.getNum();
					break;
			}
			double sWeight = 0;
			double stdKcal = 0;
			if ("남".equals(user.getGender())) {
				sWeight = Math.round((user.getHeight() - 100) * 90) / 100.0;
				stdKcal = (10 * sWeight + 6.25 * user.getHeight() - 5 * user.getAge() + 5) * num;
			} else {
				sWeight = Math.round((user.getHeight() - 100) * 85) / 100.0;
				stdKcal = (10 * sWeight + 6.25 * user.getHeight() - 5 * user.getAge() - 161) * num;
			}
			pstmt.setDouble(9, sWeight);
			pstmt.setDouble(10, Math.round(stdKcal * 100) / 100.0);
			pstmt.setDouble(11, Math.round(stdKcal * 0.6 / 4 * 100) / 100.0);
			pstmt.setDouble(12, Math.round(stdKcal * 0.14 / 4 * 100) / 100.0);
			pstmt.setDouble(13, Math.round(stdKcal * 0.22 / 9 * 100) / 100.0);
			pstmt.setDouble(14, Math.round(stdKcal * 0.04 / 4 * 100) / 100.0);
			pstmt.setDouble(15, 2);
			
			pstmt.setString(16, user.getId());

			pstmt.executeUpdate();
		} finally {
			DbUtil.close(pstmt, conn);
		}

	}

	@Override
	public void deleteUser(String id) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DbUtil.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("delete from user \n");
			sql.append("where id = ?");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);

			pstmt.executeUpdate();
		} finally {
			DbUtil.close(pstmt, conn);
		}

	}

}
