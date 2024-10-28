package com.office.library.book.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.library.book.BookVo;

@Service
public class BookService {
	
	final static public int BOOK_ISBN_ALREADY_EXIST = 0; //이미 등록된 도서
	final static public int BOOK_REGISTER_SUCCESS = 1;	//신규 도서 등록 성공
	final static public int BOOK_REGISTER_FAIL = -1;	//신규 도서 등록 실패

	@Autowired
	BookDao bookDao;
	
	public int registerBookConfirm(BookVo bookVo) {
		System.out.println("[BookService] registerBookConfirm()");
		
		//ISBN은 국제적으로 있는 값
		boolean isISBN = bookDao.isISBN(bookVo.getB_isbn());
		
		if(!isISBN) {
			int result = bookDao.insertBook(bookVo);
			
			if(result >0)
				return BOOK_REGISTER_SUCCESS;
			else
				return BOOK_REGISTER_FAIL;
		}
		return BOOK_ISBN_ALREADY_EXIST;
	}
	
	public BookVo bookDetail(int b_no) {
		System.out.println("[BookService] bookDetail()");
		
		return bookDao.selectBook(b_no);
	}
	//검색기능 서비스 구현
	public List<BookVo> searchBookConfirm(BookVo bookVo) {
		System.out.println("[BookService] searchBookConfirm()");
		return bookDao.selectBooksBySearch(bookVo);
	}

	//검색 상세 보기 기능
	public BookVo modifyBookForm(int b_no) {
		System.out.println("[BookService] modifyBookForm()");
				
				return bookDao.selectBook(b_no);
	}
	


	public int modifyBookConfirm(BookVo bookVo) {
		System.out.println("[BookService] modifyBookConfirm()");
		
		return bookDao.updateBook(bookVo);
	}

	public int deleteBookConfirm(int b_no) {
		System.out.println("[BookService] deleteBookConfirm()");
		
		return bookDao.deleteBook(b_no);
	}

	

}
