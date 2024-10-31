package com.office.library.book.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.library.book.BookVo;
import com.office.library.book.HopeBookVo;
import com.office.library.book.RentalBookVo;

@Service
public class BookService {

	final static public int BOOK_ISBN_ALREADY_EXIST = 0; // 이미 등록된 도서
	final static public int BOOK_REGISTER_SUCCESS = 1; // 신규 도서 등록 성공
	final static public int BOOK_REGISTER_FAIL = -1; // 신규 도서 등록 실패

	@Autowired
	BookDao bookDao;

	// 도서 등록 처리하기
	public int registerBookConfirm(BookVo bookVo) {
		System.out.println("[bookAdminBookService] registerBookConfirm()");

		boolean isISBN = bookDao.isISBN(bookVo.getB_isbn());

		if (!isISBN) {
			int result = bookDao.insertBook(bookVo);

			if (result > 0)
				return BOOK_REGISTER_SUCCESS;

			else
				return BOOK_REGISTER_FAIL;
		} else {
			return BOOK_ISBN_ALREADY_EXIST;
		}
	}
	
	//도서 검색
	public List<BookVo> searchBookConfirm(BookVo bookVo) {
		System.out.println("[bookAdminBookService] searchBookConfirm()");
		

		return bookDao.selectBooksBySearch(bookVo);
	}
	
	
	//도서 상세보기
	public BookVo bookDetail(int b_no) {
		System.out.println("[bookAdminBookService] bookDetail()");
		
		return bookDao.selectBook(b_no);
	}
	
	//도서 수정 폼
	public BookVo modifyBookForm(int b_no) {
		System.out.println("[bookAdminBookService] modifyBookForm()");
		
		return bookDao.selectBook(b_no);	
	}
	
	//도서 수정 확인
	public int modifyBookConfirm(BookVo bookVo) {
		System.out.println("[bookAdminBookService] modifyBookConfirm()");
		
		return bookDao.updateBook(bookVo);	
	}
	
	// 도서 삭제 확인
	public int deleteBookConfirm(int b_no) {
		System.out.println("[bookAdminBookService] deleteBookConfirm()");
		
		return bookDao.deleteBook(b_no);	
	}
	
	//대출도서 확인
	public List<RentalBookVo> getRentalBooks() {
		System.out.println("[bookAdminBookService] getRentalBooks()");
			
		return bookDao.selectRentalBooks();
	}
	//대출도서 반납처리
		public int returnBookConfirm(int b_no, int rb_no) {
			System.out.println("[bookAdminBookService] returnBookConfirm()");
			
			int result = bookDao.updateRentalBook(rb_no);
			if(result > 0)
				result = bookDao.updateBook(b_no);
			
			return result;
		}
		
	//희망 도서 목록
		public List<HopeBookVo> getHopeBooks() {
			System.out.println("[bookAdminBookService] getHopeBooks()");
			
			
			return bookDao.selectHopeBooks();
		}
		
		//희망 도서 입고
		public int registerHopeBookConfirm(BookVo bookVo, int hb_no) {
			System.out.println("[bookAdminBookService] registerHopeBookConfirm()");
			
			boolean isISBN = bookDao.isISBN(bookVo.getB_isbn());
			
			if(!isISBN) {
				int result =bookDao.insertBook(bookVo);
				
				if(result >0 ) {
					bookDao.updateHopeBookResult(hb_no);
					
					return BOOK_REGISTER_SUCCESS;
					
				} else {
					return BOOK_REGISTER_FAIL;
				}
				
			} else {
				return BOOK_ISBN_ALREADY_EXIST;
	
			}		
		}
		
		//전체도서목록
		public List<BookVo> getAllBooks() {
			System.out.println("[bookAdminBookService] getAllBooks()");
			
			return bookDao.selectAllBooks();
		}
}
