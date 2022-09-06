package site.metacoding.junitproject.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.junitproject.domain.Book;
import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.util.MailSender;
import site.metacoding.junitproject.web.dto.BookRespDto;
import site.metacoding.junitproject.web.dto.BookSaveReqDto;

@RequiredArgsConstructor
@Service
public class BookService {
	
	private final BookRepository bookRepository;
	private final MailSender mailSender;
	
	// 1. 책 등록
	@Transactional(rollbackFor = RuntimeException.class)
	public BookRespDto 책등록하기(BookSaveReqDto dto) {
		Book bookPS = bookRepository.save(dto.toEntity());
		if (bookPS != null) {
			if (!mailSender.send()) {
				throw new RuntimeException("메일이 전송되지 않았습니다.");
			}
		}
		
		return bookPS.toDto();
	}
	
	// 2. 책 목록보기
	public List<BookRespDto> 책목록보기() {
		return bookRepository.findAll().stream()
				.map(Book::toDto)
				.collect(Collectors.toList());
	}
	
	// 3. 책 한건보기
	public BookRespDto 책한건보기(Long id) {
		Optional<Book> bookOP = bookRepository.findById(id);
		if (bookOP.isPresent()) { // 찾았다면
			Book bookPS = bookOP.get();
			return bookPS.toDto();
		}
		else {
			throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
		}
	}
	
	// 4. 책 삭제
	@Transactional(rollbackFor = RuntimeException.class)
	public void 책삭제하기(Long id) {
		bookRepository.deleteById(id);
	}
	
	// 5. 책 수정
	@Transactional(rollbackFor = RuntimeException.class)
	public BookRespDto 책수정하기(Long id, BookSaveReqDto dto) {
		Optional<Book> bookOP = bookRepository.findById(id);
		if (bookOP.isPresent()) {
			Book bookPS = bookOP.get();
			bookPS.update(dto.getTitle(), dto.getAuthor());
			return bookPS.toDto();
		}
		else {
			throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
		}
	}
}
