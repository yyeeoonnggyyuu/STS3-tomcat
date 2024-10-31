package com.office.library.book.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.office.library.book.BookVo;
import com.office.library.book.HopeBookVo;
import com.office.library.book.RentalBookVo;

@Component
public class BookDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	// 신규 도서 등록 전 비교
	public boolean isISBN(String b_isbn) {
		System.out.println("[bookAdminBookDao] isISBN()");

		String sql = "SELECT COUNT(*) FROM tbl_book " + "WHERE b_isbn = ?";

		int result = jdbcTemplate.queryForObject(sql, Integer.class, b_isbn);

		return result > 0 ? true : false;

	}

	// 신규 도서 등록 INSERT 문
	public int insertBook(BookVo bookVo) {
		System.out.println("[bookAdminBookDao] insertBook()");

		String sql = "INSERT INTO tbl_book(b_thumbnail, " + "b_name, " + "b_author, " + "b_publisher, "
				+ "b_publish_year, " + "b_isbn, " + "b_call_number, " + "b_rental_able, " + "b_reg_date, "
				+ "b_mod_date) " + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";

		int result = -1;

		try {

			result = jdbcTemplate.update(sql, bookVo.getB_thumbnail(), bookVo.getB_name(), bookVo.getB_author(),
					bookVo.getB_publisher(), bookVo.getB_publish_year(), bookVo.getB_isbn(), bookVo.getB_call_number(),
					bookVo.getB_rental_able());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 도서 검색
	public List<BookVo> selectBooksBySearch(BookVo bookVo) {
		System.out.println("[bookAdminBookDao] insertBook()");

		String sql = "SELECT * FROM tbl_book " 
					+ "WHERE b_name LIKE ? "
					+ "ORDER BY b_no DESC";

		List<BookVo> bookVos = null;

		try {
			bookVos = jdbcTemplate.query(sql, new RowMapper<BookVo>() {

				@Override
				public BookVo mapRow(ResultSet rs, int rowNum) throws SQLException {

					BookVo bookVo = new BookVo();

					bookVo.setB_no(rs.getInt("b_no"));
					bookVo.setB_thumbnail(rs.getString("b_thumbnail"));
					bookVo.setB_name(rs.getString("b_name"));
					bookVo.setB_author(rs.getString("b_author"));
					bookVo.setB_publisher(rs.getString("b_publisher"));
					bookVo.setB_publish_year(rs.getString("b_publish_year"));
					bookVo.setB_isbn(rs.getString("b_isbn"));
					bookVo.setB_call_number(rs.getString("b_call_number"));
					bookVo.setB_rental_able(rs.getInt("b_rental_able"));
					bookVo.setB_reg_date(rs.getString("b_reg_date"));
					bookVo.setB_mod_date(rs.getString("b_mod_date"));

					return bookVo;

				}
			}, "%" + bookVo.getB_name() + "%");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookVos.size() > 0 ? bookVos : null;
	}
	
	//도서 상세보기
	public BookVo selectBook(int b_no) {
		System.out.println("[bookAdminBookDao] selectBook()");
		
		String sql = "SELECT * FROM tbl_book WHERE b_no = ?";
		
		List<BookVo> bookVos = null;
		
		try {
			
			bookVos = jdbcTemplate.query(sql, new RowMapper<BookVo>() {

				@Override
				public BookVo mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					BookVo bookVo = new BookVo();
					
					bookVo.setB_no(rs.getInt("b_no"));
					bookVo.setB_thumbnail(rs.getString("b_thumbnail"));
					bookVo.setB_name(rs.getString("b_name"));
					bookVo.setB_author(rs.getString("b_author"));
					bookVo.setB_publisher(rs.getString("b_publisher"));
					bookVo.setB_publish_year(rs.getString("b_publish_year"));
					bookVo.setB_isbn(rs.getString("b_isbn"));
					bookVo.setB_call_number(rs.getString("b_call_number"));
					bookVo.setB_rental_able(rs.getInt("b_rental_able"));
					bookVo.setB_reg_date(rs.getString("b_reg_date"));
					bookVo.setB_mod_date(rs.getString("b_mod_date"));
					
					return bookVo;
					
				}
				
			}, b_no);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return bookVos.size() > 0 ? bookVos.get(0) : null;
	}
	
		
	//도서 수정 확인
	public int updateBook(BookVo bookVo) {
		System.out.println("[bookAdminBookDao] updateBook()");
		
		List<String> args = new ArrayList<String>();
		
		String sql =  "UPDATE tbl_book SET ";
			   if (bookVo.getB_thumbnail() != null) {
				   sql += "b_thumbnail = ?, ";
				   args.add(bookVo.getB_thumbnail());
			   }
			   
			   sql += "b_name = ?, ";
			   args.add(bookVo.getB_name());
			   
			   sql += "b_author = ?, ";
			   args.add(bookVo.getB_author());
			   
			   sql += "b_publisher = ?, ";
			   args.add(bookVo.getB_publisher());
			   
			   sql += "b_publish_year = ?, ";
			   args.add(bookVo.getB_publish_year());
			   
			   sql += "b_isbn = ?, ";
			   args.add(bookVo.getB_isbn());
			   
			   sql += "b_call_number = ?, ";
			   args.add(bookVo.getB_call_number());
			   
			   sql += "b_rental_able = ?, ";
			   args.add(Integer.toString(bookVo.getB_rental_able()));
			   
			   sql += "b_mod_date = NOW() ";
			   
			   sql += "WHERE b_no = ?";
			   args.add(Integer.toString(bookVo.getB_no()));
			   
		
		int result = -1;
		
		try {	
			result = jdbcTemplate.update(sql, args.toArray());
		} catch (Exception e) {
			e.printStackTrace();		
		}

		return result;	
	}
	
		
	// 도서 삭제 확인
	public int deleteBook(int b_no) {
		System.out.println("[bookAdminBookDao] deleteBook()");
		
		String sql =  "DELETE FROM tbl_book "
					+ "WHERE b_no = ?";
		
		int result = -1;
		
		try {	
			result = jdbcTemplate.update(sql, b_no);		
		} catch (Exception e) {
			e.printStackTrace();		
		}
		return result;
	}
	
	//대출도서 확인
		public List<RentalBookVo> selectRentalBooks() {
			System.out.println("[bookAdminBookDao] selectRentalBooks()");

			String sql = "SELECT * FROM tbl_rental_book rb " 
					+ "JOIN tbl_book b " 
					+ "ON rb.b_no = b.b_no "
					+ "JOIN tbl_user_member um " 
					+ "ON rb.u_m_no = um.u_m_no "
					+ "WHERE rb.rb_end_date = '1000-01-01' "
					+ "ORDER BY um.u_m_id ASC, rb.rb_reg_date DESC";

			List<RentalBookVo> rentalBookVos = new ArrayList<RentalBookVo>();

			try {

				rentalBookVos = jdbcTemplate.query(sql, new RowMapper<RentalBookVo>() {

					@Override
					public RentalBookVo mapRow(ResultSet rs, int rowNum) throws SQLException {

						RentalBookVo rentalBookVo = new RentalBookVo();

						rentalBookVo.setRb_no(rs.getInt("rb_no"));
						rentalBookVo.setB_no(rs.getInt("b_no"));
						rentalBookVo.setU_m_no(rs.getInt("u_m_no"));
						rentalBookVo.setRb_start_date(rs.getString("rb_start_date"));
						rentalBookVo.setRb_end_date(rs.getString("rb_end_date"));
						rentalBookVo.setRb_reg_date(rs.getString("rb_reg_date"));
						rentalBookVo.setRb_mod_date(rs.getString("rb_mod_date"));

						rentalBookVo.setB_thumbnail(rs.getString("b_thumbnail"));
						rentalBookVo.setB_name(rs.getString("b_name"));
						rentalBookVo.setB_author(rs.getString("b_author"));
						rentalBookVo.setB_publisher(rs.getString("b_publisher"));
						rentalBookVo.setB_publish_year(rs.getString("b_publish_year"));
						rentalBookVo.setB_isbn(rs.getString("b_isbn"));
						rentalBookVo.setB_call_number(rs.getString("b_call_number"));
						rentalBookVo.setB_rental_able(rs.getInt("b_rental_able"));
						rentalBookVo.setB_reg_date(rs.getString("b_reg_date"));

						rentalBookVo.setU_m_id(rs.getString("u_m_id"));
						rentalBookVo.setU_m_pw(rs.getString("u_m_pw"));
						rentalBookVo.setU_m_name(rs.getString("u_m_name"));
						rentalBookVo.setU_m_gender(rs.getString("u_m_gender"));
						rentalBookVo.setU_m_mail(rs.getString("u_m_mail"));
						rentalBookVo.setU_m_phone(rs.getString("u_m_phone"));
						rentalBookVo.setU_m_reg_date(rs.getString("u_m_reg_date"));
						rentalBookVo.setU_m_mod_date(rs.getString("u_m_mod_date"));

						return rentalBookVo;

					}

				});

			} catch (Exception e) {
				e.printStackTrace();

			}

			return rentalBookVos;

		}
		
		//대출도서 반납처리
		
		public int updateRentalBook(int rb_no) {
			System.out.println("[bookAdminBookDao] updateRentalBook()");
			
			String sql = "UPDATE tbl_rental_book " 
						+ "SET rb_end_date = NOW() " 
						+ "WHERE rb_no = ?";
			
			int result = -1;

			try {

				result = jdbcTemplate.update(sql, rb_no);

			} catch (Exception e) {
				e.printStackTrace();

			}
			
			return result;
		}
		
		public int updateBook(int b_no) {
			System.out.println("[bookAdminBookDao] updateBook()");

			String sql = "UPDATE tbl_book " 
						+ "SET b_rental_able = 1 " 
						+ "WHERE b_no = ?";

			int result = -1;

			try {

				result = jdbcTemplate.update(sql, b_no);

			} catch (Exception e) {
				e.printStackTrace();

			}

			return result;

		}
		
		//희망 도서 목록
		public List<HopeBookVo> selectHopeBooks() {
			System.out.println("[bookAdminBookDao] selectHopeBooks()");
			
			String sql = "SELECT * FROM tbl_hope_book hb " 
					+ "JOIN tbl_user_member um " 
					+ "ON hb.u_m_no = um.u_m_no "
					+ "ORDER BY hb.hb_no DESC";

			List<HopeBookVo> hopeBookVos = new ArrayList<HopeBookVo>();

			try {

				hopeBookVos = jdbcTemplate.query(sql, new RowMapper<HopeBookVo>() {

					@Override
					public HopeBookVo mapRow(ResultSet rs, int rowNum) throws SQLException {

						HopeBookVo hopeBookVo = new HopeBookVo();

						hopeBookVo.setHb_no(rs.getInt("hb_no"));
						hopeBookVo.setHb_name(rs.getString("hb_name"));
						hopeBookVo.setHb_author(rs.getString("hb_author"));
						hopeBookVo.setHb_publisher(rs.getString("hb_publisher"));
						hopeBookVo.setHb_publish_year(rs.getString("hb_publish_year"));
						hopeBookVo.setHb_reg_date(rs.getString("hb_reg_date"));
						hopeBookVo.setHb_mod_date(rs.getString("hb_mod_date"));
						hopeBookVo.setHb_result(rs.getInt("hb_result"));
						hopeBookVo.setHb_result_last_date(rs.getString("hb_result_last_date"));
						
				
						hopeBookVo.setU_m_no(rs.getInt("u_m_no"));
						hopeBookVo.setU_m_id(rs.getString("u_m_id"));
						hopeBookVo.setU_m_pw(rs.getString("u_m_pw"));
						hopeBookVo.setU_m_name(rs.getString("u_m_name"));
						hopeBookVo.setU_m_gender(rs.getString("u_m_gender"));
						hopeBookVo.setU_m_mail(rs.getString("u_m_mail"));
						hopeBookVo.setU_m_phone(rs.getString("u_m_phone"));
						hopeBookVo.setU_m_reg_date(rs.getString("u_m_reg_date"));
						hopeBookVo.setU_m_mod_date(rs.getString("u_m_mod_date"));
						
						return hopeBookVo;

					}

				});

			} catch (Exception e) {
				e.printStackTrace();

			}

			return hopeBookVos;
		}

		//희망 도서 입고
		public void updateHopeBookResult(int hb_no) {
			System.out.println("[bookAdminBookDao] updateHopeBookResult()");

			String sql = "UPDATE tbl_hope_book " 
						+ "SET hb_result = 1, hb_result_last_date = NOW() "
						+ "WHERE hb_no = ?";

			try {

				jdbcTemplate.update(sql, hb_no);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		//전체도서목록
		public List<BookVo> selectAllBooks() {
			System.out.println("[bookAdminBookDao] selectAllBooks()");

			String sql = "SELECT * FROM tbl_book " 
						+ "ORDER BY b_reg_date DESC";

			List<BookVo> books = new ArrayList<BookVo>();

			try {

				books = jdbcTemplate.query(sql, new RowMapper<BookVo>() {

					@Override
					public BookVo mapRow(ResultSet rs, int rowNum) throws SQLException {

						BookVo bookVo = new BookVo();

					
						bookVo.setB_no(rs.getInt("b_no"));
						bookVo.setB_thumbnail(rs.getString("b_thumbnail"));
						bookVo.setB_name(rs.getString("b_name"));
						bookVo.setB_author(rs.getString("b_author"));
						bookVo.setB_publisher(rs.getString("b_publisher"));
						bookVo.setB_publish_year(rs.getString("b_publish_year"));
						bookVo.setB_isbn(rs.getString("b_isbn"));
						bookVo.setB_call_number(rs.getString("b_call_number"));
						bookVo.setB_rental_able(rs.getInt("b_rental_able"));
						bookVo.setB_reg_date(rs.getString("b_reg_date"));
						bookVo.setB_mod_date(rs.getString("b_mod_date"));

						return bookVo;
						}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
			return books.size() > 0 ? books : null;
		}
}
