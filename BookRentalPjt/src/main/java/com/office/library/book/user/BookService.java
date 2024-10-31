package com.office.library.book.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.library.book.BookVo;
import com.office.library.book.HopeBookVo;
import com.office.library.book.RentalBookVo;

@Service
public class BookService {
	
	@Autowired
	BookDao bookDao;
	
	//도서 검색
	public List<BookVo> searchBookConfirm(BookVo bookVo) {
		System.out.println("[UserBookBookService] searchBookConfirm()");
		
		return bookDao.selectBooksBySearch(bookVo);
		
	}
	
	//도서 상세
	public BookVo bookDetail(int b_no) {
		System.out.println("[UserBookBookService] bookDetail()");
		
		return bookDao.selectBook(b_no);
		
	}
	
	//도서 대출
	public int rentalBookConfirm(int b_no, int u_m_no) {
		System.out.println("[UserBookBookService] bookDetail()");
		
		int result = bookDao.insertRentalBook(b_no, u_m_no);
		
		if (result >= 0)
			bookDao.updateRentalBookAble(b_no);
		
		return result;
	}
	
	
	// 나의 책장
	
	public List<RentalBookVo> enterBookshelf(int u_m_no) {
		System.out.println("[UserBookBookService] enterBookshelf()");
		
		return bookDao.selectRentalBooks(u_m_no);
		
	}
	
	
	
	
	
	//도서 대출 이력
	public List<RentalBookVo> listupRentalBookHistory(int u_m_no) {
		System.out.println("[UserBookBookService] listupRentalBookHistory()");
		
		return bookDao.selectRentalBookHistory(u_m_no);
		
	}
	
	//희망 도서 요청 컨폼
	public int requestHopeBookConfirm(HopeBookVo hopeBookVo) {
		System.out.println("[UserBookBookService] requestHopeBookConfirm()");
		
		return bookDao.insertHopeBook(hopeBookVo);
	}

	//희망 도서 요청 확인
	public List<HopeBookVo> listupRentalBook(int u_m_no) {
		System.out.println("[UserBookBookService] listupRentalBook()");
		
		return bookDao.selectRequestHopeBooks(u_m_no);
	}
	
	//희망 도서 요청 목록
	public List<HopeBookVo> listupRequestHopeBook(int u_m_no) {
		System.out.println("[UserBookBookService] listupRequestHopeBook()");
		return bookDao.selectRequestHopeBooks(u_m_no);
	}
	
	//전체도서목록
	public List<BookVo> AllBooks() {
		System.out.println("[UserBookBookService] AllBooks()");
		
		return bookDao.selectBooks();
	}

	
}
