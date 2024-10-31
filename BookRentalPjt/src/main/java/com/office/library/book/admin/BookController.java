package com.office.library.book.admin;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.taglibs.standard.lang.jstl.test.beans.PublicBean1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.office.library.admin.member.AdminMemberVo;
import com.office.library.book.BookVo;
import com.office.library.book.HopeBookVo;
import com.office.library.book.RentalBookVo;
import com.office.library.book.admin.util.UploadFileService;

@Controller
@RequestMapping("/book/admin")
public class BookController {

	@Autowired
	BookService bookService;

	@Autowired
	UploadFileService uploadFileService;

	// 도서등록 폼
	@GetMapping("/registerBookForm")
	public String registerBookForm() {
		System.out.println("[bookAdminBookController] registerBookForm");

		String nextPage = "admin/book/register_book_form";

		return nextPage;
	}
	
	//도서 등록 처리하기
	@PostMapping("/registerBookConfirm")
	public String registerBookConfirm(BookVo bookVo, @RequestParam("file") MultipartFile file) {
		System.out.println("[bookAdminBookController] registerBookConfirm()");

		String nextPage = "admin/book/register_book_ok";

		// SAVE FILE
		String savedFileName = uploadFileService.upload(file);

		if (savedFileName != null) {
			bookVo.setB_thumbnail(savedFileName);
			int result = bookService.registerBookConfirm(bookVo);

			if (result <= 0)
				nextPage = "admin/book/register_book_ng";
		} else {
			nextPage = "admin/book/register_book_ng";
		}
		return nextPage;
	}
	
	//도서 검색
	@GetMapping("/searchBookConfirm")
	public String searchBookConfirm(BookVo bookVo, Model model) {
		System.out.println("[bookAdminBookController] searchBookConfirm()");
		
		String nextPage ="admin/book/search_book";
		
		List<BookVo> bookVos = bookService.searchBookConfirm(bookVo);
		
		model.addAttribute("bookVos", bookVos);
				
		return nextPage;
	}
	
	//도서 상세보기
	@GetMapping("/bookDetail")
	public String bookDetail(@RequestParam("b_no") int b_no, Model model) {
		System.out.println("[bookAdminBookController] bookDetail()");
		
		String nextPage = "admin/book/book_detail";
		
		BookVo bookVo = bookService.bookDetail(b_no);
		
		model.addAttribute("bookVo", bookVo);
		return nextPage;

	}
	
	//도서 수정 폼
	@GetMapping("/modifyBookForm")
	public String modifyBookForm(@RequestParam("b_no") int b_no, 
								 Model model, 
								 HttpSession session) {
		System.out.println("[bookAdminBookController] modifyBookForm()");
		
		String nextPage = "admin/book/modify_book_form";
		
		AdminMemberVo loginedAdminMemberVo = (AdminMemberVo) session.getAttribute("loginedAdminMemberVo");
		if (loginedAdminMemberVo == null)
			return "redirect:/admin/member//loginForm";
		
		BookVo bookVo = bookService.modifyBookForm(b_no);
		
		model.addAttribute("bookVo", bookVo);
		
		return nextPage;
		
	}
	
	//도서 수정 확인
	@PostMapping("/modifyBookConfirm")
	public String modifyBookConfirm(BookVo bookVo, 
									@RequestParam("file") MultipartFile file, 
									HttpSession session) {
		System.out.println("[bookAdminBookController] modifyBookConfirm()");
		
		String nextPage = "admin/book/modify_book_ok";
		
		AdminMemberVo loginedAdminMemberVo = (AdminMemberVo) session.getAttribute("loginedAdminMemberVo");
		if (loginedAdminMemberVo == null)
			return "redirect:/admin/member//loginForm";
		
		if (!file.getOriginalFilename().equals("")) {
			// SAVE FILE
			String savedFileName = uploadFileService.upload(file);
			if (savedFileName != null)
				bookVo.setB_thumbnail(savedFileName);
			
		}
		
		int result = bookService.modifyBookConfirm(bookVo);
		
		if (result <= 0)
			nextPage = "admin/book/modify_book_ng";
		
		return nextPage;
	}
	
	// 도서 삭제 확인
	@GetMapping("/deleteBookConfirm")
	public String deleteBookConfirm(@RequestParam("b_no") int b_no, 
									HttpSession session) {
		System.out.println("[bookAdminBookController] deleteBookConfirm()");
		
		String nextPage = "admin/book/delete_book_ok";
		
		AdminMemberVo loginedAdminMemberVo = (AdminMemberVo) session.getAttribute("loginedAdminMemberVo");
		if (loginedAdminMemberVo == null)
			return "redirect:/admin/member//loginForm";

		int result = bookService.deleteBookConfirm(b_no);

		if (result <= 0)
			nextPage = "admin/book/delete_book_ng";

		return nextPage;
	}
	//대출도서 확인
	@GetMapping("/getRentalBooks")
	public String getRentalBooks(Model model) {
		System.out.println("[bookAdminBookController] getRentalBooks()");
		
		String nextPage = "admin/book/rental_books";
		
		List<RentalBookVo> rentalBookVos = bookService.getRentalBooks();
		
		model.addAttribute("rentalBookVos", rentalBookVos);
		
		return nextPage;
		
	}
	//대출 도서 반납처리
		@GetMapping("/returnBookConfirm")
		public String returnBookConfirm(@RequestParam("b_no") int b_no,
										@RequestParam("rb_no") int rb_no) {
			System.out.println("[bookAdminBookController] returnBookConfirm()");
			
			String nextPage ="admin/book/return_book_ok";
			 int result = bookService.returnBookConfirm(b_no, rb_no);
			 
			 if(result <=0)
				 nextPage = "admin/book/return_book_ng";
			return nextPage;
			
		}

	//희망 도서 목록
		@GetMapping("/getHopeBooks")
		public String getHopeBooks(Model model) {
			System.out.println("[bookAdminBookController] getHopeBooks()");
			
			String nextPage ="admin/book/hope_books";
			
			List<HopeBookVo> hopeBookVos = bookService.getHopeBooks();
			
			model.addAttribute("hopeBookVos", hopeBookVos);
			
			return nextPage;
			
		}
		
		//희망 도서 등록(입고처리)
		@GetMapping("/registerHopeBookForm")
		public String registerHopeBookForm(Model model, HopeBookVo hopeBookVo) {
			System.out.println("[bookAdminBookController] registerHopeBookForm()");
			
			String nextPage ="admin/book/register_hope_book_form";
			
			model.addAttribute("hopeBookVo",hopeBookVo);
			
			return nextPage;
		}
		
		//희망 도서 입고
		@PostMapping("/registerHopeBookConfirm")
		public String registerHopeBookConfirm(BookVo bookVo,
				@RequestParam("hb_no") int hb_no,
				@RequestParam("file") MultipartFile file) {
			System.out.println("[bookAdminBookController] registerHopeBookConfirm()");
			
			System.out.println("bh_no: " +hb_no);
			String nextPage ="admin/book/register_book_ok";
			
			String savedFileName = uploadFileService.upload(file);
			
			if(savedFileName != null) {
				bookVo.setB_thumbnail(savedFileName);
				int result = bookService.registerHopeBookConfirm(bookVo, hb_no);
				if(result <= 0)
					nextPage ="admin/book/register_book_ng";
			} else {
				nextPage ="admin/book/register_book_ng";
			}				
			
			return nextPage;
		}
		//전체도서목록
		@GetMapping("/getAllBooks")
		public String getAllBooks(Model model) {
			System.out.println("[bookAdminBookController] getAllBooks()");
		
			String nextPage ="admin/book/full_list_of_books";
			
			List<BookVo> bookVos = bookService.getAllBooks();
			
			model.addAttribute("bookVos",bookVos);
			
			return nextPage;
 		}
}
