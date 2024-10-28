package com.office.library.book.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.office.library.book.BookVo;
import com.office.library.book.admin.util.UploadFileService;

@Controller
@RequestMapping("/book/admin")
public class BookController {
	@Autowired
	BookService bookService;
	
	@Autowired
	UploadFileService uploadFileService;
	
	//도서 등록화면 이동 기능
	@GetMapping("/registerBookForm")
	public String registerBookForm() {
		System.out.println("[BookController] registerBookForm()");
		
		String nextPage = "admin/book/register_book_form";
		
		return nextPage;
	}
	
	//도서등록 확인
	@PostMapping("/registerBookConfirm")
	public String registerBookConfirm(BookVo bookVo, 
									@RequestParam("file") MultipartFile file) {
		System.out.println("[BookController] registerBookConfirm()");
		
		//파일 저장
		String nextPage = "admin/book/register_book_ok";
		//파일 업로드 기능구현 
		String savedFilename = uploadFileService.upload(file);
		
		if(savedFilename != null) {
			bookVo.setB_thumbnail(savedFilename);
			//서비스 기능 구현
			int result = bookService.registerBookConfirm(bookVo);
			
			if(result <= 0)
				nextPage ="admin/book/resister_book_ng";
		} else {
			nextPage ="admin/book/resister_book_ng";
		}
		
		return nextPage;
	}
	
	//도서 검색 기능
	@GetMapping("/searchBookConfirm")
	public String searchBookConfirm(BookVo bookVo, Model model) {
		System.out.println("[UserBookController] searchBookConfirm()");
		
		String nextPage = "admin/book/search_book";
		
		List<BookVo> bookVos = bookService.searchBookConfirm(bookVo);
		
		model.addAttribute("bookVos", bookVos);
		
		return nextPage;
	}
	
	//도서 상세 보기 처리
	@GetMapping("/bookDetail")
	public String bookDetail(@RequestParam("b_no") int b_no, Model model) {
		System.out.println("[BookController] bookDetail()");
		
		String nextPage = "admin/book/book_detail";
		
		BookVo bookVo = bookService.bookDetail(b_no);
		
		model.addAttribute("bookVo", bookVo);
		
		return nextPage;
		
	}
	
	//도서정보 수정처리
	@GetMapping("/modifyBookForm")
	public String modifyBookForm(@RequestParam("b_no") int b_no, Model model) {
		System.out.println("[BookController] modifyBookForm()");
		
		String nextPage = "admin/book/modify_book_form";
		
		BookVo bookVo = bookService.modifyBookForm(b_no);
		
		model.addAttribute("bookVo",bookVo);
		
		return nextPage;
	}
	
	@PostMapping("/modifyBookConfirm")
	public String modifyBookConfirm(BookVo bookVo, @RequestParam("file") MultipartFile file) {
		System.out.println("[BookController] modifyBookConfirm()");
		
		String nextPage = "admin/book/register_book_ok";
		
		if(!file.getOriginalFilename().equals("")) {
			String savedFileName = uploadFileService.upload(file);
			if(savedFileName != null)
				bookVo.setB_thumbnail(savedFileName);
		}
		
		int result = bookService.modifyBookConfirm(bookVo);
		
		if (result <= 0 )
			nextPage ="admin/book/register_book_ng";
		
		return nextPage;
	}
	
	@GetMapping("/deleteBookConfirm")
	public String deleteBookConfirm(@RequestParam("b_no") int b_no) {
		System.out.println("[BookController] deleteBookConfirm()");
		
		String nextPage="admin/book/delete_book_ok";
		
		int result = bookService.deleteBookConfirm(b_no);
		
		if(result <= 0)
			nextPage ="admin/book/delete_book_ng";
		
		return nextPage;
	}
}
