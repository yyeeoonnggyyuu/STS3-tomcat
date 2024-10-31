package com.office.library.book.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
			
			RowMapper<BookVo> rowMapper = BeanPropertyRowMapper.newInstance(BookVo.class);
			bookVos = jdbcTemplate.query(sql, rowMapper, "%" + bookVo.getB_name() + "%");

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
			
			RowMapper<BookVo> rowMapper = BeanPropertyRowMapper.newInstance(BookVo.class);
			bookVos = jdbcTemplate.query(sql, rowMapper, b_no);
	
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
				
				RowMapper<RentalBookVo> rowMapper = BeanPropertyRowMapper.newInstance(RentalBookVo.class);
				rentalBookVos = jdbcTemplate.query(sql, rowMapper);

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
				
				RowMapper<HopeBookVo> rowMapper = BeanPropertyRowMapper.newInstance(HopeBookVo.class);
				hopeBookVos = jdbcTemplate.query(sql, rowMapper);


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
				
				RowMapper<BookVo> rowMapper = BeanPropertyRowMapper.newInstance(BookVo.class);
				books = jdbcTemplate.query(sql, rowMapper);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return books.size() > 0 ? books : null;
		}
}
