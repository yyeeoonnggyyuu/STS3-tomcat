package com.office.library.admin.member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminMemberDao {

	@Autowired
	JdbcTemplate jdbcTemplate;/* DB에 연결하기위한 */

	@Autowired
	PasswordEncoder passwordEncoder;/* 암호화를 위해서 */

	public boolean isAdminMember(String a_m_id) {
		System.out.println("[AdminMemberDao] isAdminMember()");

		String sql = "SELECT COUNT(*) FROM tbl_admin_member " + "WHERE a_m_id = ?";/* ? 에다가아래 a_m_id를 저장하겠다 */

		int result = jdbcTemplate.queryForObject(sql, Integer.class, a_m_id);
		/*
		 * jdbcTemplate. -> String값으로 저장됨 외우기 결과를 integer로 받겠다 인트객체값 queryForObject ->
		 */
		if (result > 0)
			return true;
		else
			return false;

	}

	/* 이미 등록된 아이디가 없을 때 실행 */
	public int insertAdminAccount(AdminMemberVo adminMemberVo) {
		System.out.println("[AdminMemberDao] insertAdminAccount()");

		List<String> args = new ArrayList<String>();/* 위의 adminMemverVo를 넣기위해 리스트작성 */
		/* list 부모 arraylist 자식 */
		String sql = "INSERT INTO tbl_admin_member(";
		if (adminMemberVo.getA_m_id().equals("super admin")) {
			sql += "a_m_approval,";
			args.add("1");
		}

		sql += "a_m_id, ";
		args.add(adminMemberVo.getA_m_id());
		sql += "a_m_pw, ";
		args.add(passwordEncoder.encode(adminMemberVo.getA_m_pw()));
		sql += "a_m_name, ";
		args.add(adminMemberVo.getA_m_name());
		sql += "a_m_gender, ";
		args.add(adminMemberVo.getA_m_gender());
		sql += "a_m_part, ";
		args.add(adminMemberVo.getA_m_part());
		sql += "a_m_position, ";
		args.add(adminMemberVo.getA_m_position());
		sql += "a_m_mail, ";
		args.add(adminMemberVo.getA_m_mail());
		sql += "a_m_phone, ";
		args.add(adminMemberVo.getA_m_phone());

		sql += "a_m_reg_date, a_m_mod_date) ";

		if (adminMemberVo.getA_m_id().equals("super admin"))
			sql += "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
		/*
		 * ?가 하나 더많은 이유는 위의 sql += "a_m_approval,"; args.add("1");* 의 1이 들어가있음
		 */
		else
			sql += "VALUES(?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";

		int result = -1;

		try {
			result = jdbcTemplate.update(sql, args.toArray());/* update -> insert , update, delete 할 수 있음 */
			/* update는 성공한 갯수를 반환해줌 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public AdminMemberVo selectAdmin(AdminMemberVo adminMemberVo) {
		System.out.println("[AdminMemberDao] selectAdmin()");

		String sql = "SELECT * FROM tbl_admin_member " + "WHERE a_m_id = ? AND a_m_approval > 0";

		List<AdminMemberVo> adminMemberVos = new ArrayList<AdminMemberVo>();

		try {

			adminMemberVos = jdbcTemplate.query(sql, new RowMapper<AdminMemberVo>() {

				@Override
				public AdminMemberVo mapRow(ResultSet rs, int rowNum) throws SQLException {
					AdminMemberVo adminMemberVo = new AdminMemberVo();

					adminMemberVo.setA_m_no(rs.getInt("a_m_no"));
					adminMemberVo.setA_m_approval(rs.getInt("a_m_approval"));
					adminMemberVo.setA_m_id(rs.getString("a_m_id"));
					adminMemberVo.setA_m_pw(rs.getString("a_m_pw"));
					adminMemberVo.setA_m_name(rs.getString("a_m_name"));
					adminMemberVo.setA_m_gender(rs.getString("a_m_gender"));
					adminMemberVo.setA_m_part(rs.getString("a_m_part"));
					adminMemberVo.setA_m_position(rs.getString("a_m_position"));
					adminMemberVo.setA_m_mail(rs.getString("a_m_mail"));
					adminMemberVo.setA_m_phone(rs.getString("a_m_phone"));
					adminMemberVo.setA_m_reg_date(rs.getString("a_m_reg_date"));
					adminMemberVo.setA_m_mod_date(rs.getString("a_m_mod_date"));

					return adminMemberVo;
				}
			}, adminMemberVo.getA_m_id());

			if (!passwordEncoder.matches(adminMemberVo.getA_m_pw(), adminMemberVos.get(0).getA_m_pw()))
				adminMemberVos.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return adminMemberVos.size() > 0 ? adminMemberVos.get(0) : null;
	}

	// 관리자 승인 관련 셀렉문
	public List<AdminMemberVo> selectAdmins() {
		System.out.println("[AdminMemberDao] selectAdmins()");
		String sql = "SELECT * FROM tbl_admin_member";

		List<AdminMemberVo> adminMemberVos = new ArrayList<AdminMemberVo>();

		try {

			adminMemberVos = jdbcTemplate.query(sql, new RowMapper<AdminMemberVo>() {

				@Override
				public AdminMemberVo mapRow(ResultSet rs, int rowNum) throws SQLException {
					AdminMemberVo adminMemberVo = new AdminMemberVo();

					adminMemberVo.setA_m_no(rs.getInt("a_m_no"));
					adminMemberVo.setA_m_approval(rs.getInt("a_m_approval"));
					adminMemberVo.setA_m_id(rs.getString("a_m_id"));
					adminMemberVo.setA_m_pw(rs.getString("a_m_pw"));
					adminMemberVo.setA_m_name(rs.getString("a_m_name"));
					adminMemberVo.setA_m_gender(rs.getString("a_m_gender"));
					adminMemberVo.setA_m_part(rs.getString("a_m_part"));
					adminMemberVo.setA_m_position(rs.getString("a_m_position"));
					adminMemberVo.setA_m_mail(rs.getString("a_m_mail"));
					adminMemberVo.setA_m_phone(rs.getString("a_m_phone"));
					adminMemberVo.setA_m_reg_date(rs.getString("a_m_reg_date"));
					adminMemberVo.setA_m_mod_date(rs.getString("a_m_mod_date"));

					return adminMemberVo;
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

		return adminMemberVos;
	}

	// 관리자목록 일반관리자 승인
	public int updateAdminAccount(int a_m_no) {
		System.out.println("[AdminMemberDao] updateAdminAccount()");

		String sql = "UPDATE tbl_admin_member SET " + "a_m_approval = 1 " + "WHERE a_m_no = ?";

		int result = -1;

		try {
			result = jdbcTemplate.update(sql, a_m_no);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 관리자 정보 수정
	public int updateAdminAccount(AdminMemberVo adminMemberVo) {
		System.out.println("[AdminMemberDao] updateAdminAccount()");

		String sql = "UPDATE tbl_admin_member SET " 
				+ "a_m_name = ?, " 
				+ "a_m_gender = ?, " 
				+ "a_m_part = ?, "
				+ "a_m_position = ?, " 
				+ "a_m_mail = ?, " 
				+ "a_m_phone = ?, " 
				+ "a_m_mod_date = NOW() "
				+ "WHERE a_m_no = ?";

		int result = -1;
		try {
			result = jdbcTemplate.update(sql, adminMemberVo.getA_m_name(), adminMemberVo.getA_m_gender(),
					adminMemberVo.getA_m_part(), adminMemberVo.getA_m_position(), adminMemberVo.getA_m_mail(),
					adminMemberVo.getA_m_phone(), adminMemberVo.getA_m_no());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public AdminMemberVo selectAdmin(int a_m_no) {
		System.out.println("[AdminMemberDao] selectAdmin()");

		String sql = "SELECT & FROM tbl_admin_member " + "WHERE a_m_no = ?";

		List<AdminMemberVo> adminMemberVos = new ArrayList<AdminMemberVo>();

		try {

			adminMemberVos = jdbcTemplate.query(sql, new RowMapper<AdminMemberVo>() {

				@Override
				public AdminMemberVo mapRow(ResultSet rs, int rowNum) throws SQLException {
					AdminMemberVo adminMemberVo = new AdminMemberVo();

					adminMemberVo.setA_m_no(rs.getInt("a_m_no"));
					adminMemberVo.setA_m_approval(rs.getInt("a_m_approval"));
					adminMemberVo.setA_m_id(rs.getString("a_m_id"));
					adminMemberVo.setA_m_pw(rs.getString("a_m_pw"));
					adminMemberVo.setA_m_name(rs.getString("a_m_name"));
					adminMemberVo.setA_m_gender(rs.getString("a_m_gender"));
					adminMemberVo.setA_m_part(rs.getString("a_m_part"));
					adminMemberVo.setA_m_position(rs.getString("a_m_position"));
					adminMemberVo.setA_m_mail(rs.getString("a_m_mail"));
					adminMemberVo.setA_m_phone(rs.getString("a_m_phone"));
					adminMemberVo.setA_m_reg_date(rs.getString("a_m_reg_date"));
					adminMemberVo.setA_m_mod_date(rs.getString("a_m_mod_date"));

					return adminMemberVo;
				}
			}, a_m_no);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return adminMemberVos.size() > 0 ? adminMemberVos.get(0) : null;
	}
	
	//새 비밀번호 메일 전송 selectAdmin 구형
	public AdminMemberVo selectAdmin(String a_m_id, String a_m_name, String a_m_mail) {
		System.out.println("[AdminMemberDao] MailselectAdmin()");
		
		String sql = "SELECT * FROM tbl_admin_member " 
					+ "WHERE a_m_id = ? AND a_m_name =? AND a_m_mail = ?";

		List<AdminMemberVo> adminMemberVos = new ArrayList<AdminMemberVo>();
		
		try {
			adminMemberVos = jdbcTemplate.query(sql,new RowMapper<AdminMemberVo>() {
				
				@Override
				public AdminMemberVo mapRow(ResultSet rs, int rowNum) throws SQLException {
					AdminMemberVo adminMemberVo = new AdminMemberVo();

					adminMemberVo.setA_m_no(rs.getInt("a_m_no"));
					adminMemberVo.setA_m_id(rs.getString("a_m_id"));
					adminMemberVo.setA_m_pw(rs.getString("a_m_pw"));
					adminMemberVo.setA_m_name(rs.getString("a_m_name"));
					adminMemberVo.setA_m_gender(rs.getString("a_m_gender"));
					adminMemberVo.setA_m_part(rs.getString("a_m_part"));
					adminMemberVo.setA_m_position(rs.getString("a_m_position"));
					adminMemberVo.setA_m_mail(rs.getString("a_m_mail"));
					adminMemberVo.setA_m_phone(rs.getString("a_m_phone"));
					adminMemberVo.setA_m_reg_date(rs.getString("a_m_reg_date"));
					adminMemberVo.setA_m_mod_date(rs.getString("a_m_mod_date"));

					return adminMemberVo;
				}
			}, a_m_id, a_m_name, a_m_mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return adminMemberVos.size() > 0 ? adminMemberVos.get(0) : null;
	}
	
	public int updatePassword(String a_m_id, String newPassword) {
		System.out.println("[AdminMemberDao] updatePassword()");
		
		String sql = "UPDATE tbl_admin_member SET "
				+"a_m_pw = ?",
				+"a_m_mod_date = NOW() "
				+"WHERE a_m_id = ?";
		int result = -1;
		
		try {
			
		}
				
		
	}

}
